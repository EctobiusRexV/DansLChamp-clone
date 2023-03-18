package sim.danslchamp.controleurs;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXTitledPane;
import sim.danslchamp.DansLChampApp;

import java.io.File;
import java.io.FileNotFoundException;

import static sim.danslchamp.DansLChampApp.FC;
import static sim.danslchamp.DansLChampApp.SVG_LOADER;

/**
 * Contrôleur de la fenêtre de Démarrage
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 */
public class SplashScreenController {
    public BorderPane titleBar;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // FXML

    @FXML
    private Label labelHautDroite;

    @FXML
    private Label labelHautMilieu;

    @FXML
    private Label labelHautGauche;


    @FXML
    private MFXTitledPane recentsTitlePane;

    @FXML
    private MFXTitledPane deBaseTitledPane;


    // ===============================
    //             INIT
    // ===============================
    @FXML
    public void initialize() {
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(
                getCircuitVBox(".\\circuits\\circuitR.svg")
        );
        deBaseTitledPane.getStylesheets().add(getClass().getResource("titlepane.css").toExternalForm());
        deBaseTitledPane.setContent(flowPane);
        recentsTitlePane.getStylesheets().add(getClass().getResource("titlepane.css").toExternalForm());

    }

    private VBox getCircuitVBox(String filename) {
        VBox vBox = new VBox(
                SVG_LOADER.loadSvg(filename),
                new Label(filename)
        );

        vBox.setUserData(new File(filename));
        vBox.setOnMouseClicked(this::circuitPressed);
        return vBox;
    }

    // ===============================
    //         ACTIONS UI
    // ===============================
    @FXML
    void circuitPressed(MouseEvent event) {
        try {
            DansLChampApp.showConcepteurDeCircuit((File) ((VBox) event.getSource()).getUserData());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage()); // Au moins on va connaître le bug.
        }
    }


    // ===============================
    //         ACTIONS MENU
    // ===============================
    @FXML
    void fermerApp()  {
        Platform.exit();
    }

    @FXML
    void showBibliotheque() {
        DansLChampApp.loadFenetre("Bibliotheque.fxml").show();
    }

    @FXML
    void ouvrirCircuit() {
        try {
            File file = FC.showOpenDialog(stage);
            if (file != null)
                DanslChampApp.showConcepteurDeCircuit(file);  // Ne pas ouvrir si aucune sélection n'est faite!
        } catch (FileNotFoundException neSappliquePas) {
        }
    }

    @FXML
    void showAide() {
        DansLChampApp.loadFenetre("Aide.fxml").show();
    }

    @FXML
    void showAPropos() {
        DansLChampApp.loadFenetre("APropos.fxml").show();
    }

    public void resizeApp(ActionEvent actionEvent) {
        if(stage.isMaximized()) {
            stage.setMaximized(false);
        }
        else stage.setMaximized(true);
    }

    public void minimizeApp(ActionEvent actionEvent) {
        stage.setIconified(true);
    }
    public void mouvePressed(MouseEvent event) {
        double xOffset = event.getX();
        double yOffset = event.getY();

        titleBar.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - xOffset);
            stage.setY(dragEvent.getScreenY() - yOffset);
        });
    }
}