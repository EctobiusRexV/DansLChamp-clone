package sim.danslchamp;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class Condensateur extends Composante {

    private static final long DEFAUT_CAPACITE_uf = 1000;

    private long capacite_uf;

    public final String name = "Condensateur";

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

    @Override
    public String getName() {
        return name;
    }

    public void setCapacite_uf(String capacite_uf) {
        if (!capacite_uf.isEmpty()) {
            if (capacite_uf.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "longueur_m");
            } else {
                try {
                    this.capacite_uf = Long.parseLong(capacite_uf);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "longueur_m");
                }
            }
        } else {
            this.capacite_uf = 0;
        }

    }
}
