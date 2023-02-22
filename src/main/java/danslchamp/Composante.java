package danslchamp;

import javafx.scene.Group;

import java.util.ArrayList;

public abstract class Composante {

    private ArrayList<Composante> nexts = new ArrayList<>();

    private Group groupe;

    private long reactance_mOhms;
    private long ddp_mV;


    public void addNext(Composante next) {
        nexts.add(next);
    }

    public ArrayList<Composante> getNexts() {
        return nexts;
    }

    public Group getGroupe() {
        return groupe;
    }

    public void setGroupe() {
        this.groupe = new Group();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
