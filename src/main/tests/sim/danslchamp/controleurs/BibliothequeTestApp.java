package sim.danslchamp.controleurs;

import javafx.application.Application;
import javafx.stage.Stage;
import sim.danslchamp.DansLChampApp;

public class BibliothequeTestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DansLChampApp.loadFenetre("Bibliotheque.fxml").show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
