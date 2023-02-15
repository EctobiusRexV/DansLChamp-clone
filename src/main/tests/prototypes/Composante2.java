package prototypes;

import javafx.scene.Group;

import java.util.ArrayList;

public abstract class Composante2 {

    private int x;
    private int y;

    private int largeur;
    private int hauteur;

    private Group groupe = new Group();

    private long reactance_mOhms;
    private long ddp_mV;


    public Group getGroupe() {
        return groupe;
    }


    public abstract void setGroupe();

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
