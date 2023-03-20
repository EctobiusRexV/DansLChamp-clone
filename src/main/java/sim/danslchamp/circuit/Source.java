package sim.danslchamp.circuit;

/**
 * @author Antoine Bélisle
 */
public abstract class Source extends Composant {

    private boolean inversee;

    public Source(Jonction[] jonctionsRelatives, int hauteur, int largeur, int posX, int posY, boolean rotation90) {
        super(jonctionsRelatives, hauteur, largeur, posX, posY, rotation90);

        setBornePositive(inversee? getJonctions()[1] : getJonctions()[0]);
    }
}
