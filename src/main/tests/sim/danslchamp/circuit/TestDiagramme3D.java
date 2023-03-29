package sim.danslchamp.circuit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class TestDiagramme3D extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Circuit circuit = Circuit.chargerCircuit(new File(".\\circuits\\circuitLC.svg"));

        primaryStage.setScene(new Scene(circuit.getDiagramme3D().getGroup()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
