package sim.danslchamp.controleurs;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AProposControleur extends ParentControleur implements Initializable {
    public WebView textAreaAPropos;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaAPropos.getEngine().loadContent(
                "<p align=\"justify\" style=\"font-family: Open Sans Light\">\nCette aplication est destinée à aider les élèves, en particulier ceux de Sciences de la" +
                " nature et de Sciences informatiques et Mathématiques lors de leur cours de Physique, électricité et magnétisme" +
                        " ou Physique 2 (203-NYC-05).  Elle est conçue afin que l'étudiant puisse comprendre les concepts importants " +
                        "au cours ainsi que pour visualiser les champs magnétiques et électriques, concept plus difficile à " +
                        "appréhender et maîtriser.  L'application à été créée à partir des documents fournis et utilisés dans le " +
                        "cours d'électricité et magnétisme et en collaboration avec les professeurs de physique du Cégep " +
                        "Limoilou."
                );

    }



    public void fermerApp() {
        ((Stage)textAreaAPropos.getScene().getWindow()).close();
    }
}
