package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

import sim.danslchamp.Config;
import sim.danslchamp.Util.DanslChampUtil;

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

    /**
     * Permet la construction d'un condensateur depuis les attributs SVG
     */
    public Condensateur(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(20, 0)), new Jonction(new Point(20, 30))},
                30, 40, posX, posY, rotation90);
    }

    @Override
    Group getSymbole3D() {
        Cylinder c = new Cylinder(getLargeur()/2,getHauteur());
        c.setMaterial(new PhongMaterial(Color.PINK));
//        c.setLayoutX(getPosX()* 1.5);
//        c.setLayoutY(getPosY()* 1.5);
        if (rotation90){
            c.setRotationAxis(new Point3D(1,0,0));
            c.setRotate(90);
        }
        Group g = new Group();
        g.getChildren().addAll(c);
        return g;
    }

}
