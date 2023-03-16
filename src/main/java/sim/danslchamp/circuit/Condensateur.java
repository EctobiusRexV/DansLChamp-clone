package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class Condensateur extends Composant {

    private static final long DEFAUT_CAPACITE_pf = 1000;

    private long capacité_pf;

    public String name;

    /**
     * Permet la construction d'un condensateur depuis les attributs SVG
     *
     * @param capacité_pf
     */
    public Condensateur(int posX, int posY, boolean rotation90,
                        String capacité_pf) {
        super(new Jonction[]{new Jonction(new Point(20, 0)), new Jonction(new Point(20, 30))},
                30, 40, posX, posY, rotation90);

        setCapacité_pf(capacité_pf);
    }

    @Override
    void initGroupe3D() {
        Cylinder c = new Cylinder(15,20);
        c.setMaterial(new PhongMaterial(Color.PINK));
        c.setRotationAxis(new Point3D(1,0,0));
        c.setRotate(90);
        this.getGroupe3D().getChildren().add(c);
    }


    public void setCapacité_pf(String capacité_pf) {
        if (!capacité_pf.isEmpty()) {
            if (capacité_pf.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Capacité (pf)");
            } else {
                try {
                    this.capacité_pf = Long.parseLong(capacité_pf);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Capacité (pf)");
                }
            }
        } else {
            this.capacité_pf = 0;
        }
    }
}
