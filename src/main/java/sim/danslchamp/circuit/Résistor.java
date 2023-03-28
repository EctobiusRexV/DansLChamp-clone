package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 * @author Thierry Rhéaume
 */
public class Résistor extends Composant {

    private long résistance_mOhms;

    /**
     * Permet la construction d'un résistor depuis les attributs SVG.
     */
    public Résistor(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(0, 10)), new Jonction(new Point(80, 10))},
                30, 80, posX, posY, rotation90);
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
    Group getSymbole3D() {
        Cylinder c = new Cylinder(10,30);
        c.setMaterial(new PhongMaterial(Color.BLUE));

        if (rotation90){
            c.setRotate(90);
        }
        Group g = new Group();
        g.getChildren().addAll(c);
     return g;
    }

}
