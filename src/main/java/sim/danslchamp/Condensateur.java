package sim.danslchamp;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

import java.awt.*;

public class Condensateur extends Composante {

    private static final long DEFAUT_CAPACITE_uf = 1000;

    private long capacite_uf;

    /**
     * Permet la construction d'un condensateur depuis les attributs SVG
     *
     * @param capacite_uf
     */
    public Condensateur(String capacite_uf) {
        try {
            this.capacite_uf = Long.parseLong(capacite_uf);
        } catch (NumberFormatException e) {
            this.capacite_uf = DEFAUT_CAPACITE_uf;
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
