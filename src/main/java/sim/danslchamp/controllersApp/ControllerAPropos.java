package sim.danslchamp.controllersApp;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAPropos implements Initializable {
    public TextArea textAreaAPropos;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaAPropos.setText("");
        textAreaAPropos.setEditable(false);
    }
}
