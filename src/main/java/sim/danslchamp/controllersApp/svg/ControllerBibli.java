package sim.danslchamp.controllersApp.svg;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sim.danslchamp.Util.MathMlUtil;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerBibli implements Initializable {

    public BorderPane titleBar;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private WebView textAreaBibliotheque;

    @FXML
    private VBox vBoxPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vBoxPane.setSpacing(10);

        vBoxPane.setFillWidth(true);

        CreerVBoxs("bobine");
        CreerVBoxs("condensateur");
        CreerVBoxs("resistor");
        CreerVBoxs("source_batterie");

        //fixme
        CreerVBoxs("source_ca");
        //fixme
        CreerVBoxs("source_cc");

    }

    private void CreerVBoxs(String nom) {

        VBox vBox = new VBox();

        vBox.setSpacing(5);

        vBox.setAlignment(Pos.CENTER);

        vBox.setMinHeight(50);

        SvgLoader svgLoader = new SvgLoader();

        Group group = new Group();

        group.minHeight(50);


        group = svgLoader.loadSvg(this.getClass().getResourceAsStream(nom + ".svg"));

        Label label = new Label(nom);

        vBox.getChildren().addAll(group, label);

        vBoxPane.getChildren().add(vBox);

        vBox.setOnMouseClicked(event -> {
            try {
                if(nom.contains("source") && !nom.contains("batterie")){
                    textAreaBibliotheque.getEngine().loadContent(MathMlUtil.loadTxt("source"+".txt"));
                }else textAreaBibliotheque.getEngine().loadContent(MathMlUtil.loadTxt(nom+".txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } );
    }

    @FXML
    void fermerApp()  {
        stage.close();
    }


    @FXML
    void resizeApp(ActionEvent actionEvent) {
        if(stage.isMaximized()) {
            stage.setMaximized(false);
        }
        else stage.setMaximized(true);
    }

    @FXML
    public void minimizeApp(ActionEvent actionEvent) {
        stage.setIconified(true);
    }

    @FXML
    public void mouvePressed(MouseEvent event) {

        titleBar.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - event.getX());
            stage.setY(dragEvent.getScreenY() - event.getY());
        });
    }

    @FXML
    public void dragResize(MouseEvent event) {
        double originalWidth = stage.getWidth();
        ((Node)event.getTarget()).setOnMouseDragged(dragEvent ->{

            //filtre pour l'agrandissement fluide par les coter ouest
            if(((Node)event.getTarget()).getCursor().equals(Cursor.W_RESIZE) ||
                    ((Node)event.getTarget()).getCursor().equals(Cursor.SW_RESIZE) ) {

                if(stage.getWidth() != stage.getMinWidth()) stage.setX(dragEvent.getScreenX() - event.getX());
                stage.setWidth(Math.max(event.getScreenX() - dragEvent.getScreenX() + originalWidth, stage.getMinWidth()));
            }

            else if(!((Node) event.getTarget()).getCursor().equals(Cursor.S_RESIZE)){
                stage.setWidth(Math.max(dragEvent.getScreenX() - event.getScreenX() + event.getSceneX(), stage.getMinWidth()));
            }

            if(!(((Node) event.getTarget()).getCursor().equals(Cursor.W_RESIZE) ||
                    ((Node) event.getTarget()).getCursor().equals(Cursor.E_RESIZE))){

                stage.setHeight(Math.max(dragEvent.getScreenY() - event.getScreenY() + event.getSceneY(), stage.getMinHeight()));
            }

        });
    }
}
