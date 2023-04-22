package sim.danslchamp.controleurs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.reflections.Reflections;
import sim.danslchamp.Util.MathMlUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BibliothequeUnitesController extends ParentControleur implements Initializable {

    public BorderPane titleBar;

    @FXML
    private WebView textAreaBibliotheque;

    @FXML
    private VBox vBoxPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vBoxPane.setSpacing(10);

        vBoxPane.setFillWidth(true);

        List<String> listNomUnites = new ArrayList<>(List.of("ampère", "volt",
                "ohm", "farad", "tesla", "coulomb", "mètre"));

        for (String unite : listNomUnites) {
            CreerVBoxs(unite);
        }
    }

    private void CreerVBoxs(String nom) {

        VBox vBox = new VBox();

        vBox.setSpacing(5);

        vBox.setAlignment(Pos.CENTER);

        vBox.setMinHeight(50);

        Image image = null;

        try {
            image = new Image(this.getClass().getResourceAsStream("../unites/" + nom + ".jpg"));

        } catch (Exception e) {
            System.err.println("Incapable de présenter l'unité " + nom);

        }

        Label label = new Label(nom);

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        vBox.getChildren().addAll(imageView, label);

        vBoxPane.getChildren().add(vBox);

        vBox.setOnMouseClicked(event -> {
            try {
                textAreaBibliotheque.getEngine().loadContent(MathMlUtil.loadTxt("unites/" + nom + ".txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
