package sim.danslchamp.controllersApp;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import sim.danslchamp.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

import static sim.danslchamp.controllersApp.DanslChampApp.FC;
import static sim.danslchamp.controllersApp.DanslChampApp.SVG_LOADER;

/**
 * Contrôleur de la fenêtre Concepteur de circuit
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 */
public class ControllerPrincipal {

    private ArrayList<Composante> composantes = new ArrayList<>();

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



        composantes.add(new SourceCC("1000"));
        composantes.add(new Bobine("1","1","1"));
        composantes.add(new Condensateur("1"));
        composantes.add(new Resistor("1000"));

        for (Composante c : composantes){
            VBox vBox = new VBox();

            vBox.setPrefWidth(200);

            vBox.getChildren().add(new Label(c.getName()));

            Method[] methods = c.getClass().getDeclaredMethods();

            for (Method m : methods){

                if (m.getName().startsWith("set")){
                    HBox hBox = new HBox();
                    HBox.setHgrow(hBox, Priority.ALWAYS);
                    hBox.setMinWidth(195);
                    hBox.setSpacing(10);
                    Label label = new Label(m.getName().substring(3));
                    label.setMinWidth(70);
                    hBox.getChildren().add(label);
                    TextField textField = new TextField();
                    textField.setOnKeyTyped(eh -> {
                        try {
                            if (textField.getText() != null){
                                m.invoke(c, textField.getText());
                            }

                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    hBox.getChildren().add(textField);
                    vBox.getChildren().add(hBox);
                }
            }


            vBoxScrollPane.getChildren().add(vBox);
        }


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
