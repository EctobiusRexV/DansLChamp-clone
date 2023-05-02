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
                "<p align=\"justify\" style=\"font-family: Open Sans Light\">\nCette application est conçue pour aider les élèves, en particulier ceux des filières Sciences de la Nature et Sciences Informatiques et Mathématiques, dans le cadre de leur cours de Physique électricité et de magnétisme ou de Physique 2 (203-NYC-05). Elle a été créée à partir des documents fournis et utilisés dans le cours d'électricité et magnétisme et en collaboration avec les professeurs de physique du Cégep Limoilou.\n" +
                        "<br><br>" +
                        "Cette application permet à l'étudiant de comprendre les concepts importants abordés dans le cours, ainsi que de visualiser les champs magnétiques et électriques, qui peuvent être des concepts plus difficiles à appréhender et à maîtriser. Grâce à cette application, l'élève pourra bénéficier d'un outil interactif qui l'aidera à mieux comprendre les principes de base de la physique, à travers des simulations et des expériences virtuelles. Cela contribuera à renforcer ses connaissances et ses compétences dans ce domaine."
                        + "<br><br>Nous espérons que cette application vous plaira et qu'elle vous aidera lors de votre étude."
                        + "<br><br>Antoine Belisle, Henri Baillargeon, Thierry Rhéaume et Mathis Rosa-Wilson"
                );

    }



    public void fermerApp() {
        ((Stage)textAreaAPropos.getScene().getWindow()).close();
    }
}
