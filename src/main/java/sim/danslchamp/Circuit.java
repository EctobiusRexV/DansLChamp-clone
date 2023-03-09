package sim.danslchamp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import sim.danslchamp.Util.SmartGroup;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Circuit {

    private Source source;

    private Group groupe2D = new Group();

    // pose pas d'question
    private final SmartGroup groupe3D = new SmartGroup();


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

    private ObservableList<Composante> composantes;

    public Circuit(Group groupe2D, List<Composante> composantes) {
        this.groupe2D = groupe2D;
        this.composantes = FXCollections.observableList(composantes);
        setCircuit3D();
    }

    void setCircuit3D() {
        for (Composante composante : composantes) {
            groupe3D.getChildren().add(composante.getGroupe3D());
            System.out.println(composante);
            System.out.println(composante.getPosX());
            System.out.println(composante.getPosY());
        }
    }

    public ObservableList<Composante> getComposantes() {
        return composantes;
    }

    public Group getGroupe2D() {
        return groupe2D;
    }

    public Group getGroupe3D() {
        return groupe3D;
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
