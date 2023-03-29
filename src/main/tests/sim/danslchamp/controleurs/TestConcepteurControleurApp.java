package sim.danslchamp.controleurs;

import javafx.application.Application;
import javafx.stage.Stage;
import sim.danslchamp.DansLChampApp;

public class TestConcepteurControleurApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DansLChampApp.loadFenetre("Concepteur.fxml").show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
