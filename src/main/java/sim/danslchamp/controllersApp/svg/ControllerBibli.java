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
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
        CreerVBoxs("source_ca");
        CreerVBoxs("source_cc");

    }

    private void CreerVBoxs(String nom) {

        VBox vBoxBobine = new VBox();

        vBoxBobine.setSpacing(5);

        vBoxBobine.setAlignment(Pos.CENTER);

        vBoxBobine.setMinHeight(50);

        SvgLoader svgLoader = new SvgLoader();

        Group group = new Group();

        group.minHeight(50);

        group = svgLoader.loadSvg(this.getClass().getResourceAsStream(nom + ".svg"));

        Label labelBobine = new Label(nom);

        vBoxBobine.getChildren().addAll(group, labelBobine);

        vBoxPane.getChildren().add(vBoxBobine);
    }
}
