package sim.danslchamp.controleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import sim.danslchamp.Config;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.Util.ComposantsListCell;
import sim.danslchamp.Util.DanslChampUtil;
import sim.danslchamp.circuit.Bobine;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;
import sim.danslchamp.svg.FXASvg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Collections;

import static sim.danslchamp.DansLChampApp.*;

/**
 * Contrôleur de la fenêtre Concepteur de circuit
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 * @author Thierry Rhéaume
 */
public class CircuitControleur extends ParentControleur {

    private Circuit circuit;

    private File fichierEnregistrement;

    private ConcepteurControleur concepteurControleur;

    Tooltip infobulleC = new Tooltip();

    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);

        stage.setMaximized(Config.circuitMaximise);
        if (!Config.circuitMaximise) {
            stage.setX(Config.circuitPosX);
            stage.setY(Config.circuitPosY);
            stage.setWidth(Config.circuitLargeur);
            stage.setHeight(Config.circuitHauteur);
        }

        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                concepteurControleur.annulerEdition();
            }
        });

        stage.setOnHidden(event -> {
            Config.circuitDiagrammesSplitPanePosition0 = diagrammesSplitPane.getDividerPositions()[0];
            Config.circuitDiagrammesSplitPanePosition1 = diagrammesSplitPane.getDividerPositions()[1];
            Config.circuitMaximise = stage.isMaximized();
            if (!stage.isMaximized()) {
                Config.circuitPosX = stage.getX();
                Config.circuitPosY = stage.getY();
                Config.circuitLargeur = stage.getWidth();
                Config.circuitHauteur = stage.getHeight();
            }

            Config.circuitAfficherDiagramme2D = diagramme2DCheckMenuItem.isSelected();
            Config.circuitAfficherDiagramme3D = diagramme3DCheckMenuItem.isSelected();
            Config.circuitAfficherListeDesComposants = listeDesComposantsCheckMenuItem.isSelected();
            Config.circuitAfficherBarreDOutils = barreDOutilsCheckMenuItem.isSelected();
            Config.circuitAfficherTitre = titreCheckMenuItem.isSelected();

            Config.sauvegarder();
        });
    }

    // FXML
    @FXML
    private ListView<Composant> composantsListView;
    @FXML
    private VBox vBox2D, vBox3D;
    @FXML
    private SubScene subSceneConcepteur,
                     subScene3D;

    // FXML fields for each CheckMenuItem in Circuit.fxml
    @FXML
    private CheckMenuItem diagramme2DCheckMenuItem, diagramme3DCheckMenuItem, listeDesComposantsCheckMenuItem, barreDOutilsCheckMenuItem, titreCheckMenuItem;

    @FXML
    private SplitPane diagrammesSplitPane;


    // ===============================
    //             INIT
    // ===============================
    @FXML
    public void initialize() {
        diagramme2DCheckMenuItem.setSelected(Config.circuitAfficherDiagramme2D);
        diagramme3DCheckMenuItem.setSelected(Config.circuitAfficherDiagramme3D);
        listeDesComposantsCheckMenuItem.setSelected(Config.circuitAfficherListeDesComposants);
        barreDOutilsCheckMenuItem.setSelected(Config.circuitAfficherBarreDOutils);
        titreCheckMenuItem.setSelected(Config.circuitAfficherTitre);

        diagrammesSplitPane.setDividerPosition(0, Config.circuitDiagrammesSplitPanePosition0);
        diagrammesSplitPane.setDividerPosition(1, Config.circuitDiagrammesSplitPanePosition1);

        composantsListView.setCellFactory(item ->
                new ComposantsListCell(circuit));

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("."));
        try {
//            Stage stage = fxmlLoader.load(DansLChampApp.class.getResourceAsStream("fxml/Concepteur.fxml");
            subSceneConcepteur.setRoot(fxmlLoader.load(DansLChampApp.class.getResourceAsStream("fxml/Concepteur.fxml")));


            vBox2D.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                if (event.getTarget().getClass() == Group.class) {

                } else if (concepteurControleur.getChamp().isSelected()) {
                    Bobine bob = new Bobine(0, 0, 0);
                    for (int i = 0; i < circuit.getComposantsSansFils().size(); i++) {
                        if (circuit.getComposantsSansFils().get(i).getClass() == Bobine.class) {
                            bob = (Bobine) circuit.getComposantsSansFils().get(i);
                            break;
                        }
                    }

                    javafx.scene.control.Label valeursLabel = new javafx.scene.control.Label();
                    VBox infobulleVBox = new VBox(
                            new Label("champ magnétique"),
                            new Separator(Orientation.HORIZONTAL),
                            valeursLabel);
//TODO faire les distances correct
                    infobulleC.setGraphic(infobulleVBox);
                    double x = bob.getSymbole2D().getLayoutX();
                    double y = bob.getSymbole2D().getLayoutY();
                    double d = Math.hypot((bob.getPosX() - event.getX()), (bob.getPosY() - event.getY()));
                    double B = 4 * Math.PI * (bob.nombreDeSpires.getValeur(Composant.Unite.UNITE) / bob.longueur.getValeur(Composant.Unite.UNITE)) * bob.courant.getValeur(Composant.Unite.UNITE) / 100;
                    double Bext = (B * Math.pow(bob.rayon.getValeur(Composant.Unite.UNITE), 2)) / (2 * Math.pow(Math.pow(bob.rayon.getValeur(Composant.Unite.UNITE), 2) + Math.pow(d, 2), (3 / 2)));
                    valeursLabel.setText("La force du champ magnétique à " + d + " mètres de la bobine est de: " + "\n" + Bext + "e-5 T");     // Clear


                    infobulleC.show(vBox2D, event.getScreenX(), event.getScreenY());
                }
            });

            vBox2D.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
                infobulleC.hide();

            });
            concepteurControleur = fxmlLoader.getController();
            concepteurControleur.setCircuit(circuit);
            concepteurControleur.setCircuitControleur(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        composantsListView.getSelectionModel().selectedItemProperty().addListener((l, old, composant) -> {
            if (composant != null) {
                composant.getSymbole2D().getChildren().forEach(node -> {
                    if (node instanceof Group) {
                        ((Group) node).getChildren().forEach(subnodes -> subnodes.setStyle("-fx-stroke: blue"));
                    }
                });
            }
            if (old != null) {
                old.getSymbole2D().getChildren().forEach(node -> {
                    if (node instanceof Group) {
                        ((Group) node).getChildren().forEach(subnodes -> subnodes.setStyle("-fx-stroke: black"));    // FIXME: 2023-04-25 old.getStrokeColor()
                    }
                });
            }
        });

        subSceneConcepteur.heightProperty().bind(vBox2D.heightProperty());
        subSceneConcepteur.widthProperty().bind(vBox2D.widthProperty());
        subScene3D.heightProperty().bind(vBox3D.heightProperty());
        subScene3D.widthProperty().bind(vBox3D.widthProperty());
    }

    // ===============================
    //         ACTIONS MENU
    // ===============================

    /**
     * Charge un circuit depuis SVG.
     *
     * @param file Le fichier à charger.
     */
    public void chargerCircuit(@Nullable File file) throws FileNotFoundException {
        circuit = Circuit.chargerCircuit(file);
        pousserCircuitRecent(file);
        fichierEnregistrement = file;
        composantsListView.setItems(circuit.getComposantsSansFils());

        concepteurControleur.diagrammeAnchorPane.getChildren().setAll(circuit.getDiagramme2D().getGroup());
        concepteurControleur.setCircuit(circuit);

        init3D();
    }

    private void init3D() {
        Group group3D = circuit.getDiagramme3D().getGroup();
        subScene3D.addEventHandler(ScrollEvent.SCROLL, event -> {
            group3D.translateZProperty().set(group3D.getTranslateZ() - event.getDeltaY());
        });


        Camera camera = new PerspectiveCamera();
        subScene3D.setFill(Color.WHITE);
        subScene3D.setCamera(camera);

        VBox vBox = new VBox(group3D);
        vBox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.CENTER);
        subScene3D.setRoot(hBox);
        circuit.getDiagramme3D().initMouseControl(group3D, subScene3D);
    }

    private static void pousserCircuitRecent(File file) {
        String circuitActuel = file.getAbsolutePath();

        if (!(Config.circuitRecent2.equals(circuitActuel) || Config.circuitRecent1.equals(circuitActuel)))
            Config.circuitRecent3 = Config.circuitRecent2;
        if (!Config.circuitRecent1.equals(circuitActuel))
            Config.circuitRecent2 = Config.circuitRecent1;

        Config.circuitRecent1 = circuitActuel;
    }

    public void enregistrer() {
        if (fichierEnregistrement == null) enregistrerSous();
        else {
            try {
                Files.write(fichierEnregistrement.toPath(), Collections.singleton(FXASvg.aSvg(circuit)), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                DanslChampUtil.erreur("Impossible d'enregistrer le fichier", e.getMessage());
            }
        }
    }

    public void enregistrerSous() {
        fichierEnregistrement = FC.showSaveDialog(stage);
        if (fichierEnregistrement != null) {
            if (FC.getSelectedExtensionFilter() == EXTENSION_FILTER && !fichierEnregistrement.getPath().matches("[" + FILE_EXTENSION + "]^")) {
                fichierEnregistrement = new File(fichierEnregistrement.getPath() + FILE_EXTENSION);
            }

            enregistrer();
        }
    }

    public static void nouveau() {
        ControllerUtil.loadFenetre("Circuit.fxml", 500, 600);
    }

    public void nouveau(ActionEvent actionEvent) {
        nouveau();
    }
}
