package prototypes;

import danslchamp.Composante;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

public class Condensateur extends Composante2 {

    public Condensateur() {
        setGroupe();
    }

    @Override
    public void setGroupe() {
        Cylinder c = new Cylinder(15,20);
        c.setMaterial(new PhongMaterial(Color.PINK));
        c.setRotationAxis(new Point3D(1,0,0));
        c.setRotate(90);
        this.getGroupe().getChildren().add(c);
    }
}
