package sim.danslchamp.controleurs;

import javafx.application.Application;
import javafx.stage.Stage;
import sim.danslchamp.DansLChampApp;

public class BievenueTestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DansLChampApp.loadFenetre("Bienvenue.fxml").show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
