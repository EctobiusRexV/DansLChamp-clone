package sim.danslchamp;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class Bobine extends Composante {

    public static final int DEFAUT_NOMBRE_DE_SPIRES = 4;
    public static final int DEFAUT_LONGUEUR_m = 4;
    public static final double DEFAUT_RAYON_m = 2;

    private int nombreDeSpires = DEFAUT_NOMBRE_DE_SPIRES;
    private double longueur_m = DEFAUT_LONGUEUR_m;
    private double rayon_m = DEFAUT_RAYON_m;

    private double inductance;

    private String name;

    /**
     * Permet la construction d'une bobine depuis les attributs SVG
     *
     * @param nombreDeSpires
     * @param longueur_m
     * @param rayon_m
     */
    public Bobine(String nombreDeSpires, String longueur_m, String rayon_m) {
        setNombreDeSpires(nombreDeSpires);
        setLongueur_m(longueur_m);
        setRayon_m(rayon_m);
    }

    @Override
    public void initGroupe3D() {

    }

    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(0, 10), new Point(80, 10)};
    }

    @Override
    public String getName() {
        return name;
    }

    public void setNombreDeSpires(String nombreDeSpires) {


        if (!nombreDeSpires.isEmpty()) {
            if (nombreDeSpires.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "NombreDeSpires");
            } else {
                try{
                    this.nombreDeSpires = Integer.parseInt(nombreDeSpires);
                } catch (NumberFormatException e){
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "NombreDeSpires");
                }
            }
        }else {
            this.nombreDeSpires = 0;
        }
    }

    public void setLongueur_m(String longueur_m) {
        if (!longueur_m.isEmpty()) {
            if (longueur_m.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Longueur (m)");
            } else {
                try {
                    this.longueur_m = Double.parseDouble(longueur_m);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Longueur (m)");
                }
            }
        } else {
            this.longueur_m = 0;
        }
    }

    public void setRayon_m(String rayon_m) {
        if (!rayon_m.isEmpty()) {
            if (rayon_m.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Rayon (m)");
            } else {
                try{
                    this.rayon_m = Double.parseDouble(rayon_m);
                } catch (NumberFormatException e){
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Rayon (m)");
                }
            }
        }else {
            this.rayon_m = 0;
        }
    }
}
