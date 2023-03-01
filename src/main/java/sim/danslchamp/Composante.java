package sim.danslchamp;

import java.awt.*;
import java.util.ArrayList;

public abstract class Composante {

    private int posX;
    private int posY;

    private int hauteur;
    private int largeur;

    // Getters & Setters

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
