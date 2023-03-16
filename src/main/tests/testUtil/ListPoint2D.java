package testUtil;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class ListPoint2D {

    private Set<Point> pointSet;


    public ListPoint2D(Set<Point> pointSet) {
        this.pointSet = pointSet;
    }

    public Group getGroupe() {
        Group groupe = new Group();
        for (Point point : pointSet) {
            Sphere sp = new Sphere(5);

            sp.setMaterial(new PhongMaterial(Color.RED));
            sp.setLayoutY(point.getY());
            sp.setLayoutX(point.getX());
            groupe.getChildren().add(sp);
        }

        return groupe;
    }
}
