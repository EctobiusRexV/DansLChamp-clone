package sim.danslchamp.circuit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Jonction {

    private Point positionXY;

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

    @Override
    public boolean equals(Object jonction) {
        return positionXY.equals(((Jonction) jonction).positionXY);
    }
}
