package sim.danslchamp.circuit;

import javafx.scene.shape.Line;

import java.awt.*;

public class Fil extends Composant {

    public Fil(int startX, int startY, int endX, int endY) {
        super(new Jonction[]{new Jonction(new Point(0, 0)), new Jonction(new Point(endX-startX, endY-startY))},
                endY-startY, endX-startX, startX, startY, false);
    }

    @Override
    void initGroupe3D() {
//        this.getGroupe3D().getChildren().add(new Line(getPosX(), getPosY()));
    }
}
