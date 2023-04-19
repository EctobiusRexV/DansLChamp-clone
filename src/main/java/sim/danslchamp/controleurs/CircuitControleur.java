package sim.danslchamp.controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Nullable;
import sim.danslchamp.Config;
import sim.danslchamp.Util.ComposantesListCell;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;

import java.io.File;
import java.io.FileNotFoundException;

import static sim.danslchamp.DansLChampApp.FC;

/**
 * Contrôleur de la fenêtre Concepteur de circuit
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 * @author Thierry Rhéaume
 */
public class CircuitControleur extends ParentControleur {

    private Circuit circuit;

//    private Stage stage;
//
//    public void setStage(Stage stage) {
//        this.stage = stage;
//
//        this.stage.setOnCloseRequest(event -> {
//            new Alert(Alert.AlertType.CONFIRMATION,
//                    "Êtes-vous certain de vouloir quitter?",
//                    ButtonType.YES, ButtonType.NO).showAndWait()
//                    .ifPresent(buttonType -> {
//                        if (buttonType == ButtonType.NO)
//                            event.consume();
//                    });
//        });
//    }


    // FXML
    @FXML
    private ListView<Composant> composantesListView;
    @FXML
    private VBox vBox2D, vBox3D;
    @FXML
    private SubScene subScene3D;

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
        composantesListView.setCellFactory(item ->
                new ComposantesListCell(circuit));

        subScene3D.heightProperty().bind(vBox3D.heightProperty());
        subScene3D.widthProperty().bind(vBox3D.widthProperty());
    }

    // ===============================
    //         ACTIONS MENU
    // ===============================

    @FXML
    void enregistrerCircuit(ActionEvent event) {

    }

    @FXML
    void ouvrirCircuit() {
        try {
            chargerCircuit(FC.showOpenDialog(stage));   // Nullable
        } catch (FileNotFoundException neSappliquePas) {
        }
    }

    /**
     * Charge un circuit depuis SVG.
     *
     * @param file Le fichier à charger.
     */
    public void chargerCircuit(@Nullable File file) throws FileNotFoundException {
        circuit = Circuit.chargerCircuit(file);
        pousserCircuitRecent(file);
        composantesListView.setItems(circuit.getComposants());

        vBox2D.getChildren().setAll(circuit.getDiagramme2D().getGroup());
        vBox2D.addEventHandler(ScrollEvent.SCROLL, event -> {
            if (vBox2D.getScaleX() + event.getDeltaY() / 100 < 0) return; // empêcher d'obtenir un scale négatif

            vBox2D.scaleXProperty().set(vBox2D.getScaleX() + event.getDeltaY() / 100);
            vBox2D.scaleYProperty().set(vBox2D.getScaleY() + event.getDeltaY() / 100);

        });
        Group group3D = circuit.getDiagramme3D().getGroup();
        subScene3D.addEventHandler(ScrollEvent.SCROLL, event -> {
            group3D.translateZProperty().set(group3D.getTranslateZ() + event.getDeltaY());
        });

        // Centrer
        group3D.translateXProperty().set(subScene3D.getWidth() + 100);
        group3D.translateYProperty().set(subScene3D.getHeight() + 150);
        //group3D.translateZProperty().set(-500);

        Camera camera = new PerspectiveCamera();
//        scene.setFill(Color.TRANSPARENT);
        subScene3D.setFill(Color.WHITE);
        subScene3D.setCamera(camera);
        subScene3D.addEventHandler(EventType.ROOT, (a) -> {
            if (subScene3D.getWidth() / 2 != group3D.getLayoutX()) {
                group3D.setLayoutX(subScene3D.getWidth() / 3);
            }
        });
        subScene3D.setRoot(group3D);
        circuit.getDiagramme3D().initMouseControl(group3D, subScene3D);
    }

    private static void pousserCircuitRecent(File file) {
        Config.circuitRecent3 = Config.circuitRecent2;
        Config.circuitRecent2 = Config.circuitRecent1;
        Config.circuitRecent1 = file.getAbsolutePath();
    }
}
