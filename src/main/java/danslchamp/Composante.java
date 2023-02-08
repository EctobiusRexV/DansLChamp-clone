package danslchamp;

import java.util.ArrayList;

public abstract class Composante {

    private ArrayList<Composante> nexts = new ArrayList<>();

    private long reactance_mOhms;
    private long ddp_mV;

    public void addNext(Composante next) {
        nexts.add(next);
    }

    public ArrayList<Composante> getNexts() {
        return nexts;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
