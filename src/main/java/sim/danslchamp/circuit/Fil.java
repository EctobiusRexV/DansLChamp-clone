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
        Line l = new Line(0, 0, endX, endY);
        l.setStrokeWidth(getStrokeWidth());
        return new Group(l);
    }

    @Override
    Group getSymbole3D() {
        return getSymbole2D();
    }

    @Override
    Group getChamp() {
        return new Group();
    }

    @Override
    public double calculResistance(double frequence) {
        return 0;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
}
