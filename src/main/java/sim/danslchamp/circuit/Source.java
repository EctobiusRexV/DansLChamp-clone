package sim.danslchamp.circuit;

import sim.danslchamp.Config;

/**
 * @author Antoine Bélisle
 */
public abstract class Source extends Composant {

    private boolean inversee;

    @Modifiable
    public Valeur voltage = new Valeur(Config.defautSourceVoltage_mV, Unite.UNITE, "V");

    public Source(Jonction[] jonctionsRelatives, int hauteur, int largeur, int posX, int posY, int angleRotation) {
        super(jonctionsRelatives, hauteur, largeur, posX, posY, angleRotation);

        setBornePositive(inversee? getJonctions()[1] : getJonctions()[0]);
    }

    @Override
    public double calculResistance(double frequence) {
        return 0;
    }
}
