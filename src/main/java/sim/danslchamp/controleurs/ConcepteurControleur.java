package sim.danslchamp.controleurs;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import sim.danslchamp.Config;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;
import sim.danslchamp.Util.ComposantesListCell;
import sim.danslchamp.svg.SvgLoader;

import java.awt.*;
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
public class ConcepteurControleur {

    private Circuit circuit;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;

        this.stage.setOnCloseRequest(event -> {
            new Alert(Alert.AlertType.CONFIRMATION,
                    "Êtes-vous certain de vouloir quitter?",
                    ButtonType.YES, ButtonType.NO).showAndWait()
                    .ifPresent(buttonType -> {
                        if (buttonType == ButtonType.NO)
                            event.consume();
                    });
        });
    }


    // FXML

    @FXML
    private BorderPane borderPane;
    @FXML
    private ListView<Composant> composantesListView;
    @FXML
    private VBox vBox2D, vBox3D;
    @FXML
    private SubScene subScene3D;


    // ===============================
    //             INIT
    // ===============================
    @FXML
    public void initialize() {
        composantesListView.setCellFactory(item ->
                new ComposantesListCell());

        subScene3D.heightProperty().bind(vBox3D.heightProperty());
        subScene3D.widthProperty().bind(vBox3D.widthProperty());
    }

    // ===============================
    //         ACTIONS MENU
    // ===============================
    @FXML
    void fermerRetour() {

        Platform.exit();
    }

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
        subScene3D.setFill(Color.LIGHTGRAY);
        subScene3D.setCamera(camera);
        subScene3D.setRoot(group3D);
    }

    @FXML
    void showBibliotheque() {
        DansLChampApp.loadFenetre("Bibliotheque.fxml").show();
    }

    @FXML
    void showAide() {
        DansLChampApp.loadFenetre("Aide.fxml").show();
    }

    @FXML
    void showAPropos() {
        DansLChampApp.loadFenetre("APropos.fxml").show();
    }

    public class Concepteur {
        Point posXY;
        boolean vertical;

        public void addComposant(Class<Composant> composantClass, int posX, int posY, boolean rotation90) {

        }
    }

    private static void pousserCircuitRecent(File file) {
        Config.circuitRecent3 = Config.circuitRecent2;
        Config.circuitRecent2 = Config.circuitRecent1;
        Config.circuitRecent1 = file.getAbsolutePath();
    }
}
