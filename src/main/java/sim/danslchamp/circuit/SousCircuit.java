package sim.danslchamp.circuit;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class SousCircuit extends Composant{

    private List<Composant> composants;

    private double resistance;


    public SousCircuit() {
        composants = new ArrayList<>();
    }


    public void ISetResistance() {

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
    public double calculResistance(int frequence) {

        double resistance = 0.0;
        double reactanceBobine;
        reactanceBobine = 0.0;
        double reactanceCondensateur = 0.0;
        double inverseImpedenceSousCircuit = 0.0;
        double impedenceTotaleSousCircuit = 0.0;

        for (int i = 0; i < composants.size() - 1; i++) {

            Composant c = composants.get(i);

            if (c instanceof Condensateur){
                reactanceCondensateur += c.calculResistance(frequence);
            } else if (c instanceof Bobine) {
                reactanceBobine += c.calculResistance(frequence);
            } else if (c instanceof SousCircuit) {
                inverseImpedenceSousCircuit += 1 / c.calculResistance(frequence);
                if (!(composants.get(i + 1) instanceof SousCircuit)){
                    impedenceTotaleSousCircuit += 1 / inverseImpedenceSousCircuit;
                }
            }else resistance += c.calculResistance(frequence);

        }

        return Math.sqrt(Math.pow(resistance, 2) + Math.pow(reactanceCondensateur - reactanceBobine, 2)) + impedenceTotaleSousCircuit;

//        int resistance = 0;
//
//        for (Composant c : composants){
//            resistance += c.calculResistance(frequence);
//        }
//
//        return resistance;
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
