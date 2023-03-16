package control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sim.danslchamp.circuit.Composant;
import testUtil.ListPoint2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import static sim.danslchamp.controllersApp.DanslChampApp.SVG_LOADER;

public class TestMailles extends Application {

    Map<Point, ArrayList<Composant>> connecteurs;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane(SVG_LOADER.loadSvg(".\\circuits\\circuitLC.svg"));

        connecteurs = SVG_LOADER.getSvgElementHandler().getConnecteurs();

        // DEBUG
        pane.getChildren().add(new ListPoint2D(connecteurs.keySet()).getGroupe());

        primaryStage.setScene(new Scene(pane));

        primaryStage.show();

        trouverMailles();
    }

    private void trouverMailles() {
        System.out.println("Intersections:");
                connecteurs.forEach(((point, composantes) -> {
                    if (composantes.size()>2) {
                        System.out.println(composantes + " " + point);
            }
        }));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
