package sim.danslchamp.Util;

import javafx.scene.control.Alert;

public class DanslChampUtil {

    public static void lanceAlerte(String pourquoi, String source){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur dans l'entrée des données");
        alert.setHeaderText(pourquoi);
        alert.setContentText("La valeur entrée est non conforme au format attendue." +
                "  Changez la valeur dans la case : " + source);
        alert.showAndWait();
    }

}
