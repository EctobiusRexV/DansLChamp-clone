package sim.danslchamp;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

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
        box.setMaterial(new PhongMaterial(Color.DARKGRAY));

        Cylinder cFil1 = new Cylinder(3, 16);
        cFil1.setMaterial(new PhongMaterial(Color.BLACK));
        cFil1.setLayoutX(-box.getWidth()/3);
        cFil1.setLayoutY(box.getHeight()/2 + cFil1.getHeight()/2);

        Cylinder cFil2 = new Cylinder(3, 16);
        cFil2.setMaterial(new PhongMaterial(Color.BLACK));
        cFil2.setLayoutX(box.getWidth()/3);
        cFil2.setLayoutY(box.getHeight()/2 + cFil2.getHeight()/2);


        this.getGroupe3D().getChildren().addAll(box, cFil1, cFil2);
        this.getGroupe3D().setLayoutX(this.getPosX());
        this.getGroupe3D().setLayoutY(this.getPosY());
    }
}
