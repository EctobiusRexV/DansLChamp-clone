package sim.danslchamp;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class Résistor extends Composante {

    private static final long DEFAUT_RESISTANCE_mOHMS = 4000;
    private long résistance_mOhms;

    public String name;

    /**
     * Permet la construction d'un résistor depuis les attributs SVG.
     *
     * @param résistance_mOhms
     */
    public Résistor(String résistance_mOhms) {
        try {
            this.résistance_mOhms = Long.parseLong(résistance_mOhms);
            initGroupe3D();
        } catch (NumberFormatException e) {
            this.résistance_mOhms = DEFAUT_RESISTANCE_mOHMS;
        }
    }

    public long getRésistance_mOhms() {
        return résistance_mOhms;
    }

    public void setRésistance_mOhms(String résistance_mOhms) {
        if (!résistance_mOhms.isEmpty()) {
            if (résistance_mOhms.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Résistance (mOhms)");
            } else {
                try {
                    this.résistance_mOhms = Long.parseLong(résistance_mOhms);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Résistance (mOhms)");
                }
            }
        } else {
            this.résistance_mOhms = 0;
        }

    }

    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(0, 10), new Point(80, 10)};
    }

    @Override
    public void initGroupe3D() {
        Cylinder c = new Cylinder(15, 30);
        c.setMaterial(new PhongMaterial(Color.BEIGE));
        c.setRotate(90);

        Cylinder c2 = new Cylinder(12, 30);
        c2.setMaterial(new PhongMaterial(Color.DARKBLUE));
        c2.setRotate(90);
        c2.setLayoutX(c.getHeight());

        Cylinder c3 = new Cylinder(15, 30);
        c3.setMaterial(new PhongMaterial(Color.BEIGE));
        c3.setRotate(90);
        c3.setLayoutX(c2.getLayoutX() + c2.getHeight());


        Cylinder cFil1 = new Cylinder(3, 16);
        cFil1.setMaterial(new PhongMaterial(Color.BLACK));
        cFil1.setRotate(90);
        cFil1.setLayoutX(c.getLayoutX() - c.getHeight() / 2 - cFil1.getHeight() / 2);

        Cylinder cFil2 = new Cylinder(3, 16);
        cFil2.setMaterial(new PhongMaterial(Color.BLACK));
        cFil2.setRotate(90);
        cFil2.setLayoutX(c3.getLayoutX() + c3.getHeight() / 2 + cFil2.getHeight() / 2);

        this.getGroupe3D().getChildren().addAll(c2, c, c3, cFil1, cFil2);
        this.getGroupe3D().setLayoutX(this.getPosX());
        this.getGroupe3D().setLayoutY(this.getPosY());

    }


    public String getName() {
        return name;
    }
}
