package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.StrokeLineCap;
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
    public Résistor(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(0, 10)), new Jonction(new Point(80, 10))},
                30, 80, posX, posY, rotation90);
    }

    @Override
    Group getSymbole3D() {
        Cylinder c = new Cylinder(getLargeur() / 2, getHauteur());
        if (getLargeur() > getHauteur()) {
            c = new Cylinder(getHauteur() / 2, getLargeur());
        }
        c.setLayoutX(c.getLayoutX() + c.getHeight() / 2);
        c.setRotate(90);
        if (!rotation90) {
            c.setRotate(90);
        }
        Group g = new Group();
        g.getChildren().addAll(c);
        return g;
    }

    @Override
    Group getChamp() {
        return new Group();
    }

    public double calculResistance(int frequence) {
        reactance.setValeur(resistance.getValeur(Unite.UNITE), Unite.UNITE);
        return resistance.getValeur(Unite.UNITE);
    }
}
