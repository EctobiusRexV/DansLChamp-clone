package sim.danslchamp.circuit;

import javafx.scene.Group;
import sim.danslchamp.Config;

import java.awt.*;

/**
 * @author Antoine Bélisle
 * @author Mathis-Rosa Wilson
 */
public class Bobine extends Composant {

    @Affichable
    @Modifiable
    public Valeur  nombreDeSpires = new Valeur(Config.defautBobineNombreDeSpires, Unite.UNITE, ""),
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

        double L = (mu * surface * Math.pow(nombreDeSpires.getValeur(), 2))/longueur.getValeur();

        reactance.setValeur(2 * Math.PI * frequence * L, Unite.UNITE);

        return reactance.getValeur();
    }

    @Override
    Group getSymbole3D() {
        Group g = new Group();
        //set layout xy
        if (rotation90) {
            g.setRotate(90);// ou la shape
        }
        g.getChildren().addAll();
        return g;
    }


}
