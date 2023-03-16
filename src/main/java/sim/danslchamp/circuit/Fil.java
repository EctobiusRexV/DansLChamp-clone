package sim.danslchamp.circuit;

import javafx.scene.shape.Line;

import java.awt.*;

public class Fil extends Composant {

    public Fil(int startX, int startY, int endX, int endY) {
        int largeur = endX-startX;
        int hauteur = endY-startY;
        super(new Jonction[]{new Jonction(new Point(0, 0), new Jonction(new Point(largeur, hauteur))},
                hauteur, largeur, startX, startY, false);
    }

    @Override
    void initGroupe3D() {
        this.getGroupe3D().getChildren().add(new Line(getPosX(), getPosY(), getConnecteurs()[1].x, getConnecteurs()[1].y));
    }
}
