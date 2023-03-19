package sim.danslchamp.controleurs;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXTitledPane;
import javafx.stage.StageStyle;
import sim.danslchamp.controllersApp.svg.ControllerBibli;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        SvgLoader svgLoader = new SvgLoader(null);
        VBox vBox = new VBox(
                svgLoader.loadSvg(filename),
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
        try {
            Stage bibliStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();

            Scene scene = new Scene(fxmlLoader.load(this.getClass().getResourceAsStream("ControllerBibli.fxml")));

           ControllerBibli controllerBibli = fxmlLoader.getController();

            controllerBibli.setStage(bibliStage);

            bibliStage.setScene(scene);
            bibliStage.initStyle(StageStyle.UNDECORATED);
            bibliStage.setResizable(true);
            bibliStage.setMinWidth(610.0);
            bibliStage.setMinWidth(468.0);
            bibliStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        titleBar.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - event.getX());
            stage.setY(dragEvent.getScreenY() - event.getY());
        });
    }

    public void dragResize(MouseEvent event) {
        double originalWidth = stage.getWidth();
        ((Node)event.getTarget()).setOnMouseDragged(dragEvent ->{

            //filtre pour l'agrandissement fluide par les coter ouest
            if(((Node)event.getTarget()).getCursor().equals(Cursor.W_RESIZE) ||
                    ((Node)event.getTarget()).getCursor().equals(Cursor.SW_RESIZE) ) {

                if(stage.getWidth() != stage.getMinWidth()) stage.setX(dragEvent.getScreenX() - event.getX());
                stage.setWidth(Math.max(event.getScreenX() - dragEvent.getScreenX() + originalWidth, stage.getMinWidth()));
            }

            else if(!((Node) event.getTarget()).getCursor().equals(Cursor.S_RESIZE)){
                stage.setWidth(Math.max(dragEvent.getScreenX() - event.getScreenX() + originalWidth, stage.getMinWidth()));
            }

            if(!(((Node) event.getTarget()).getCursor().equals(Cursor.W_RESIZE) ||
                    ((Node) event.getTarget()).getCursor().equals(Cursor.E_RESIZE))){

                stage.setHeight(Math.max(dragEvent.getScreenY() - event.getScreenY() + event.getSceneY(), stage.getMinHeight()));
            }

        });
    }

}
