package circuit;

import javafx.application.Application;
import javafx.stage.Stage;
import sim.danslchamp.circuit.Jonction;

import java.util.List;

import static sim.danslchamp.DansLChampApp.SVG_LOADER;

public class TestJonctions extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SVG_LOADER.loadSvg(".\\circuits\\circuitLC.svg");

        List<Jonction> jonctions = SVG_LOADER.getSvgElementHandler().getJonctions();

        jonctions.forEach(jonction -> {
            if (jonction.getComposants().size()==1) System.out.println(jonction.getComposants().get(0) + " n'est pas connecté ! "+jonction.getComposants());
        });

        System.out.println();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
