package sim.danslchamp.controleurs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ControllerUtil {

    public static Stage loadFenetre(String path){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load(ControllerUtil.class.getResourceAsStream("..\\fxml\\" + path)));

            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur dans l'ouverture de la fenêtre");
            alert.setContentText("Veuillez réessayer...");
        }
        return stage;
    }

    public static void resizeUtil(MouseEvent event){
        Stage stage = ((Stage) ((Node)event.getTarget()).getScene().getWindow());
        double originalWidth = stage.getWidth();
        ((Node) event.getTarget()).setOnMouseDragged(dragEvent -> {
            //filtre pour l'agrandissement fluide par les coter ouest
            if (((Node) event.getTarget()).getCursor().equals(Cursor.W_RESIZE) ||
                    ((Node) event.getTarget()).getCursor().equals(Cursor.SW_RESIZE)) {

                if (stage.getWidth() != stage.getMinWidth()) stage.setX(dragEvent.getScreenX() - event.getX());
                stage.setWidth(Math.max(event.getScreenX() - dragEvent.getScreenX() + originalWidth, stage.getMinWidth()));
            } else if (!((Node) event.getTarget()).getCursor().equals(Cursor.S_RESIZE)) {
                stage.setWidth(Math.max(dragEvent.getScreenX() - event.getScreenX() + originalWidth, stage.getMinWidth()));
            }

            if (!(((Node) event.getTarget()).getCursor().equals(Cursor.W_RESIZE) ||
                    ((Node) event.getTarget()).getCursor().equals(Cursor.E_RESIZE))) {

                stage.setHeight(Math.max(dragEvent.getScreenY() - event.getScreenY() + event.getSceneY(), stage.getMinHeight()));
            }
        });
    }

    public static void mouveStageUtil(MouseEvent event){
        Stage stage = ((Stage) ((Node)event.getTarget()).getScene().getWindow());
        Boolean wasMaximised;
        if(stage.isMaximized()){
            stage.setMaximized(false);
            wasMaximised = true;

        } else {
            wasMaximised = false;
        }
        ((Node)event.getTarget()).setOnMouseDragged(dragEvent -> {
            if(wasMaximised){
                stage.setX(event.getX());
            }
            stage.setX(dragEvent.getScreenX() - event.getX());
            stage.setY(dragEvent.getScreenY() - event.getY());
        });
    }
}
