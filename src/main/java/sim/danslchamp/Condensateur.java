package sim.danslchamp;

import java.awt.*;

public class Condensateur extends Composante {

    private static final long DEFAUT_CAPACITE_uf = 1000;

    private long capacite_uf;

    /**
     * Permet la construction d'un condensateur depuis les attributs SVG
     *
     * @param capacite_uf
     */
    public Condensateur(String capacite_uf) {
        try {
            this.capacite_uf = Long.parseLong(capacite_uf);
        } catch (NumberFormatException e) {
            this.capacite_uf = DEFAUT_CAPACITE_uf;
        }
    }

    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(20, 0), new Point(20, 30)};
    }
}
