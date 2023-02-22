package prototypes;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Source extends Composante2 {

    public Source() {
        setGroupe();
    }


    @Override
    public void setGroupe() {
            Box box = new Box(100, 50, 20);
            box.setMaterial(new PhongMaterial(Color.RED));
            this.getGroupe().getChildren().add(box);

        }
}
