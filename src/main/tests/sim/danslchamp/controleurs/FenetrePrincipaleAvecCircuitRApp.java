package sim.danslchamp.controleurs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class FenetrePrincipaleAvecCircuitRApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("."));

        primaryStage.setScene(
                new Scene(loader.load(this.getClass().getResourceAsStream("Concepteur.fxml"))));

        ConcepteurControleur concepteurControleur = loader.getController();

        concepteurControleur.setStage(primaryStage);
        concepteurControleur.chargerCircuit(new File(".\\circuits\\circuitR.svg"));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
