package sim.danslchamp.controllersApp;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAPropos implements Initializable {
    public TextArea textAreaAPropos;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        textAreaAPropos.setText("Cette aplication est destinée à aider les élèves, en particulier ceux de Sciences de la" +
                " nature \net de Sciences informatiques et Mathématiques lors de leur cours de Physique, électricité et \nmagnétisme" +
                        " ou Physique 2 (203-NYC-05).  Elle est conçue afin que l'étudiant puisse \ncomprendre les concepts importants " +
                        "au cours ainsi que pour visualiser les champs \nmagnétiques et électriques, concept plus difficile à " +
                        "appréhender et maîtriser.  \n\nL'application à été créée à partir des documents fournis et utilisés dans le " +
                        "cours d'électricité \net magnétisme et en collaboration avec les professeurs de physique du Cégep " +
                        "Limoilou.  "
                );
        textAreaAPropos.setEditable(false);
    }
}
