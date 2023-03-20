package sim.danslchamp.controleurs;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class AideControleur implements Initializable {
    public TextArea textAreaAide;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaAide.setText("");
        textAreaAide.setEditable(false);
    }
}
