package sim.danslchamp.circuit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Jonction;
import testUtil.ListPoint2D;

import java.io.File;
import java.util.List;

import static sim.danslchamp.DansLChampApp.SVG_LOADER;

public class TestNoeuds extends Application {
    List<Jonction> jonctions;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Circuit circuit = Circuit.chargerCircuit(new File(".\\circuits\\circuitLC.svg"));

        jonctions = circuit.getJonctions();

        // DEBUG
        Pane pane = new Pane(circuit.getDiagramme2D().getGroup());
        pane.getChildren().add(new ListPoint2D(jonctions.stream().filter(Jonction::estNoeud).toList()).getGroupe());

        primaryStage.setScene(new Scene(pane));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
