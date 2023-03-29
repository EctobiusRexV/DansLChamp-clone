package sim.danslchamp.controleurs;

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
import org.reflections.Reflections;
import sim.danslchamp.Util.MathMlUtil;
import sim.danslchamp.circuit.Composant;
import sim.danslchamp.controleurs.ControllerUtil;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import static sim.danslchamp.DansLChampApp.SVG_LOADER;

public class BibliothequeControleur extends ParentControleur implements Initializable {

    public BorderPane titleBar;

    @FXML
    private WebView textAreaBibliotheque;

    @FXML
    private VBox vBoxPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vBoxPane.setSpacing(10);

        vBoxPane.setFillWidth(true);

        // Un panel pour chaque composant :
        Reflections reflections = new Reflections("sim.danslchamp.circuit");

        Set<Class<? extends Composant>> composantsClasses = reflections.getSubTypesOf(Composant.class);

        for (Class<? extends Composant> composantClass : composantsClasses) {
            CreerVBoxs(composantClass.getSimpleName());
        }
    }

    private void CreerVBoxs(String nom) {

        VBox vBox = new VBox();

        vBox.setSpacing(5);

        vBox.setAlignment(Pos.CENTER);

        vBox.setMinHeight(50);

        Group group = new Group();

        group.minHeight(50);

try {
    group = SVG_LOADER.loadSvg(this.getClass().getResourceAsStream("..\\circuit\\symboles\\" + nom + ".svg"));
} catch (Exception e) {
    System.err.println("Incapable de présenter le composant " + nom);;
}

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
    void fermerApp(ActionEvent e) throws InterruptedException {
        Thread.sleep(100);
        ((Stage)(((Node)e.getTarget()).getScene().getWindow())).close();
    }


    @FXML
    void resizeApp(ActionEvent e) throws InterruptedException {
        Thread.sleep(50);
        Stage stage = ((Stage) ((Node)e.getTarget()).getScene().getWindow());
        if( stage.isMaximized()) {
           stage.setMaximized(false);
        }
        else  stage.setMaximized(true);
    }

    @FXML
    public void minimizeApp(ActionEvent e) throws InterruptedException {
        Thread.sleep(50);
        Stage stage = ((Stage) ((Node)e.getTarget()).getScene().getWindow());
        stage.setIconified(true);
    }

    @FXML
    public void mouvePressed(MouseEvent event) {
        ControllerUtil.mouveStageUtil(event);
    }

    @FXML
    public void dragResize(MouseEvent event) {
        ControllerUtil.resizeUtil(event);
    }
}
