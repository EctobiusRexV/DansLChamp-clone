package sim.danslchamp.circuit;

import javafx.scene.Group;
import javafx.scene.shape.Cylinder;
import sim.danslchamp.Config;

import java.awt.*;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 * @author Thierry Rhéaume
 */
public class Résistor extends Composant {

    // Pas afficher! (duplic. avec réactance)
    @Modifiable
    public Valeur resistance = new Valeur(Config.defautResistorResistance_mOhms, Unite.UNITE, "Ω");

    /**
     * Permet la construction d'un résistor depuis les attributs SVG.
     */
    public Résistor(int posX, int posY, int angleRotation) {
        super(new Jonction[]{new Jonction(new Point(0, 10)), new Jonction(new Point(80, 10))},
                30, 80, posX, posY, angleRotation);
    }

    @Override
    Group getSymbole3D() {
        Cylinder c = new Cylinder(getLargeur() / 2, getHauteur());
        if (getLargeur() > getHauteur()) {
            c = new Cylinder(getHauteur() / 2, getLargeur());
        }
        c.setLayoutX(c.getLayoutX() + c.getHeight() / 2);
        c.setTranslateY(getHauteur()/2);
        c.setRotate(90);
        if (!rotation90) {
            c.setRotate(90);
        }
        Group g = new Group();
        g.getChildren().addAll(c);
        return g;
    }

    @Override
    public Group getChamp() {
        return new Group();
    }


    @Override
    public double calculResistance(double frequence) {
        reactance.setValeur(resistance.getValeur(Unite.UNITE), Unite.UNITE);
        return resistance.getValeur(Unite.UNITE);
    }
}
