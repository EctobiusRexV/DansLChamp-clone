package sim.danslchamp.circuit;

import sim.danslchamp.Composante;

import java.util.ArrayList;
import java.util.List;

public class Maille {

    private final int id;

    private final List<Composante> composantes;

    public Maille(int id) {
        this.id = id;
        composantes = new ArrayList<>();
    }

    public void addComposante(Composante c){
        composantes.add(c);
    }

    public int getId() {
        return id;
    }

    public List<Composante> getComposantes() {
        return composantes;
    }
}
