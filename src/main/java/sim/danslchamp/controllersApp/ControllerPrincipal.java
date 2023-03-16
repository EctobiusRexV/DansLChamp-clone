package sim.danslchamp.controllersApp;

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
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;
import sim.danslchamp.Util.ComposantesListCell;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.FileNotFoundException;

import static sim.danslchamp.controllersApp.DanslChampApp.FC;

/**
 * Contrôleur de la fenêtre Concepteur de circuit
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 * @author Thierry Rhéaume
 */
public class ControllerPrincipal {

    private Circuit circuit;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    private Stage stage;

    void setStage(Stage stage) {
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
    void chargerCircuit(@Nullable File file) throws FileNotFoundException {
        SvgLoader loader = new SvgLoader();
        circuit = Circuit.chargerCircuit(file);

        Group group = circuit.getGroupe2D();
        vBox2D.addEventHandler(ScrollEvent.SCROLL, event -> {
            if (group.getScaleX() + event.getDeltaY()/100 < 0) return; // empêcher d'obtenir un scale négatif

            group.scaleXProperty().set(group.getScaleX() + event.getDeltaY() / 100);
            group.scaleYProperty().set(group.getScaleY() + event.getDeltaY() / 100);

        });
        vBox2D.getChildren().setAll(circuit.getGroupe2D());


        Group group3D = circuit.getDiagramme3D();
        subScene3D.addEventHandler(ScrollEvent.SCROLL, event -> {
            group.translateZProperty().set(group3D.getTranslateZ() + event.getDeltaY());
        });

        // Centrer
        group3D.translateXProperty().set(subScene3D.getWidth() + 100);
        group3D.translateYProperty().set(subScene3D.getHeight() + 150);
        //group3D.translateZProperty().set(-500);

        Camera camera = new PerspectiveCamera();
//        scene.setFill(Color.TRANSPARENT);
        subScene3D.setFill(Color.LIGHTGRAY);
        subScene3D.setCamera(camera);
        subScene3D.setRoot(circuit.getDiagramme3D());

        initMouseControl(group3D, subScene3D);

        composantesListView.setItems(circuit.getComposants());
    }

    private void initMouseControl(Group group, SubScene scene) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });
    }


    @FXML
    void showBibliotheque() {
        ControllerUtil.loadFenetre("ControllerBibli.fxml").show();
    }

    @FXML
    void showAide() {
        ControllerUtil.loadFenetre("ControllerAide.fxml").show();
    }

    @FXML
    void showAPropos() {
        ControllerUtil.loadFenetre("ControllerAPropos.fxml").show();
    }
}
