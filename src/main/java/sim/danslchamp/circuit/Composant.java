package sim.danslchamp.circuit;

import javafx.scene.Group;

import java.lang.reflect.Method;
import java.util.Arrays;

import static sim.danslchamp.DansLChampApp.SVG_LOADER;

public abstract class Composant {

    /**
     * Les composants concrets doivent définir leurs connecteurs relativement à la position (0, 0)
     */
    private Jonction[] jonctionsRelatives;

    /**
     * Hauteur, largeur de l'image de la composant
     */
    private int hauteur, largeur;


    private String label;

    private int posX, posY;

    // Indique si la composante est tournée de 90°
    boolean rotation90;


    private long    reactance_mOhms,
                    voltage_mV,
                    courant_uA;

    /**
     * Position absolue des connecteurs
     */
    private Jonction[] jonctions;

    /**
     * Référence sur la jonction de la borne positive
     */
    private Jonction bornePositive;


    /**
     * Aide aux tests
     */
    protected Composant() {}

    /**
     * Construit une composante à partir d'un élément SVG.
     *
     * @param jonctionsRelatives
     * @param hauteur
     * @param largeur
     * @param posX la position en X
     * @param posY la position en Y
     * @param rotation90 la composante est tournée de 90°?
     */
    protected Composant(Jonction[] jonctionsRelatives, int hauteur, int largeur, int posX, int posY, boolean rotation90) {
        this.jonctionsRelatives = jonctionsRelatives;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.rotation90 = rotation90;

        setPosXY(posX, posY);
    }

    /**
     * Set la position en X et Y
     *
     * @param posX
     * @param posY
     */
    public void setPosXY(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        setJonctions();
    }

    public void setVoltage_mV(long voltage_mV) {
        this.voltage_mV = voltage_mV;
    }

    /**
     * Ajuste les jonctions en fonction de la position absolue
     */
    private void setJonctions() {
        jonctions = Arrays.stream(jonctionsRelatives)
                .peek(jonction ->
                        jonction.translate(posX, posY))
                .toArray(Jonction[]::new);
    }

    public void setBornePositive(Jonction bornePositive) {
        this.bornePositive = bornePositive;
    }

    public Group getSymbole2D() {
        return SVG_LOADER.loadSvg(this.getClass().getResourceAsStream("symboles\\" + getClass().getSimpleName() + ".svg"));
    }

    public static Group getSymbole2D(String symbole) {
        return SVG_LOADER.loadSvg(Composant.class.getResourceAsStream("symboles\\" +  symbole + ".svg"));
    }
    abstract Group getSymbole3D();

    // Getters & Setters

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public Jonction[] getJonctions() {
        return jonctions;
    }

    public Jonction getBornePositive() {
        return bornePositive;
    }
    public Method[] getSetMethods() {
        return Arrays.stream(getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("set"))
                .toArray(Method[]::new);
    }

    public Method[] getGetMethods() {
        return Arrays.stream(getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("get"))
                .toArray(Method[]::new);
    }

    public static String getUniteTypeFromMethod(Method methode) {
        return methode.getName()
                .substring(3)   // set/get
                .split("_")[0];
    }

    /**
     * @return le nom de la composante (Résistor, Condensateur, etc.)
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void IsetReactance_mOhms(long reactance_mOhms) {
        this.reactance_mOhms = reactance_mOhms;
    }
}
