package controllersApp;

import danslchamp.Circuit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.VBox;

public class ControllerPrincipal {

    @FXML
    private Group group3D;

    @FXML
    private VBox vBoxScrollPane;

    @FXML
    void FermerRetour(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void enregistrerCircuit(ActionEvent event) {

    }

    @FXML
    void ouvrirBibliothèque(ActionEvent event) {

    }

    @FXML
    void ouvrirCircuit(ActionEvent event) {

    }

    @FXML
    void ouvrirInfoApp(ActionEvent event) {

    }

    @FXML
    void ouvrirInfoCreateur(ActionEvent event) {

    }

    void setCircuit(Circuit circuit){

    }

}

