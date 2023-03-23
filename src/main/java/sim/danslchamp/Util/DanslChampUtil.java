package sim.danslchamp.Util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class DanslChampUtil {

    public static void lanceAlerte(String pourquoi, String source){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur dans l'entrée des données");
        alert.setHeaderText(pourquoi);
        alert.setContentText("La valeur entrée est non conforme au format attendue." +
                "  Changez la valeur dans la case : " + source);
        alert.showAndWait();
    }

    public static void erreur(String contentText, String expandableText) {
        Alert alert = new Alert(Alert.AlertType.ERROR, contentText);
        alert.getDialogPane().setExpandableContent(new Label(expandableText));
        alert.showAndWait();
    }

    public static void warning(String contentText, String expandableText) {
        Alert alert = new Alert(Alert.AlertType.WARNING, contentText);
        alert.getDialogPane().setExpandableContent(new Label(expandableText));
        alert.showAndWait();
    }
}
