package sim.danslchamp.controleurs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class ParentControleur {
    Stage stage;

    @FXML
    BorderPane root;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean onHidden, Boolean onShown) {
                if(ov.getValue()){
                    root.setStyle("-fx-background-color: dimgray");
                }
                else{
                    root.setStyle("-fx-background-color: lightgray");
                }
            }
        });
    }

    //fermer la fenetre
    @FXML
    void fermerApp(ActionEvent e){
        stage.close();
    }

    //maximiser la fenetre
    @FXML
    void resizeApp(ActionEvent e) {
        if( stage.isMaximized()) {
            stage.setMaximized(false);
        }
        else  stage.setMaximized(true);
    }

    //minimiser la fenetre
    public void minimizeApp(ActionEvent actionEvent) {
        stage.setIconified(true);
    }

    @FXML
    void showBibliotheque() {
        ControllerUtil.loadFenetre("Bibliotheque.fxml", 610, 700);
    }

    @FXML
    void showAide() {
        ControllerUtil.loadFenetre("Aide.fxml", 328, 500);

    }

    @FXML
    void showAPropos() {
        Stage stageAPropos = ControllerUtil.loadFenetre("APropos.fxml", 328, 500);
        stageAPropos.setWidth(500);
        stageAPropos.setHeight(328);
    }



    public void dragResize(MouseEvent event) {
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
    public void mouvePressed(MouseEvent event) {
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
