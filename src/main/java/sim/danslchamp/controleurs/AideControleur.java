package sim.danslchamp.controleurs;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AideControleur implements Initializable {
    public TextArea textAreaAide;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaAide.setText("");
        textAreaAide.setEditable(false);
    }


    public void fermerApp() {
        ((Stage)textAreaAide.getScene().getWindow()).close();
    }

    public void mouvePressed(MouseEvent mouseEvent) {
        ControllerUtil.mouveStageUtil(mouseEvent);
    }
}
