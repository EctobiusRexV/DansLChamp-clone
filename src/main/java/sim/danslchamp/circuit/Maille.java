package sim.danslchamp.circuit;

import sim.danslchamp.circuit.Composant;

import java.util.ArrayList;
import java.util.List;

public class Maille {

    private final int id;

    private final List<Composant> composantes;

    public Maille(int id) {
        this.id = id;
        composantes = new ArrayList<>();
    }

    public void addComposante(Composant c){
        composantes.add(c);
    }

    public int getId() {
        return id;
    }

    public List<Composant> getComposantes() {
        return composantes;
    }
}
