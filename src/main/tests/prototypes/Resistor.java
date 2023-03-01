package prototypes;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

public class Resistor extends Composante2 {

    public Resistor() {
        setGroupe();
    }

    @Override
    public void setGroupe() {
       Cylinder c = new Cylinder(10,30);
       c.setMaterial(new PhongMaterial(Color.BLUE));
        c.setRotate(90);
        this.getGroupe().getChildren().add(c);
    }
}
