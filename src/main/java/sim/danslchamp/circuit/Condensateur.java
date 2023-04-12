package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;

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
        Cylinder r = new Cylinder(getLargeur()/2, getHauteur()/5);
        Cylinder r2 = new Cylinder(getLargeur()/2, getHauteur()/5);
        r.setMaterial(new PhongMaterial(Color.GRAY));
        r2.setMaterial(new PhongMaterial(Color.GRAY));
        r2.setTranslateY(getHauteur()*3/5);
        Group g = new Group();
        g.getChildren().addAll(r,r2);
        g.setLayoutX(g.getLayoutX() + getLargeur()/2);
        g.setLayoutY(g.getLayoutY() + getHauteur()/5);
        return g;
        /**Cylinder c = new Cylinder(getLargeur() / 2, getHauteur());
         c.setMaterial(new PhongMaterial(Color.PINK));
         c.setRotationAxis(new Point3D(1, 0, 0));
         c.setRotate(90);**/
    }

    @Override
    Group getChamp() {
        Circle c = new Circle(getLargeur()*3/5);
        c.setStrokeWidth(3);
        c.setStroke(Color.DARKRED);
        c.setStrokeLineCap(StrokeLineCap.ROUND);
        c.setFill(Color.TRANSPARENT);
        c.setRotationAxis(new Point3D(1,0,0));
        c.setRotate(80);
        c.setCenterX(getPosX() + getLargeur()/2);
        c.setCenterY(getPosY() + getHauteur()*3/5);
        return new Group(c);
    }

    public double calculResistance(int frequence) {

        if (frequence == 0) {
            return Double.MAX_VALUE;
        }

        reactance.setValeur(1 / (2 * Math.PI * frequence * capacite.getValeur()), Unite.UNITE);

        return reactance.getValeur();
    }

}
