package sim.danslchamp.circuit;

import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;

import sim.danslchamp.Config;

import java.awt.Point;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 * @author Thierry Rhéaume
 */
public class Condensateur extends Composant {

    @Affichable
    @Modifiable
    public Valeur capacite = new Valeur(Config.defautCondensateurCapacite_pF, Unite.UNITE, "F");

    private double resistance;

    /**
     * Permet la construction d'un condensateur depuis les attributs SVG
     */
    public Condensateur(int posX, int posY, boolean rotation90) {
        super(new Jonction[]{new Jonction(new Point(20, 0)), new Jonction(new Point(20, 30))},
                30, 40, posX, posY, rotation90);
    }

    @Override
    Group getSymbole3D() {
        Cylinder r = new Cylinder(getLargeur() / 2, getHauteur() / 5);
        Cylinder r2 = new Cylinder(getLargeur() / 2, getHauteur() / 5);
        r.setMaterial(new PhongMaterial(Color.GRAY));
        r2.setMaterial(new PhongMaterial(Color.GRAY));
        r2.setTranslateY(getHauteur() * 3 / 5);
        Group g = new Group();
        g.getChildren().addAll(r, r2);
        g.setLayoutX(g.getLayoutX() + getLargeur() / 2);
        g.setLayoutY(g.getLayoutY() + getHauteur() / 5);
        return g;
    }

    public Group getElectric() {
        Cylinder c = new Cylinder(2, getHauteur() / 5 * 2);
        c.setMaterial(new PhongMaterial(Color.YELLOW));
        c.setLayoutY(getPosY() + getHauteur() / 2);
        c.setLayoutX(getPosX() + getLargeur() / 2);
        Cylinder c2 = new Cylinder(2, getHauteur() / 5 * 2);
        c2.setMaterial(new PhongMaterial(Color.YELLOW));
        c2.setLayoutY(getPosY() + getHauteur() / 2);
        c2.setLayoutX(getPosX() + getLargeur() / 2);
        c2.setTranslateZ(getLargeur() / 4);
        Cylinder c3 = new Cylinder(2, getHauteur() / 5 * 2);
        c3.setMaterial(new PhongMaterial(Color.YELLOW));
        c3.setLayoutY(getPosY() + getHauteur() / 2);
        c3.setLayoutX(getPosX() + getLargeur() / 2);
        c3.setTranslateZ(-getLargeur() / 4);
        Cylinder c4 = new Cylinder(2, getHauteur() / 5 * 2);
        c4.setMaterial(new PhongMaterial(Color.YELLOW));
        c4.setLayoutY(getPosY() + getHauteur() / 2);
        c4.setLayoutX(getPosX() + getLargeur() / 4);
        Cylinder c5 = new Cylinder(2, getHauteur() / 5 * 2);
        c5.setMaterial(new PhongMaterial(Color.YELLOW));
        c5.setLayoutY(getPosY() + getHauteur() / 2);
        c5.setLayoutX(getPosX() + getLargeur() / 4 * 3);
        Group group = new Group(c, c2, c3, c4, c5);
        Tooltip infobulle = new Tooltip();
        Label valeursLabel = new Label();
        VBox infobulleVBox = new VBox(
                new Label(this.toString()),
                new Separator(Orientation.HORIZONTAL),
                valeursLabel);

        infobulle.setGraphic(infobulleVBox);

        group.setOnMousePressed(event -> {
            valeursLabel.setText("");     // Clear


            valeursLabel.setText(valeursLabel.getText().concat("La force du champ électrique dans un condensateur plan distancé de 5 mm" + " est de" + ": " + "\n" +
                    (voltage.getValeur(Unite.UNITE)) / 0.005 * 1.6) + "e-19 N");

            System.out.println(voltage.getValeur(Unite.UNITE));
            infobulle.show(group, event.getScreenX(), event.getScreenY());
        });
        group.setOnMouseReleased(event -> {
            infobulle.hide();
        });
        return group;
    }

    @Override
    Group getChamp() {
        Circle c = new Circle(getLargeur() * 3 / 5);
        c.setStrokeWidth(3);
        c.setStroke(Color.DARKRED);
        c.setStrokeLineCap(StrokeLineCap.ROUND);
        c.setFill(Color.TRANSPARENT);
        c.setRotationAxis(new Point3D(1, 0, 0));
        c.setRotate(80);
        c.setCenterX(getPosX() + getLargeur() / 2);
        c.setCenterY(getPosY() + getHauteur() * 3 / 5);
        return new Group(c, getElectric());
    }


    @Override
    public double calculResistance(double frequence) {

        if (frequence == 0) {
            return Double.MAX_VALUE;
        }

        reactance.setValeur(1 / (2 * Math.PI * frequence * capacite.getValeur(Unite.UNITE)), Unite.UNITE);

        return reactance.getValeur(Unite.UNITE);
    }

}
