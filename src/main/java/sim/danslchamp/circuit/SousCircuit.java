package sim.danslchamp.circuit;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class SousCircuit extends Composant{

    private List<Composant> composants;


    public SousCircuit() {
        composants = new ArrayList<>();
    }

    void initGroupe3D() {

    }

    public void addComposant(Composant composant){
        composants.add(composant);
    }

    public Composant getLast() {
        return composants.get(composants.size() - 1);
    }

    public List<Composant> getComposants(){
        return composants;
    }

    @Override
    Group getSymbole3D() {
        return null;
    }

    @Override
    public String toString() {
        return "SousCircuit{" +
                "composants=" + composants +
                '}';
    }

    public void add(SousCircuit sousCircuit) {
        composants.add(sousCircuit);
    }
}
