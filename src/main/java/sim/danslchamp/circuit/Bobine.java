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

    @Affichable
    @Modifiable
    public Valeur  nombreDeSpires = new Valeur(Config.defautBobineNombreDeSpires, Unite.UNITE, ""),
                    longueur = new Valeur(Config.defautBobineLongueur_mm, Unite.UNITE, "m"),
                    rayon = new Valeur(Config.defautBobineRayon_mm, Unite.UNITE, "m");

    /**
     * Permet la construction d'une bobine depuis les attributs SVG
     */
    public Bobine(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(0, 10)), new Jonction(new Point(80, 10))},
                22, 80, posX, posY, rotation90);
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
