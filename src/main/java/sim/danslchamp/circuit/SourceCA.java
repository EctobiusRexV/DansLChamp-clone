package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 */
public class SourceCA extends Source {

    public SourceCA(int posX, int posY, boolean rotation90,
                    String voltage_mV) {
        super(new Jonction[]{new Jonction(new Point(20, 0)), new Jonction(new Point(20, 40))},
                40, 40,  posX, posY, rotation90);

        setVoltage_mV(voltage_mV);
    }

    public void setVoltage_mV(String voltage_mV) {
        if (!voltage_mV.isEmpty()) {
            if (voltage_mV.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Voltage (mV)");
            } else {
                try {
                    setVoltage_mV(Long.parseLong(voltage_mV));
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Voltage (mV)");
                }
            }
        } else {
            setVoltage_mV(0);
        }
    }

    @Override
    Group getSymbole3D() {
        Box box = new Box(100, 50, 20);
        box.setMaterial(new PhongMaterial(Color.RED));
        box.setLayoutX(this.getPosX());
        box.setLayoutY(this.getPosY());
        if (rotation90){
            box.setRotationAxis(new Point3D(1,0,0));
            box.setRotate(90);
        }
        Group g = new Group();
        g.getChildren().addAll(box);
        return g;
    }
}
