package sim.danslchamp;

import java.awt.*;

public class Bobine extends Composante {

    public static final int DEFAUT_NOMBRE_DE_SPIRES = 4;
    public static final int DEFAUT_LONGUEUR_m = 4;
    public static final double DEFAUT_RAYON_m = 2;

    private int nombreDeSpires;
    private double longueur_m;
    private double rayon_m;

    private double inductance;

    private final String name = "Bobine";

    /**
     * Permet la construction d'une bobine depuis les attributs SVG
     * @param nombreDeSpires
     * @param longueur_m
     * @param rayon_m
     */
    public Bobine(String nombreDeSpires, String longueur_m, String rayon_m) {
        try {
            this.nombreDeSpires = Integer.parseInt(nombreDeSpires);
        } catch (NumberFormatException e) {
            this.nombreDeSpires =  DEFAUT_NOMBRE_DE_SPIRES;
        }

        try {
            this.longueur_m = Integer.parseInt(longueur_m);
        } catch (NumberFormatException e) {
            this.longueur_m =  DEFAUT_LONGUEUR_m;
        }

        try {
            this.rayon_m = Double.parseDouble(rayon_m);
        } catch (NumberFormatException e) {
            this.rayon_m =  DEFAUT_RAYON_m;
        }
    }

    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(0,10), new Point(80,10)};
    }

    @Override
    public String getName() {
        return name;
    }

    public void setNombreDeSpires(String nombreDeSpires) {

        this.nombreDeSpires = Integer.parseInt(nombreDeSpires);
    }

    public void setLongueur_m(String longueur_m) {
        this.longueur_m = Double.parseDouble(longueur_m);
    }

    public void setRayon_m(String rayon_m) {
        this.rayon_m = Double.parseDouble(rayon_m);
    }
}
