package sim.danslchamp.circuit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Jonction;

import java.io.File;

public class TestSensDuCourant extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Circuit circuit = Circuit.chargerCircuit(new File(".\\circuits\\circuitLC.svg"));

        circuit.getComposants().forEach(composant -> {
                    for (Jonction jonction : composant.getJonctions()) {
                        if (composant.getBornePositive() == null) System.out.println(composant + " n'a pas de borne positive");
                        else circuit.getDiagramme2D().getGroup().getChildren().add(
                                new Text(jonction.getPositionXY().x, jonction.getPositionXY().y + 10,
                                         jonction == composant.getBornePositive()? "+" : "-"));
                    }
                });

        primaryStage.setScene(new Scene(circuit.getDiagramme2D().getGroup()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
