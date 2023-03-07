package sim.danslchamp;

import javafx.scene.Group;

import java.awt.Point;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public abstract class Composante {

    private int posX;
    private int posY;

    private int hauteur;
    private int largeur;

    private Group groupe3D = new Group();


    // Getters & Setters
    public Group getGroupe3D() {
        return groupe3D;
    }
    public abstract void initGroupe3D();

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public List<Method> getSetMethods() {
        return getSetMethods(getClass().getDeclaredMethods());
    }

    public static List<Method> getSetMethods(Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().startsWith("set"))
                .toList();
    }

    private long reactance_mOhms;

    private long voltage_mV;

    /**
     * Les composantes concrètes doivent définir leurs connecteurs relativement à la position (0, 0)
     * @return la liste des connecteurs de la composante.
     */
    abstract Point[] getConnecteursRelatifs();

    public void setVoltage_mV(long voltage_mV) {
        this.voltage_mV = voltage_mV;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
