package sim.danslchamp.circuit;

import javafx.geometry.Point3D;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.StrokeLineCap;
import sim.danslchamp.Config;

import java.awt.*;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 */
public class Bobine extends Composant {

    @Affichable
    @Modifiable
    public Valeur nombreDeSpires = new Valeur(Config.defautBobineNombreDeSpires, Unite.UNITE, ""),
            longueur = new Valeur(Config.defautBobineLongueur_mm, Unite.UNITE, "m"),
            rayon = new Valeur(Config.defautBobineRayon_mm, Unite.UNITE, "m");

    private double resistance;

    private final double mu = (4 * Math.PI) * Math.pow(10, -7);

    /**
     * Permet la construction d'une bobine depuis les attributs SVG
     */
    public Bobine(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(0, 10)), new Jonction(new Point(80, 10))},
                22, 80, posX, posY, rotation90);
    }

    public double calculResistance(int frequence) {


        double surface = 2 * Math.PI * rayon.getValeur();

        double L = (mu * surface * Math.pow(nombreDeSpires.getValeur(), 2)) / longueur.getValeur();

        reactance.setValeur(2 * Math.PI * frequence * L, Unite.UNITE);

        return reactance.getValeur();
    }

    @Override
    Group getSymbole3D() {
        Cylinder c = new Cylinder(getLargeur() / 2, getHauteur());
        if (getLargeur() > getHauteur()) {
            c = new Cylinder(getHauteur() / 2, getLargeur());
        }
        c.setLayoutX(c.getLayoutX() + c.getHeight() / 2);
        c.setLayoutY(getPosY());
        c.setRotate(90);
        if (!rotation90) {
            c.setRotate(90);
        }
        Group g = new Group();
        g.getChildren().addAll(c);
        return g;
    }

    @Override
    Group getChamp() {
        double startX = this.getJonctions()[0].getPositionXY().getX();
        double startY = this.getJonctions()[0].getPositionXY().getY();
        double endX = this.getJonctions()[1].getPositionXY().getX();
        double endY = this.getJonctions()[1].getPositionXY().getY();

        Group group = new Group();
        for (int i = 0; i < 4; i++) {
            CubicCurve champ = new CubicCurve(startX, startY, startX - 20, courant.getValeur() + getHauteur(), endX + 20, courant.getValeur() + getHauteur(), endX, endY);
            if (i == 1) {
                champ = new CubicCurve(startX, startY, startX - 20, -1*(courant.getValeur() + getHauteur()), endX + 20, -1*(courant.getValeur() + getHauteur()), endX, endY);
            }
            if (i == 2) {
                champ = new CubicCurve(startX, startY, startX - 20, -1*(courant.getValeur() + getHauteur()), endX + 20, -1*(courant.getValeur() + getHauteur()), endX, endY);
                champ.setRotationAxis(new Point3D(1, 0, 0));
                champ.setRotate(-90);
                champ.setLayoutY(champ.getStartY());
                champ.setTranslateZ(-champ.getControlY1());

            }
            if (i == 3) {
                champ = new CubicCurve(startX, startY, startX - 20, -1*(courant.getValeur() + getHauteur()), endX + 20, -1*(courant.getValeur() + getHauteur()), endX, endY);
                champ.setRotationAxis(new Point3D(1, 0, 0));
                System.out.println(champ.getStartX());
                System.out.println(champ.getStartY());
                champ.setRotate(90);
                champ.setLayoutY(champ.getStartY());
                champ.setTranslateZ(champ.getControlY1());

            }
            champ.setStrokeWidth(4);
            champ.setStroke(javafx.scene.paint.Color.YELLOW);
            champ.setStrokeLineCap(StrokeLineCap.ROUND);
            champ.setFill(Color.TRANSPARENT);
            group.getChildren().add(champ);
        }
        return group;
    }
}