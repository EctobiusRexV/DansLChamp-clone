package sim.danslchamp.controleurs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.circuit.Circuit;

public class ConcepteurTestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();

        primaryStage.setScene(new Scene(fxmlLoader.load(DansLChampApp.class.getResourceAsStream("fxml/Concepteur.fxml"))));
        ((ConcepteurControleur) fxmlLoader.getController()).setCircuit(new Circuit());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}