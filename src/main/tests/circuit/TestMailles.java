package circuit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sim.danslchamp.circuit.Jonction;
import testUtil.ListPoint2D;

import java.util.List;

import static sim.danslchamp.controllersApp.DanslChampApp.SVG_LOADER;

public class TestMailles extends Application {

    List<Jonction> jonctions;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane(SVG_LOADER.loadSvg(".\\circuits\\circuitLC.svg"));

        jonctions = SVG_LOADER.getSvgElementHandler().getJonctions();

        // DEBUG
        pane.getChildren().add(new ListPoint2D(jonctions).getGroupe());

        primaryStage.setScene(new Scene(pane));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
