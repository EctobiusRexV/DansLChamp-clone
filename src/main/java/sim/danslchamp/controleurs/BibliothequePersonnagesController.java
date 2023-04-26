package sim.danslchamp.controleurs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.reflections.Reflections;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.Util.MathMlUtil;
import sim.danslchamp.circuit.Composant;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static sim.danslchamp.DansLChampApp.SVG_LOADER;

public class BibliothequePersonnagesController extends ParentControleur implements Initializable {

    public BorderPane titleBar;

    @FXML
    private WebView textAreaBibliotheque;

    @FXML
    private VBox vBoxPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vBoxPane.setSpacing(10);

        vBoxPane.setFillWidth(true);

        List<String> listNomPersonnages = new ArrayList<>(List.of("Ørsted","Ampère", "Volta", "Edison",
                "Ohm", "Faraday", "Tesla"));
        listNomPersonnages.addAll(List.of("Maxwell", "Coulomb", "Kirchhoff", "Henry", "Hertz"));

        for (String personnage : listNomPersonnages) {
                CreerVBoxs(personnage);

        }

//        Set<Class<? extends Composant>> composantsClasses = reflections.getSubTypesOf(Composant.class);
//
//        for (Class<? extends Composant> composantClass : composantsClasses) {
//            if (this.getClass().getResource("../circuit/symboles/" + composantClass.getSimpleName() + ".svg") != null)   // FIXME: 2023-04-04 Hotfix
//                CreerVBoxs(composantClass.getSimpleName());
//        }
    }

    private void CreerVBoxs(String nom) {

        VBox vBox = new VBox();

        vBox.setSpacing(5);

        vBox.setAlignment(Pos.CENTER);

        vBox.setMinHeight(50);

        Image image = null;

        try {
            image = new Image(DansLChampApp.class.getResourceAsStream("personnages/" + nom + ".jpg"));

        } catch (Exception e) {
            System.err.println("Incapable de présenter le personnage " + nom);

        }

        Label label = new Label(nom);

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(130);
        imageView.setFitWidth(130);

        vBox.getChildren().addAll(imageView, label);

        vBoxPane.getChildren().add(vBox);

        vBox.setOnMouseClicked(event -> {
            try {
                textAreaBibliotheque.getEngine().loadContent(MathMlUtil.loadTxt("personnages/" + nom + ".txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
