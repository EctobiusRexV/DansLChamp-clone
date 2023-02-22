package controllersApp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashScreenController {

    @FXML
    private Label LabelBasMilieu;

    @FXML
    private ImageView imageBasDroite;

    @FXML
    private ImageView imageBasGauche;

    @FXML
    private ImageView imageBasMilieu;

    @FXML
    private ImageView imageHautDroite;

    @FXML
    private ImageView imageHautGauche;

    @FXML
    private ImageView imageHautMilieu;

    @FXML
    private Label labelBasDroite;

    @FXML
    private Label labelBasGauche;

    @FXML
    private Label labelHautDroite;

    @FXML
    private Label labelHautGauche;

    @FXML
    private Label labelHautMilieu;

    @FXML
    void CircuitPressed(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load(this.getClass().getResourceAsStream("3DController.fxml")));


            stage.setScene(scene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur dans l'ouverture du circuit");
            alert.setContentText("Veuillez réessayer...");
        }
        ControllerPrincipal controllerPrincipal = fxmlLoader.getController();
        controllerPrincipal.setCircuit(null);
        stage.show();

    }

    @FXML
    void fermerApp(ActionEvent event) {

        Platform.exit();
    }

    @FXML
    void ouvrirBibliothèque(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load(this.getClass().getResourceAsStream("ControllerBibli.fxml")));


            stage.setScene(scene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur dans l'ouverture de la fenêtre");
            alert.setContentText("Veuillez réessayer...");
        }

        stage.show();
    }

    @FXML
    void ouvrirFichier(ActionEvent event) {

    }

    @FXML
    void ouvrirInfoApp(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load(this.getClass().getResourceAsStream("ControllerAide.fxml")));


            stage.setScene(scene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur dans l'ouverture de la fenêtre");
            alert.setContentText("Veuillez réessayer...");
        }

        stage.show();
    }

    @FXML
    void ouvrirInfoCreateurs(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load(this.getClass().getResourceAsStream("ControllerAPropos.fxml")));


            stage.setScene(scene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur dans l'ouverture de la fenêtre");
            alert.setContentText("Veuillez réessayer...");
        }

        stage.show();
    }

}

