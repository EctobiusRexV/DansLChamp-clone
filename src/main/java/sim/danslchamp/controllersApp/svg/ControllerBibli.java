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
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerBibli implements Initializable {

    @FXML
    private TextArea textAreaBibliotheque;

    @FXML
    private VBox vBoxPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaBibliotheque.setEditable(false);

        vBoxPane.setSpacing(10);

        vBoxPane.setFillWidth(true);

        CreerVBoxs("bobine");
        CreerVBoxs("condensateur");
        CreerVBoxs("resistor");
        CreerVBoxs("source_batterie");
        CreerVBoxs("source_ca");
        CreerVBoxs("source_cc");

//        VBox vBoxBobine = new VBox();
//
//        vBoxBobine.setSpacing(5);
//
//        vBoxBobine.setAlignment(Pos.CENTER);
//
//        vBoxBobine.setMinHeight(50);
//
//        SvgLoader svgLoader = new SvgLoader();
//
//        Group group = new Group();
//
//        group.minHeight(50);
//
//        group = svgLoader.loadSvg(this.getClass().getResourceAsStream("bobine.svg"));
//
//        Label labelBobine = new Label("Bobine");
//
//        vBoxBobine.getChildren().addAll(group, labelBobine);
//
//        vBoxPane.getChildren().add(vBoxBobine);
//
//        VBox vBoxCondenasteur = new VBox();
//
//        vBoxCondenasteur.setMinHeight(50);
//
//        SvgLoader svgLoaderCondensateur = new SvgLoader();
//
//        Group groupCondensateur = new Group();
//
//        groupCondensateur.minHeight(50);
//
//        groupCondensateur = svgLoaderCondensateur.loadSvg(this.getClass().getResourceAsStream("condensateur.svg"));
//
//        Label labelCondensateur = new Label("Condensateur");
//
//        vBoxCondenasteur.getChildren().addAll(groupCondensateur, labelCondensateur);
//
//        vBoxPane.getChildren().add(vBoxCondenasteur);
//
//        VBox vBoxresistor = new VBox();
//
//        vBoxCondenasteur.setMinHeight(50);
//
//        SvgLoader svgLoaderCondensateur = new SvgLoader();
//
//        Group groupCondensateur = new Group();
//
//        groupCondensateur.minHeight(50);
//
//        groupCondensateur = svgLoaderCondensateur.loadSvg(this.getClass().getResourceAsStream("condensateur.svg"));
//
//        Label labelCondensateur = new Label("Condensateur");
//
//        vBoxCondenasteur.getChildren().addAll(groupCondensateur, labelCondensateur);
//
//        vBoxPane.getChildren().add(vBoxCondenasteur);
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
