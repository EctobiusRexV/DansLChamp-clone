package sim.danslchamp.controllersApp;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import sim.danslchamp.svg.SvgLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static sim.danslchamp.controllersApp.DanslChampApp.FC;
import static sim.danslchamp.controllersApp.DanslChampApp.SVG_LOADER;

/**
 * Contrôleur de la fenêtre Concepteur de circuit
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 */
public class ControllerPrincipal {

    private Stage stage;
    void setStage(Stage stage) {
        this.stage = stage;

        this.stage.setOnCloseRequest(event -> {
            new Alert(Alert.AlertType.CONFIRMATION,
                    "Êtes-vous certain de vouloir quitter?",
                    ButtonType.YES, ButtonType.NO).showAndWait()
                    .ifPresent(buttonType -> {
                        if (buttonType == ButtonType.NO)
                            event.consume();        // fixme
                    });
        });
    }

    // FXML

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vBoxScrollPane;

    // ===============================
    //             INIT
    // ===============================
    @FXML
    public void initialize() {
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
        } catch (FileNotFoundException neSappliquePas) {}
    }

    /**
     * Charge un circuit depuis SVG.
     * @param file Le fichier à charger.
     */
    void chargerCircuit(@Nullable File file) throws FileNotFoundException {
        borderPane.setCenter(
                file == null ? new Group() :
                        SVG_LOADER.loadSvg(
                                new FileInputStream(file))); // Group
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
