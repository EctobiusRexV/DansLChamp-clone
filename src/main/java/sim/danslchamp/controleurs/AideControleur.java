package sim.danslchamp.controleurs;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AideControleur extends ParentControleur implements Initializable {
    public TextArea textAreaAide;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaAide.setText("");
        textAreaAide.setEditable(false);
    }


    public void fermerApp() {
        ((Stage)textAreaAide.getScene().getWindow()).close();
    }
}
