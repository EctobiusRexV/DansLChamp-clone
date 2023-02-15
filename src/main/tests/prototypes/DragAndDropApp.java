package prototypes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DragAndDropApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("."));

        primaryStage.setScene(
                new Scene(loader.load(this.getClass().getResourceAsStream("DragAndDrop.fxml"))));

        primaryStage.show();
    }

    public static void main(String[] args) {
        DragAndDropApp.launch();
    }
}
