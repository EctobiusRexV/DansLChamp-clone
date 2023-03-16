package sim.danslchamp.circuit;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class Bobine extends Composant {

    public static final int DEFAUT_NOMBRE_DE_SPIRES = 4;
    public static final int DEFAUT_LONGUEUR_mm = 4;
    public static final double DEFAUT_RAYON_mm = 2;

    private int nombreDeSpires = DEFAUT_NOMBRE_DE_SPIRES;
    private double longueur_mm = DEFAUT_LONGUEUR_mm;
    private double rayon_mm = DEFAUT_RAYON_mm;

    /**
     * Permet la construction d'une bobine depuis les attributs SVG
     *
     * @param longueur_mm
     * @param nombreDeSpires
     * @param rayon_mm
     */
    public Bobine(int posX, int posY, boolean rotation90,
                  String longueur_mm, String nombreDeSpires, String rayon_mm) {
        super(new Jonction[]{new Jonction(new Point(0, 10)), new Jonction(new Point(80, 10))},
                22, 80, posX, posY, rotation90);

        setLongueur_mm(longueur_mm);
        setNombreDeSpires(nombreDeSpires);
        setRayon_mm(rayon_mm);
    }

    @Override
    void initGroupe3D() {

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

    public void setLongueur_mm(String longueur_mm) {
        if (!longueur_mm.isEmpty()) {
            if (longueur_mm.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Longueur (m)");
            } else {
                try {
                    this.longueur_mm = Double.parseDouble(longueur_mm);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Longueur (m)");
                }
            }
        } else {
            this.longueur_mm = 0;
        }
    }

    public void setRayon_mm(String rayon_mm) {
        if (!rayon_mm.isEmpty()) {
            if (rayon_mm.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Rayon (m)");
            } else {
                try{
                    this.rayon_mm = Double.parseDouble(rayon_mm);
                } catch (NumberFormatException e){
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Rayon (m)");
                }
            }
        }else {
            this.rayon_mm = 0;
        }
    }
}
