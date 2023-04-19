package sim.danslchamp.circuit;

import sim.danslchamp.circuit.Composant;

import java.util.ArrayList;
import java.util.List;

public class Maille {

    private final int id;

    private final List<Composant> composants;

    public Maille(int id) {
        this.id = id;
        composants = new ArrayList<>();
    }

    public void addComposant(Composant c){
        composants.add(c);
    }

    public int getId() {
        return id;
    }

    public List<Composant> getComposants() {
        return composants;
    }
}
