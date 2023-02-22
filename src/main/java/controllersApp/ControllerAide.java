package controllersApp;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAide implements Initializable {
    public TextArea textAreaAide;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaAide.setText("");
        textAreaAide.setEditable(false);
    }
}
