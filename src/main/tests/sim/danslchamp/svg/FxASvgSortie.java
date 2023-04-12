package sim.danslchamp.svg;

import javafx.application.Application;
import javafx.stage.Stage;
import sim.danslchamp.circuit.Circuit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FxASvgSortie extends Application {

    void nodeToSvgCircuitREnSerie() {
        Path entree = Path.of("./circuits/circuitR.svg");
        Path sortie = Path.of("./circuitsExportsTests/" + entree.getFileName().toString().split("\\.")[0] + LocalDateTime.now().toString().replaceAll(":", "") + ".svg");
        try {
            Circuit circuit = Circuit.chargerCircuit(entree.toFile());
            Files.write(sortie, Collections.singleton(FXASvg.aSvg(circuit)), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        nodeToSvgCircuitREnSerie();
    }

    public static void main(String[] args) {
        launch();
    }
}
