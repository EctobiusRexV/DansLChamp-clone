package sim.danslchamp.circuit;

import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.awt.*;

public class Fil extends Composant {

    public Fil(int startX, int startY, int endX, int endY) {
        super(new Jonction[]{new Jonction(new Point(0, 0)), new Jonction(new Point(endX-startX, endY-startY))},
                endY-startY, endX-startX, startX, startY, false);
    }

    @Override
    Group getSymbole3D() {
        return new Group(new Line(getPosX(), getPosY(), getJonctions()[1].getPositionXY().x, getJonctions()[1].getPositionXY().y));
    }
}
