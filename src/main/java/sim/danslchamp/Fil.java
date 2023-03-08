package sim.danslchamp;

import javafx.scene.shape.Line;

import java.awt.*;

public class Fil extends Composante{

    private final String name = "fil";
    private int startX;
    private int endX;
    private int endY;
    private int startY;

    public Fil(int startX, int startY, int endX, int endY) {
        setStartX(startX);
        setStartY(startY);
        setEndX(endX);
        setEndY(endY);
        initGroupe3D();
    }

    @Override
    public void initGroupe3D() {
        this.getGroupe3D().getChildren().add(new Line(startX, startY, endX, endY));
    }




    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    @Override
    Point[] getConnecteursRelatifs() {
        throw new RuntimeException("getConnecteursRelatifs() non-implémenté pour les fils.");
    }


    public String getName() {
        return name;
    }
}
