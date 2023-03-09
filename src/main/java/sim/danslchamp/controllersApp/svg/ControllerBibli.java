package sim.danslchamp.controllersApp.svg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import sim.danslchamp.Util.MathMlUtil;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sim.danslchamp.controllersApp.DanslChampApp.SVG_LOADER;

public class ControllerBibli implements Initializable {

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

        Group group = new Group();

        group.minHeight(50);


        group = SVG_LOADER.loadSvg(this.getClass().getResourceAsStream(nom + ".svg"));

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
}
