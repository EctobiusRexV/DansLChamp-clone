package controllersApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DanslChampApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Scene scene = new Scene(fxmlLoader.load(this.getClass().getResourceAsStream("SplashScreenController.fxml")));

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
