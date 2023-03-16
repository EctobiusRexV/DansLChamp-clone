package sim.danslchamp.circuit;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class Résistor extends Composant {

    private static final long DEFAUT_RESISTANCE_mOHMS = 4000;
    private long résistance_mOhms;

    public String name;

    /**
     * Permet la construction d'un résistor depuis les attributs SVG.
     *
     * @param résistance_mOhms
     */
    // todo gérer nom, scale, rotation
    public Résistor(int posX, int posY, boolean rotation90,
                    String résistance_mOhms) {
        super(new Jonction[]{new Jonction(new Point(0, 10)), new Jonction(new Point(80, 10))},
                30, 80, posX, posY, rotation90);

        setRésistance_mOhms(résistance_mOhms);
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
    void initGroupe3D() {
        Cylinder c = new Cylinder(10,30);
        c.setMaterial(new PhongMaterial(Color.BLUE));
        c.setRotate(90);
        this.getGroupe3D().getChildren().add(c);
    }
}
