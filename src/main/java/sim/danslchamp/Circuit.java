package sim.danslchamp;

import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Circuit {

    private Source source;

    private Group groupe = new Group();


    /**
     * Un circuit est composé de multiples composantes connectées l'une avec les autres.
     * Pour un point de connexion donné (connecteur), on énumère la liste des composantes qui y sont liées.
     * <p>
     * Chaque composante définit relativement la position de ses connecteurs. Ce sont les positions où il est graphiquement
     * possible de lier la composante à une autre. L'emplacement des connecteurs absolus est calculé suivant l'emplacement absolu
     * de la composante (x=, y=), additionné de la position relative de ses connecteurs (translation).
     * <p>
     * Un connecteur liant plus de deux composantes est une intersection, suivant la loi des mailles.
     */
    private Map<Point, ArrayList<Composante>> connecteurs;

    public Circuit(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

    private ArrayList<Composante> nexts = new ArrayList<>();

    public Circuit(Composante[] a) {

        for (int i = 0; i < a.length; i++) {
            addNext(a[i]);
        }
        setCircuit3D();
    }

    public void addNext(Composante next) {
        nexts.add(next);
    }

    public ArrayList<Composante> getNexts() {
        return nexts;
    }

    public void setNexts(ArrayList<Composante> nexts) {
        this.nexts = nexts;
    }

    void setCircuit3D() {
        for (int i = 0; i < nexts.size(); i++) {
            groupe.getChildren().add(nexts.get(i).getGroupe3D());
        }
    }

    public Group getGroupe() {
        return groupe;
    }

    // TRAVERSE NEXT
    /*public void traverser() {
        traverserRecursive(source);
    }

    private void traverserRecursive(Composante composante) {
        System.out.println("Traverse de " + composante);
        for (Composante next : composante.getNexts()) {
            if (next == composante) break; // !!
            traverserRecursive(next);   // un thread pour chaque maille?
        }
    }*/
}
