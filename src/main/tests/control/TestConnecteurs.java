package control;

import javafx.application.Application;
import javafx.stage.Stage;
import sim.danslchamp.circuit.Composant;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import static sim.danslchamp.controllersApp.DanslChampApp.SVG_LOADER;

public class TestConnecteurs extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SVG_LOADER.loadSvg(".\\circuits\\circuitLC.svg");

        Map<Point, ArrayList<Composant>> connecteurs = SVG_LOADER.getSvgElementHandler().getConnecteurs();

        connecteurs.forEach((point, composantes) -> {
            if (composantes.size()==1) System.out.println(point + " n'est pas connecté ! "+composantes);
        });

        System.out.println();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
