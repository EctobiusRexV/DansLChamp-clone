package controllersApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerUtil {

    public static Stage loadFenetre(String path){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load(ControllerUtil.class.getResourceAsStream(path)));


            stage.setScene(scene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur dans l'ouverture de la fenêtre");
            alert.setContentText("Veuillez réessayer...");
        }
        return stage;
    }
}
