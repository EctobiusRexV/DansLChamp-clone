package sim.danslchamp.circuit;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Une jonction représente l'embout d'un composant, éventuellement lié à d'autres composants.
 * Si la jonction joint plus de deux composants, c'est un noeud et la loi des noeuds s'applique.
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 */
public class Jonction {

    private final Point positionXY;
    private final List<Composant> composantes;
    private boolean estNoeud;

    public Jonction(Point positionXY) {
        this.positionXY = positionXY;
        composantes = new ArrayList<>();
        estNoeud = false;
    }

    public void addComposant(Composant c) {
        composantes.add(c);

        if (composantes.size() > 2) {
            estNoeud = true;
        }
    }

    public boolean estNoeud() {
        return estNoeud;
    }

    void translate(int x, int y) {
        positionXY.translate(x, y);
    }

    public Point getPositionXY() {
        return positionXY;
    }

    public List<Composant> getComposants() {
        return composantes;
    }

    /**
     * @throws ClassCastException {@code jonction} n'est pas de type Jonction.
     */
    @Override
    public boolean equals(Object jonction) {
        return positionXY.equals(((Jonction) jonction).positionXY);
    }
}
