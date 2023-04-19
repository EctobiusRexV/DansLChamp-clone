package sim.danslchamp.controleurs;

import javafx.application.Application;
import javafx.stage.Stage;
import sim.danslchamp.DansLChampApp;

public class CircuitTestApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerUtil.loadFenetre("Circuit.fxml", 600, 500).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
