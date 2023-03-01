package sim.danslchamp;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Circuit {

    private Source source;

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
