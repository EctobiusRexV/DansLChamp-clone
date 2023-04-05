package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import sim.danslchamp.Config;
import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 */
public class Générateur extends Source {

    @Affichable
    @Modifiable
    public Valeur frequence = new Valeur(Config.defautGenerateurFrequence_Hz, Unite.UNITE, "Hz");

    public Générateur(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(20, 0)), new Jonction(new Point(20, 40))},
                40, 40, posX, posY, rotation90);
    }

    @Override
    Group getSymbole3D() {
        Box box = new Box(getLargeur(), getHauteur(), 20);
        box.setMaterial(new PhongMaterial(Color.RED));
        box.setLayoutX(box.getLayoutX() + box.getWidth()/2);
box.setLayoutY(box.getHeight()/2 + box.getLayoutY()
);

        if (rotation90) {
            box.setRotationAxis(new Point3D(1, 0, 0));
            box.setRotate(90);
        }
        Group g = new Group();
        g.getChildren().addAll(box);
        return g;
    }

    @Override
    Group getChamp() {
        return new Group();
    }
}
