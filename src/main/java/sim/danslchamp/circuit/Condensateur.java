package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

import javafx.scene.shape.Rectangle;
import sim.danslchamp.Config;

import java.awt.Point;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 * @author Thierry Rhéaume
 */
public class Condensateur extends Composant {

    @Affichable
    @Modifiable
    public Valeur capacite = new Valeur(Config.defautCondensateurCapacite_pF, Unite.UNITE, "F");

    private double resistance;

    /**
     * Permet la construction d'un condensateur depuis les attributs SVG
     */
    public Condensateur(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(20, 0)), new Jonction(new Point(20, 30))},
                30, 40, posX, posY, rotation90);
    }

    @Override
    Group getSymbole3D() {
        Box r = new Box(getLargeur(), getLargeur(), getHauteur()/5);
        Cylinder c = new Cylinder(getLargeur() / 2, getHauteur());
        r.setMaterial(new PhongMaterial(Color.DEEPPINK));
        c.setMaterial(new PhongMaterial(Color.PINK));
        c.setRotationAxis(new Point3D(1, 0, 0));
        c.setRotate(90);
        Group g = new Group();
        g.getChildren().addAll(r,c);
        r.setTranslateZ(getHauteur()/2);
        g.setLayoutX(g.getLayoutX() + getLargeur()/2);
        g.setLayoutY(g.getLayoutY() + getLargeur()/2);
        return g;
    }

    @Override
    Group getChamp() {
        return new Group();
    }

    public double calculResistance(int frequence) {

        if (frequence == 0) {
            return Double.MAX_VALUE;
        }

        reactance.setValeur(1 / (2 * Math.PI * frequence * capacite.getValeur()), Unite.UNITE);

        return reactance.getValeur();
    }

}
