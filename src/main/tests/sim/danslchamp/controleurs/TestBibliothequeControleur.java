package sim.danslchamp.controleurs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestBibliothequeControleur extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("."));

        primaryStage.setScene(
                new Scene(loader.load(this.getClass().getResourceAsStream("Bibliotheque.fxml"))));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
