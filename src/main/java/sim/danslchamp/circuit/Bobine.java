package sim.danslchamp.circuit;

import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
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
    public Bobine(int posX, int posY, int angleRotation) {
        super(new Jonction[]{new Jonction(new Point(0, 10)), new Jonction(new Point(80, 10))},
                22, 80, posX, posY, angleRotation);
    }

    @Override
    public double calculResistance(double frequence) {


        double surface = 2 * Math.PI * rayon.getValeur(Unite.UNITE);

        double L = (mu * surface * Math.pow(nombreDeSpires.getValeur(Unite.UNITE), 2)) / longueur.getValeur(Unite.UNITE);

        reactance.setValeur(2 * Math.PI * frequence * L, Unite.UNITE);

        return reactance.getValeur(Unite.UNITE);
    }

    @Override
    Group getSymbole3D() {
        Cylinder c = new Cylinder(getLargeur() / 2, getHauteur());
        if (getLargeur() > getHauteur()) {
            c = new Cylinder(getHauteur() / 2, getLargeur());
        }
        c.setLayoutX(c.getLayoutX() + c.getHeight() / 2);
        c.setTranslateY(getHauteur() / 2);
        c.setRotate(90);
        if (!rotation90) {
            c.setRotate(90);
        }
        Group g = new Group();
        g.getChildren().addAll(c);
        return g;
    }

    @Override
    public Group getChamp() {
        double startX = this.getJonctions()[0].getPositionXY().getX();
        double startY = this.getJonctions()[0].getPositionXY().getY();
        double endX = this.getJonctions()[1].getPositionXY().getX();
        double endY = this.getJonctions()[1].getPositionXY().getY();
        double criss = courant.getValeur(Unite.UNITE);
        if (criss == 0 || criss > 5000) {
            return new Group();
        }
        Group group = new Group();
        for (int i = 0; i < 4; i++) {
            CubicCurve champ = new CubicCurve(startX, startY, ((endX - startX) / 3) + startX, criss, ((endX - startX) * 2 / 3) + startX, criss, endX, endY);
            double t = 0.5;

            double y = Math.pow(1 - t, 3) * champ.getStartY()
                    + 3 * t * Math.pow(1 - t, 2) * champ.getControlY1()
                    + 3 * Math.pow(t, 2) * (1 - t) * champ.getControlY2()
                    + Math.pow(t, 3) * champ.getEndY();

            if (i == 0) {
                champ.setControlX1(startX - 20);
                champ.setControlX2(endX + 20);
            }
            if (i == 1) {
                champ.setTranslateY(startY - y);
                champ.setRotationAxis(new Point3D(1, 0, 0));
                champ.setRotate(180);
                champ.setControlX1(startX - 20);
                champ.setControlX2(endX + 20);
            }
            if (i == 2) {
                if (startY > 30) {
                    champ.setTranslateY(y + getHauteur());
                    champ.setTranslateZ(y);
                    champ.setRotationAxis(new Point3D(1, 0, 0));
                    champ.setRotate(-90);
                    champ.setControlX1(startX - 20);
                    champ.setControlX2(endX + 20);
                }else{
                    champ.setTranslateY(-y / 2 + (getHauteur() / 2));
                    champ.setTranslateZ(-y / 2);
                    champ.setRotationAxis(new Point3D(1, 0, 0));
                    champ.setRotate(-90);
                    champ.setControlX1(startX - 20);
                    champ.setControlX2(endX + 20);
                }
            }
            if (i == 3) {
                if (startY > 30) {
                    champ.setTranslateY(y + getHauteur());
                    champ.setTranslateZ(-y);
                    champ.setRotationAxis(new Point3D(1, 0, 0));
                    champ.setRotate(90);
                    champ.setControlX1(startX - 20);
                    champ.setControlX2(endX + 20);
                }else{
                    champ.setTranslateY(-y / 2 + (getHauteur() / 2));
                    champ.setTranslateZ(y / 2);
                    champ.setRotationAxis(new Point3D(1, 0, 0));
                    champ.setRotate(90);
                    champ.setControlX1(startX - 20);
                    champ.setControlX2(endX + 20);
                }
            }
            champ.setStrokeWidth(4);
            champ.setStroke(Color.ORANGERED);
            champ.setStrokeLineCap(StrokeLineCap.ROUND);
            champ.setFill(null);
            group.getChildren().add(champ);
        }
        Tooltip infobulle = new Tooltip();
        javafx.scene.control.Label valeursLabel = new javafx.scene.control.Label();
        VBox infobulleVBox = new VBox(
                new Label(this.toString()),
                new Separator(Orientation.HORIZONTAL),
                valeursLabel);

        infobulle.setGraphic(infobulleVBox);

        group.setOnMousePressed(event -> {
            double B = 4 * Math.PI * (nombreDeSpires.getValeur(Unite.UNITE) / longueur.getValeur(Unite.UNITE)) * courant.getValeur(Unite.UNITE) / 10000000;
            double Bext = (B * Math.pow(rayon.getValeur(Unite.UNITE), 2)) / (2 * Math.pow(Math.pow(rayon.getValeur(Unite.UNITE), 2) + Math.pow(0.5, 2), (3 / 2)));
            valeursLabel.setText("La force du champ magnétique à 0.5 mètres de la bobine est de: " + "\n" + new Valeur(Bext, Unite.UNITE, "T"));     // Clear

            valeursLabel.setText(valeursLabel.getText().concat("\n" + "La force du champ magnétique dans la bobine" + " est de" + ": "
                    + "\n" + new Valeur(B, Unite.UNITE, "T")));

            infobulle.show(group, event.getScreenX(), event.getScreenY());
            event.consume();
        });
        group.setOnMouseReleased(event -> {
            infobulle.hide();
        });
        return group;
    }
}
