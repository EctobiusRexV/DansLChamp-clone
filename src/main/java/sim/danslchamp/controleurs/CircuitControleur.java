package sim.danslchamp.controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import sim.danslchamp.Config;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.Util.ComposantsListCell;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    private ConcepteurControleur concepteurControleur;

    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);

        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                concepteurControleur.annulerEdition();
            }
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


    // ===============================
    //             INIT
    // ===============================
    @FXML
    public void initialize() {
        composantsListView.setCellFactory(item ->
                new ComposantsListCell(circuit));

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("."));
        try {
//            Stage stage = fxmlLoader.load(DansLChampApp.class.getResourceAsStream("fxml/Concepteur.fxml");
            subSceneConcepteur.setRoot(fxmlLoader.load(DansLChampApp.class.getResourceAsStream("fxml/Concepteur.fxml")));
            concepteurControleur = fxmlLoader.getController();
            concepteurControleur.setCircuit(circuit);
//            ((ConcepteurControleur) fxmlLoader.getController()).setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        subSceneConcepteur.heightProperty().bind(vBox2D.heightProperty());
        subSceneConcepteur.widthProperty().bind(vBox2D.widthProperty());
        subScene3D.heightProperty().bind(vBox3D.heightProperty());
        subScene3D.widthProperty().bind(vBox3D.widthProperty());
    }

    // ===============================
    //         ACTIONS MENU
    // ===============================

    @FXML
    void enregistrerCircuit(ActionEvent event) {

    }

    /**
     * Charge un circuit depuis SVG.
     *
     * @param file Le fichier à charger.
     */
    public void chargerCircuit(@Nullable File file) throws FileNotFoundException {
        circuit = Circuit.chargerCircuit(file);
        pousserCircuitRecent(file);
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
        Config.circuitRecent3 = Config.circuitRecent2;
        Config.circuitRecent2 = Config.circuitRecent1;
        Config.circuitRecent1 = file.getAbsolutePath();
    }
}
