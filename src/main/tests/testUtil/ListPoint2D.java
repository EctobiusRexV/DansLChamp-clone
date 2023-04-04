package testUtil;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import sim.danslchamp.circuit.Jonction;

import java.awt.Point;
import java.util.List;

public class ListPoint2D {

    private List<Jonction> jonctionList;


    public ListPoint2D(List<Jonction> jonctionList) {
        this.jonctionList = jonctionList;
    }

    public Group getGroupe() {
        Group groupe = new Group();
        for (Jonction jonction : jonctionList) {
            Sphere sp = new Sphere(5);

            sp.setMaterial(new PhongMaterial(Color.RED));
            sp.setLayoutY(jonction.getPositionXY().getY());
            sp.setLayoutX(jonction.getPositionXY().getX());
            groupe.getChildren().add(sp);
        }

        return groupe;
    }
}
