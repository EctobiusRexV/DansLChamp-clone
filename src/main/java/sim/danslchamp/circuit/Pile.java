package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 * @author Thierry Rhéaume
 */
public class Pile extends Source {

    public Pile(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(20, 0)), new Jonction(new Point(20, 30))},
                30, 40, posX, posY, rotation90);
    }

    @Override
    Group getSymbole3D() {
        Box box = new Box(getLargeur(), getHauteur(), 20);
        box.setMaterial(new PhongMaterial(Color.RED));
        box.setLayoutX(box.getLayoutX() + box.getWidth()/2);
        box.setLayoutY(box.getHeight()/2 + box.getLayoutY());
        if(rotation90) {
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
