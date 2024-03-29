package sim.danslchamp.circuit;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class SousCircuit extends Composant{

    private List<Composant> composants;

    private double resistanceEquiSousCircuits;


    public SousCircuit() {
        composants = new ArrayList<>();
    }


    public void ISetResistanceEquiSousCircuits(double resistanceEquiSousCircuits) {

        this.resistanceEquiSousCircuits = resistanceEquiSousCircuits;
    }

    public double getResistanceEquiSousCircuits() {
        return resistanceEquiSousCircuits;
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
    public Group getChamp() {
        return new Group();
    }

    @Override
    public double calculResistance(double frequence) {

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

                    for (int j = i; j > 0; j--) {
                        if (!(composants.get(j) instanceof SousCircuit)) {
                            break;
                        }

                        ((SousCircuit) composants.get(j)).ISetResistanceEquiSousCircuits(impedenceTotaleSousCircuit);
                    }
                }
            }else resistance += c.calculResistance(frequence);

        }

        reactance.setValeur(Math.sqrt(Math.pow(resistance, 2) + Math.pow(reactanceCondensateur - reactanceBobine, 2)) + impedenceTotaleSousCircuit, Unite.UNITE);

        return reactance.getValeur(Unite.UNITE);

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
