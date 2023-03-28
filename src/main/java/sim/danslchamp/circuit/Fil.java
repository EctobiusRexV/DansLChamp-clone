package sim.danslchamp.circuit;

import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.awt.*;

public class Fil extends Composant {

    private int endX, endY;
    public Fil(int startX, int startY, int endX, int endY) {
        super(new Jonction[]{new Jonction(new Point(0, 0)), new Jonction(new Point(endX-startX, endY-startY))},
                endY-startY, endX-startX, startX, startY, false);
        this.endX=endX-startX;
        this.endY=endY-startY;
    }

    @Override
    public Group getSymbole2D() {
        return new Group(new Line(0, 0, endX, endY));
    }

    @Override
    Group getSymbole3D() {
        return getSymbole2D();
    }
}
