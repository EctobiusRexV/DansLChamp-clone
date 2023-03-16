package sim.danslchamp.circuit;

import sim.danslchamp.Composante;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Jonction {

    private Point positionXY;

    private final List<Composante> composantes;

    private boolean estNoeud;

    public Jonction(Point positionXY) {
        this.positionXY = positionXY;
        composantes = new ArrayList<>();
        estNoeud = false;
    }

    public void addComposant(Composante c) {
        composantes.add(c);

        if (composantes.size() > 2) {
            estNoeud = true;
        }
    }

    public boolean estNoeud() {
        return estNoeud;
    }

    public boolean equals(Jonction jonction) {

        return positionXY.equals(jonction.positionXY);
    }
}
