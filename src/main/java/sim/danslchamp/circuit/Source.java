package sim.danslchamp.circuit;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.awt.*;

public abstract class Source extends Composant {

    public static final long DEFAUT_VOLTAGE_mV = 5000;

    private boolean inversee;

    public Source(Jonction[] jonctionsRelatives, int hauteur, int largeur, int posX, int posY, boolean rotation90) {
        super(jonctionsRelatives, hauteur, largeur, posX, posY, rotation90);

        setBornePositive(inversee? getJonctions()[1] : getJonctions()[0]);
    }

    @Override
    void initGroupe3D() {
        Box box = new Box(100, 50, 20);
        box.setMaterial(new PhongMaterial(Color.RED));
        this.getGroupe3D().getChildren().add(box);
    }
}
