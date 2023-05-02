package sim.danslchamp.controleurs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.circuit.Circuit;

import java.io.File;

public class CircuitTestApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("."));

        primaryStage.setScene(new Scene(fxmlLoader.load(DansLChampApp.class.getResourceAsStream("fxml/Circuit.fxml"))));
        if (getParameters().getRaw().size() > 0)
            ((CircuitControleur) fxmlLoader.getController()).chargerCircuit(new File("circuits/" + getParameters().getRaw().get(0)));
        ((CircuitControleur) fxmlLoader.getController()).setStage(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
