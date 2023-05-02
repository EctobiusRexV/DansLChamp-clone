package sim.danslchamp.controleurs;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AideControleur extends ParentControleur implements Initializable {
    public WebView textAreaAide;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaAide.getEngine().loadContent("En cours de développement...");
    }


    public void fermerApp() {
        ((Stage)textAreaAide.getScene().getWindow()).close();
    }
}
