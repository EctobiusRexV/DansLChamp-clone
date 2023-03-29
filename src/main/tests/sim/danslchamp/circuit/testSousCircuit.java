package sim.danslchamp.circuit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.File;

public class testSousCircuit extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Circuit circuit = Circuit.chargerCircuit(new File(".\\circuits\\circuitLC.svg"));

        circuit.getCircuit().forEach(System.out::println);

        primaryStage.setScene(new Scene(circuit.getDiagramme2D().getGroup()));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
