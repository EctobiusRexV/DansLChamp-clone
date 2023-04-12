package sim.danslchamp.controleurs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ControllerUtil {

    public static Stage loadFenetre(String path, double minHeight, double minWidht){
        Stage stage = loadStage(path, minHeight, minWidht);
        stage.show();
        return stage;
    }

    public static Stage loadStage(String path, double minHeight, double minWidht){
        Stage stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ControllerUtil.class.getResource("."));

            Scene scene = new Scene(fxmlLoader.load(ControllerUtil.class.getResourceAsStream("../fxml/" + path)));

            ParentControleur bienvenueControleur = fxmlLoader.getController();
            bienvenueControleur.setStage(stage);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMinHeight(minHeight);
            stage.setMinWidth(minWidht);
            stage.setResizable(true);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur dans l'ouverture de la fenêtre");
            alert.setContentText("Veuillez réessayer...");
        }
        return stage;
    }



}
