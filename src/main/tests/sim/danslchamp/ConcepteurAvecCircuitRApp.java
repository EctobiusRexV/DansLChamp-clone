package sim.danslchamp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sim.danslchamp.controleurs.ConcepteurControleur;

import java.io.File;

public class ConcepteurAvecCircuitRApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DansLChampApp.showConcepteurDeCircuit(new File(".\\circuits\\circuitLC.svg"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
