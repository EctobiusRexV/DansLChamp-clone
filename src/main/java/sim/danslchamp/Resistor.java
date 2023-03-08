package sim.danslchamp;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class Resistor extends Composante {

    private static final long DEFAUT_RESISTANCE_mOHMS = 4000;
    private long resistance_mOhms;

    public final String name = "Resistor";

    /**
     * Permet la construction d'un résistor depuis les attributs SVG.
     *
     * @param resistance_mOhms
     */
    public Resistor(String resistance_mOhms) {
        try {
            this.resistance_mOhms = Long.parseLong(resistance_mOhms);
        } catch (NumberFormatException e) {
            this.resistance_mOhms = DEFAUT_RESISTANCE_mOHMS;
        }
    }

    public long getResistance_mOhms() {
        return resistance_mOhms;
    }

    public void setResistance_mOhms(String resistance_mOhms) {
        if (!resistance_mOhms.isEmpty()) {
            if (resistance_mOhms.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme, enlevez le dernier caractère entré", "resistance_mOhms");
            } else {
                try {
                    this.resistance_mOhms = Long.parseLong(resistance_mOhms);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme, enlevez le dernier caractère entré", "resistance_mOhms");
                }
            }
        } else {
            this.resistance_mOhms = 0;
        }

    }

    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(0, 10), new Point(80, 10)};
    }

    @Override
    public void initGroupe3D() {
        Cylinder c = new Cylinder(10,30);
        c.setMaterial(new PhongMaterial(Color.BLUE));
        c.setRotate(90);
        this.getGroupe3D().getChildren().add(c);
    }


    public String getName() {
        return name;
    }
}
