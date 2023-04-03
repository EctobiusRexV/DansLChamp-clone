package sim.danslchamp.circuit;

import sim.danslchamp.Config;

/**
 * @author Antoine Bélisle
 */
public abstract class Source extends Composant {

    private boolean inversee;

    @Modifiable
    public Valeur voltage = new Valeur(Config.defautSourceVoltage_mV, Unite.UNITE, "V");

    public Source(Jonction[] jonctionsRelatives, int hauteur, int largeur, int posX, int posY, boolean rotation90) {
        super(jonctionsRelatives, hauteur, largeur, posX, posY, rotation90);

        setBornePositive(inversee? getJonctions()[1] : getJonctions()[0]);
    }
}
