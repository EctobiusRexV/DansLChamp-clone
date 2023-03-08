package sim.danslchamp;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public abstract class Source extends Composante {

    public static final long DEFAUT_VOLTAGE_mV = 1000;

    private long voltage_mv;

    public Source(String voltage_mV) {
        try {
            setVoltage_mV(Long.parseLong(voltage_mV));
        } catch (NumberFormatException e) {
            setVoltage_mV(DEFAUT_VOLTAGE_mV);
        }

        initGroupe3D();
    }

    @Override
    public void initGroupe3D() {
        Box box = new Box(100, 50, 20);
        box.setMaterial(new PhongMaterial(Color.RED));
        this.getGroupe3D().getChildren().add(box);

    }
}
