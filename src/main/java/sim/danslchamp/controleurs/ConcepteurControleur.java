package sim.danslchamp.controleurs;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;
import sim.danslchamp.Config;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.Util.DanslChampUtil;
import sim.danslchamp.circuit.*;
import sim.danslchamp.svg.FXASvg;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static sim.danslchamp.DansLChampApp.*;
import static sim.danslchamp.DansLChampApp.FILE_EXTENSION;

public class ConcepteurControleur {

    private static final int TAILLE_QUADRILLAGE_px = 25;
    public RadioButton curseurRadioButton;
    public RadioButton conceptionRadioButton;

    private int posX = 200, posY = 200;

    private Circuit circuit;

    // Pour communication suavegarde
    private CircuitControleur circuitControleur;
    protected File fichierEnregistrement;

    private Stage stage;
    private double angle;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                annulerEdition();
            }
        });
    }

    public void setCircuitControleur(CircuitControleur circuitControleur) {
        this.circuitControleur = circuitControleur;
    }

    @FXML
    protected AnchorPane diagrammeAnchorPane;

    @FXML
    private ToolBar toolbar;

    @FXML
    private ToggleButton lockToggleButton;

    private Line currentLine = new Line();

    private Group jonctionsGroup = new Group();

    private boolean vertical, annule;

    @FXML
    void initialize() {
        initBoutonsComposants();
        initZoom();

        jonctionsGroup.setVisible(false);
        conceptionRadioButton.selectedProperty().addListener((l, old, selected) -> {
            jonctionsGroup.setVisible(selected);;
        });
    }

    private void initBoutonsComposants() {
        Reflections reflections = new Reflections("sim.danslchamp.circuit");

//        Résistor résistor = new Résistor(10, 10, false, "10");
//        Composant.getSymbole2D(Résistor.class);

        Set<Class<? extends Composant>> composantsClasses = reflections.getSubTypesOf(Composant.class);
        composantsClasses.remove(Fil.class);
        composantsClasses.remove(Source.class);
        composantsClasses.remove(SousCircuit.class);
//        composantsClasses.remove(SousCircuit.class)
//        Set<Class<? extends Composant>> composantsClasses = Set.of(Bobine.class);
        for (Class<? extends Composant> composantClass :
                composantsClasses) {
            try {
                Method method = Composant.class.getMethod("getSymbole2D", String.class);
                Group group = (Group) method.invoke(Composant.class, composantClass.getSimpleName());
                Button button = new Button("", group);
                button.setTooltip(new Tooltip(composantClass.getSimpleName()));
                button.setOnAction(event -> {
                    Composant composant = Composant.getInstance(composantClass);
//                    composant.setAngleRotation((int) Math.toDegrees(angle));
                    composant.setPosXY(posX-composant.getJonctionsRelatives()[0].getPositionXY().x, posY-composant.getJonctionsRelatives()[0].getPositionXY().y);
                    posX = composant.getJonctions()[1].getPositionXY().x;
                    posY = composant.getJonctions()[1].getPositionXY().y;

                    circuit.addComposant(composant);
                });
                toolbar.getItems().add(button);
            } catch (ClassCastException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println(e);        // NE DEVRAIT PAS ARRIVER
            }
        }
    }

    private void initZoom() {
        diagrammeAnchorPane.addEventHandler(ScrollEvent.SCROLL, event -> {
            if (diagrammeAnchorPane.getScaleX() + event.getDeltaY() / 100 < 0)
                return; // empêcher d'obtenir un scale négatif

            diagrammeAnchorPane.scaleXProperty().set(diagrammeAnchorPane.getScaleX() + event.getDeltaY() / 1000);
            diagrammeAnchorPane.scaleYProperty().set(diagrammeAnchorPane.getScaleY() + event.getDeltaY() / 1000);

        });
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
        diagrammeAnchorPane.getChildren().setAll(circuit.getDiagramme2D().getGroup(), jonctionsGroup);

        jonctionsGroup.getChildren().clear();
        addJonctionPoint(circuit.getJonctions());

        circuit.getJonctions().addListener((ListChangeListener<? super Jonction>) l -> {
            l.next();
            addJonctionPoint((List<Jonction>) l.getAddedSubList());
        });
    }

    void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @FXML
    void mousePressed(MouseEvent event) {
        annule = curseurRadioButton.isSelected();

        if (annule) return;

        currentLine = new Line(posX, posY,
                Math.round((int) event.getX() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px),
                Math.round((int) event.getY() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));

        currentLine.setStrokeWidth(Config.defautComposantStrokeWidth);

        ajouterComportementDuComposant();

        diagrammeAnchorPane.getChildren().add(currentLine);       // FIXME: 2023-04-12 Les lignes ne sont jamais enlevées!
    }

    private void ajouterComportementDuComposant() {
        currentLine.setOnMouseEntered(e -> {
                    Line line = (Line) e.getTarget();

                    line.setStrokeWidth(10);
                    line.setStyle("-fx-stroke: blue");
                    line.setOnMouseExited(ev -> {
                        // Revenir à la normale
                        line.setStrokeWidth(Config.defautComposantStrokeWidth);
                        line.setStyle("");
                    });
                }
        );
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        if (annule) return;
        if (event.getX() < 0 || event.getY() < 0
                || event.getX() > diagrammeAnchorPane.getWidth() || event.getY() > diagrammeAnchorPane.getHeight()) event.consume();
        else {
            currentLine.setEndX(Math.round((int) event.getX() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));
            currentLine.setEndY(Math.round((int) event.getY() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));
        }
    }

    @FXML
    void mouseReleased() {
        if (!annule) {
//            angle = Math.atan2(currentLine.getEndY() - currentLine.getStartY(), currentLine.getEndX() - currentLine.getStartX());
            posX = (int) currentLine.getEndX();
            posY = (int) currentLine.getEndY();

            circuit.addComposant(new Fil((int) currentLine.getStartX(), (int) currentLine.getStartY(), posX, posY));
        }
    }

    public void enregistrer() {
        if (fichierEnregistrement == null) enregistrerSous();
        else {
            try {
                Files.write(fichierEnregistrement.toPath(), Collections.singleton(FXASvg.aSvg(circuit)), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                DanslChampUtil.erreur("Impossible d'enregistrer le fichier", e.getMessage());
            }
        }
    }

    public void enregistrerSous() {
        fichierEnregistrement = FC.showSaveDialog(null);
        if (fichierEnregistrement != null) {
            enregistrer();
        }
    }

    public void annulerEdition() {
        annule = true;
        currentLine.setVisible(false);
    }

    public void ouvrir() {
        try {
            File file = FC.showOpenDialog(null);
            if (file != null) {
                setCircuit(Circuit.chargerCircuit(file));  // Ne pas ouvrir si aucune sélection n'est faite!
                fichierEnregistrement = file;
            }
        } catch (FileNotFoundException neSappliquePas) {
        }
    }

    private void addJonctionPoint(List<Jonction> list) {
        for (Jonction jonction : list) {
            Sphere sp = new Sphere(8);
            sp.setMaterial(new PhongMaterial(Color.RED));

            int x = (int) jonction.getPositionXY().getX();
            int y = (int) jonction.getPositionXY().getY();
            sp.setLayoutX(x);
            sp.setLayoutY(y);

            sp.setOnMousePressed(event -> setPos(x, y));
            sp.setOnMouseReleased(event -> setPos(x, y));

            jonctionsGroup.getChildren().add(sp);
        }
    }

    public void ouvrirCircuit(ActionEvent actionEvent) throws FileNotFoundException {
        if (fichierEnregistrement == null) {
            DanslChampUtil.warning("Vous devez enregistrer avant de visualiser.", "");
        }
        DansLChampApp.showConcepteurDeCircuit(fichierEnregistrement);
    }

    public static void nouveau(@Nullable Circuit pcircuit) {
        Circuit circuit = pcircuit != null
                ? pcircuit
                : new Circuit();

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            stage.setScene(new Scene(fxmlLoader.load(DansLChampApp.class.getResourceAsStream("fxml/Concepteur.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((ConcepteurControleur) fxmlLoader.getController()).setCircuit(circuit);
        ((ConcepteurControleur) fxmlLoader.getController()).setStage(stage);

        stage.show();
    }
}
