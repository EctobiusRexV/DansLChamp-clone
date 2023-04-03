package sim.danslchamp.circuit;

import javafx.scene.Group;
import sim.danslchamp.Config;
import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 */
public class Bobine extends Composant {

    private Valeur  nombreDeSpires = new Valeur(Config.defautBobineNombreDeSpires, Unite.UNITE, ""),
                    longueur = new Valeur(Config.defautBobineLongueur_mm, Unite.UNITE, "m"),
                    rayon = new Valeur(Config.defautBobineRayon_mm, Unite.UNITE, "m");

    /**
     * Permet la construction d'une bobine depuis les attributs SVG
     */
    public Bobine(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(0, 10)), new Jonction(new Point(80, 10))},
                22, 80, posX, posY, rotation90);
    }

    public void setNombreDeSpires(String nombreDeSpires) {
        if (!nombreDeSpires.isEmpty()) {
            if (nombreDeSpires.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "NombreDeSpires");
            } else {
                try {
                    this.nombreDeSpires = Integer.parseInt(nombreDeSpires);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "NombreDeSpires");
                }
            }
        } else {
            this.nombreDeSpires = 0;
        }
    }

    public void setLongueur_mm(String longueur_mm) {
        if (!longueur_mm.isEmpty()) {
            if (longueur_mm.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Longueur (m)");
            } else {
                try {
                    this.longueur = Double.parseDouble(longueur_mm);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Longueur (m)");
                }
            }
        } else {
            this.longueur = 0;
        }
    }

    public void setRayon_mm(String rayon_mm) {
        if (!rayon_mm.isEmpty()) {
            if (rayon_mm.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Rayon (m)");
            } else {
                try {
                    this.rayon = Double.parseDouble(rayon_mm);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Rayon (m)");
                }
            }
        } else {
            this.rayon = 0;
        }
    }

    @Override
    Group getSymbole3D() {
        Group g = new Group();
        //set layout xy
        if (rotation90) {
            g.setRotate(90);// ou la shape
        }
        g.getChildren().addAll();
        return g;
    }


}
