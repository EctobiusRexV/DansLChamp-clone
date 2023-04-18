package sim.danslchamp.controleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import org.reflections.Reflections;
import sim.danslchamp.Config;
import sim.danslchamp.circuit.*;
import sim.danslchamp.svg.FXASvg;

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

public class ConcepteurControleur {

    private static final int TAILLE_QUADRILLAGE_px = 25;

    private int posX = 0, posY = 0;

    private Circuit circuit;

    @FXML
    protected AnchorPane diagrammeAnchorPane;

    @FXML
    private ToolBar toolbar;

    @FXML
    private ToggleButton    curseurToggleButton,
                            filToggleButton,
                            lockToggleButton;

    private Line currentLine = new Line();

    private boolean vertical, annule;

    @FXML
    void initialize() {
        initBoutonsComposants();
        initZoom();
        initToggleGroup();
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
                    circuit.addComposant(composantClass, posX, posY, vertical);
                    diagrammeAnchorPane.getChildren().add(new ListPoint2D(circuit.getJonctions()).getGroupe());
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
            if (diagrammeAnchorPane.getScaleX() + event.getDeltaY() / 100 < 0) return; // empêcher d'obtenir un scale négatif

            diagrammeAnchorPane.scaleXProperty().set(diagrammeAnchorPane.getScaleX() + event.getDeltaY() / 1000);
            diagrammeAnchorPane.scaleYProperty().set(diagrammeAnchorPane.getScaleY() + event.getDeltaY() / 1000);

        });
    }

    private void initToggleGroup() {
        ToggleGroup group = new ToggleGroup();
        group.getToggles().setAll(curseurToggleButton, filToggleButton);
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public void setStage(Stage stage) {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                annule = true;
                currentLine.setVisible(false);
            }
        });
    }

    void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @FXML
    void mousePressed(MouseEvent event) {
        annule = curseurToggleButton.isSelected();

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
        currentLine.setEndX(Math.round((int) event.getX() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));
        currentLine.setEndY(Math.round((int) event.getY() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));
    }

    @FXML
    void mouseReleased() {
        if (!annule) {
            posX = (int) currentLine.getEndX();
            posY = (int) currentLine.getEndY();

            circuit.addComposant(new Fil((int) currentLine.getStartX(), (int) currentLine.getStartY(), posX, posY));

            diagrammeAnchorPane.getChildren().add(new ListPoint2D(circuit.getJonctions()).getGroupe());
        }
    }

    @FXML
    void sauvegarder() throws IOException {
        FXASvg.aSvg(circuit);
        Path sortie = Path.of("./circuitsExportsTests/sauvegardeDepuisConcepteur" + LocalDateTime.now().toString().replaceAll(":", "") + ".svg");

        Files.write(sortie, Collections.singleton(FXASvg.aSvg(circuit)), StandardOpenOption.CREATE);
    }


    public class ListPoint2D {

        private List<Jonction> jonctionList;


        public ListPoint2D(List<Jonction> jonctionList) {
            this.jonctionList = jonctionList;
        }

        public Group getGroupe() {
            Group groupe = new Group();
            for (Jonction jonction : jonctionList) {
                Sphere sp = new Sphere(5);
                sp.setMaterial(new PhongMaterial(Color.RED));

                int x = (int) jonction.getPositionXY().getX();
                int y = (int) jonction.getPositionXY().getY();
                sp.setLayoutX(x);
                sp.setLayoutY(y);

                sp.setOnMousePressed(event -> setPos(x, y));

                groupe.getChildren().add(sp);
            }

            return groupe;
        }
    }

}
