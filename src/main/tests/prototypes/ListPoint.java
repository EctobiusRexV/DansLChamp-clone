package prototypes;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;

public class ListPoint {

    private ArrayList<Point2D> liste;


    public ListPoint() {
        liste = new ArrayList<Point2D>();
    }

    public void addPoint(double x, double y) {
        liste.add(new Point2D(x, y));
    }

    public void removePoint(double x, double y) {
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).getX() == x && liste.get(i).getY() == y) {
                liste.remove(i);
            }
        }
    }


    Group getGroupe() {
        Group groupe = new Group();
        for (Point2D i : liste) {
            Sphere sp = new Sphere(5);

            sp.setMaterial(new PhongMaterial(Color.RED));
            sp.setLayoutY(i.getY());
            sp.setLayoutX(i.getX());
            groupe.getChildren().add(sp);
        }

        return groupe;
    }
}
