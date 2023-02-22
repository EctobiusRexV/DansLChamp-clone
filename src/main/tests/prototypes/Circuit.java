package prototypes;

import danslchamp.Composante;
import danslchamp.Source;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.util.ArrayList;

public class Circuit {
    private Group groupe = new Group();

    private ArrayList<Composante2> nexts = new ArrayList<>();

    public Circuit(Composante2[] a) {

        for (int i = 0; i < a.length; i++) {
            addNext(a[i]);
        }
        setCircuit();
        switch (a.length) {
            case 2:
                layoutDeux();
                break;
            case 3:
                layoutTrois();
                break;
            case 4:
                layoutQuatre();
                break;
            case 5:
                layoutCinq();
                break;
            case 6:
                layoutSix();
                break;
            default:
                break;
        }
    }




    public void addNext(Composante2 next) {
        nexts.add(next);
    }

    public ArrayList<Composante2> getNexts() {
        return nexts;
    }

    public void setNexts(ArrayList<Composante2> nexts) {
        this.nexts = nexts;
    }

    private void traverserRecursive(Composante composante) {
        System.out.println("Traverse de " + composante);
        for (Composante next : composante.getNexts()) {
            if (next == composante) break; // !!
            traverserRecursive(next);   // un thread pour chaque maille?
        }
    }



    void setCircuit() {
        for (int i = 0; i < nexts.size(); i++) {
            groupe.getChildren().add(nexts.get(i).getGroupe());
        }
    }

    public Group getGroupe() {
        return groupe;
    }

    private void layoutSix() {
        groupe.getChildren().get(1).setLayoutX(100);
        groupe.getChildren().get(2).setLayoutX(100);
        groupe.getChildren().get(2).setLayoutY(100);
        groupe.getChildren().get(3).setLayoutY(100);
        groupe.getChildren().get(4).setLayoutX(-100);
        groupe.getChildren().get(4).setLayoutY(100);
        groupe.getChildren().get(5).setLayoutX(-100);

    }

    private void layoutCinq() {
        groupe.getChildren().get(1).setLayoutX(100);
        groupe.getChildren().get(2).setLayoutX(100);
        groupe.getChildren().get(2).setLayoutY(100);
        groupe.getChildren().get(3).setLayoutY(100);
        groupe.getChildren().get(4).setLayoutX(-100);
        groupe.getChildren().get(4).setLayoutY(50);

    }

    private void layoutQuatre() {
        groupe.getChildren().get(1).setLayoutX(100);
        groupe.getChildren().get(2).setLayoutX(100);
        groupe.getChildren().get(2).setLayoutY(100);
        groupe.getChildren().get(3).setLayoutY(100);



    }

    private void layoutTrois() {
        groupe.getChildren().get(1).setLayoutX(50);
        groupe.getChildren().get(1).setLayoutY(100);
        groupe.getChildren().get(2).setLayoutX(-50);
        groupe.getChildren().get(2).setLayoutY(100);

    }

    private void layoutDeux() {
        groupe.getChildren().get(1).setLayoutX(100);
    }
}
