package sim.danslchamp.controleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sim.danslchamp.Util.MathMlUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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


        group = SVG_LOADER.loadSvg(this.getClass().getResourceAsStream("..\\circuit\\symboles\\" + nom + ".svg"));

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
