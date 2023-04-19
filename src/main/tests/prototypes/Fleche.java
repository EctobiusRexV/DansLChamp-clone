package prototypes;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import sim.danslchamp.circuit.ConeMesh;

public class Fleche {
    // Set up the start and end points of the arrow
    Point3D start;
    Point3D end;

    // Calculate the middle point of the arrow
    Point3D middle = start.midpoint(end);

    // Calculate the direction vector of the arrow
    Point3D direction = end.subtract(start);
    Cylinder cylinder = new Cylinder(5, start.distance(end));
    TriangleMesh arrowHead = new TriangleMesh();
    MeshView v = new MeshView(arrowHead);
     ConeMesh c = new  ConeMesh();
    Group groupe = new Group(cylinder,c);


    public Fleche(double sx,double sy,double sz, double ex,double ey, double ez) {
        start = new Point3D(sx,sy,sz);
        end = new Point3D(ex,ey,ez);
        createCylinder();
        createArrow();
        c.setRotationAxis(new Point3D(0,0,1));
        c.setLayoutY(cylinder.getLayoutY() - c.getRadius());
        c.setLayoutX(cylinder.getLayoutX()+ cylinder.getHeight() + c.getHeight()/2);
        c.setRotate(90);
    }

    // Create the cylinder for the arrow body
    void createCylinder() {
        cylinder.setTranslateX(middle.getX());
        cylinder.setTranslateY(middle.getY());
        cylinder.setTranslateZ(middle.getZ());

        cylinder.setRotationAxis(direction.crossProduct(new Point3D(0, 1, 0)));
        cylinder.setRotate(Math.toDegrees(Math.acos(direction.normalize().dotProduct(new Point3D(0, 1, 0)))));
    }
// Rotate the cylinder to align it with the direction vector

    // Create the arrow head using a MeshView


    void createArrow() {
        arrowHead.getPoints().addAll(
                0, 0, 0,
                -10, -10, 10,
                10, -10, 10,
                0, 20, 0
        );
        arrowHead.getTexCoords().addAll(0, 0);
        arrowHead.getFaces().addAll(
                0, 0, 1, 0, 3, 0,
                1, 0, 2, 0, 3, 0,
                2, 0, 0, 0, 3, 0,
                2, 0, 1, 0, 0, 0
        );
        v.setTranslateX(end.getX());
        v.setTranslateY(end.getY());
        v.setTranslateZ(end.getZ());

    }
    // Add the cylinder and arrow head to a Group


    public Group getGroupe() {
        return groupe;
    }
}
