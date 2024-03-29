package sim.danslchamp.controleurs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.reflections.Reflections;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.Util.MathMlUtil;
import sim.danslchamp.circuit.Composant;

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
            if (DansLChampApp.class.getResource("circuit/symboles/" + composantClass.getSimpleName() + ".svg") != null)   // FIXME: 2023-04-04 Hotfix
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
            group = SVG_LOADER.loadSvg(DansLChampApp.class.getResourceAsStream("circuit/symboles/" + nom + ".svg"));
        } catch (Exception e) {
            System.err.println("Incapable de présenter le composant " + nom);
            ;
        }

        Label label = new Label(nom);

        vBox.getChildren().addAll(group, label);

        vBoxPane.getChildren().add(vBox);

        vBox.setOnMouseClicked(event -> {
            try {
                if (nom.contains("source") && !nom.contains("batterie")) {
                    textAreaBibliotheque.getEngine().loadContent(MathMlUtil.loadTxt("composants/source" + ".txt"));
                } else textAreaBibliotheque.getEngine().loadContent(MathMlUtil.loadTxt("composants/" + nom + ".txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
