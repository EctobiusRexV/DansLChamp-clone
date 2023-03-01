package sim.danslchamp.controllersApp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

import static sim.danslchamp.controllersApp.DanslChampApp.FC;

/**
 * Contrôleur de la fenêtre de Démarrage
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 */
public class SplashScreenController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // FXML

    @FXML
    private Label LabelBasMilieu;

    @FXML
    private ImageView imageBasDroite;

    @FXML
    private ImageView imageBasGauche;

    @FXML
    private ImageView imageBasMilieu;

    @FXML
    private ImageView imageHautDroite;

    @FXML
    private ImageView imageHautGauche;

    @FXML
    private ImageView imageHautMilieu;

    @FXML
    private Label labelBasDroite;

    @FXML
    private Label labelBasGauche;

    @FXML
    private Label labelHautDroite;

    @FXML
    private Label labelHautGauche;

    @FXML
    private Label labelHautMilieu;

    // ===============================
    //             INIT
    // ===============================
    @FXML
    public void initialize() {

    }

    // ===============================
    //         ACTIONS UI
    // ===============================
    @FXML
    void circuitPressed(MouseEvent event) {
        try {
            DanslChampApp.showConcepteurDeCircuit((File) ((VBox) event.getSource()).getUserData());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage()); // Au moins on va connaître le bug.
        }
    }


    // ===============================
    //         ACTIONS MENU
    // ===============================
    @FXML
    void fermerApp() {
        Platform.exit();
    }

    @FXML
    void showBibliotheque() {
        ControllerUtil.loadFenetre("ControllerBibli.fxml").show();
    }

    @FXML
    void ouvrirCircuit() {
        try {
            File file = FC.showOpenDialog(stage);
            if (file != null) DanslChampApp.showConcepteurDeCircuit(file);  // Ne pas ouvrir si aucune sélection n'est faite!
        } catch (FileNotFoundException neSappliquePas) {}
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
