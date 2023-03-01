package sim.danslchamp;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

import java.awt.*;

public class Condensateur extends Composante {

    private static final long DEFAUT_CAPACITE_pf = 1000;

    private long capacite_pf;

    /**
     * Permet la construction d'un condensateur depuis les attributs SVG
     *
     * @param capacite_pf
     */
    public Condensateur(String capacite_pf) {
        try {
            this.capacite_pf = Long.parseLong(capacite_pf);
        } catch (NumberFormatException e) {
            this.capacite_pf = DEFAUT_CAPACITE_pf;
        }
    }

    public Condensateur() {
        setGroupe3D();
    }

    @Override
    public void setGroupe3D() {
        Cylinder c = new Cylinder(15,20);
        c.setMaterial(new PhongMaterial(Color.PINK));
        c.setRotationAxis(new Point3D(1,0,0));
        c.setRotate(90);
        this.getGroupe3D().getChildren().add(c);
    }

    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(20, 0), new Point(20, 30)};
    }
}
