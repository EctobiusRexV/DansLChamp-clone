package prototypes;

import danslchamp.Composante;
import javafx.scene.shape.Line;

public class Fil extends Composante2 {

    private int startX;
    private int endX;
    private int endY;
    private int startY;


    public Fil(int startX, int startY, int endX, int endY) {
        setStartX(startX);
        setStartY(startY);
        setEndX(endX);
        setEndY(endY);
        setGroupe();
    }

    @Override
    public void setGroupe() {
        this.getGroupe().getChildren().add(new Line(startX, startY, endX, endY));
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
}
