/*
package danslchamp;

import static org.junit.jupiter.api.Assertions.*;

class CircuitTest {
    public CircuitTest() {
//        circuitSerie();
        circuitParallele();
    }

    private void circuitSerie() {
        Fil fil1 = new Fil();
        Fil fil2 = new Fil();

        Source source = new Source();
        Resistor resistor = new Resistor();

        Bobine bobine = new Bobine();

        source.addNext(fil1);
        fil1.addNext(resistor);
        resistor.addNext(fil2);
//        fil2.addNext(source);

        Circuit circuit = new Circuit(source);

        circuit.traverser();
        System.out.println("Fini");
    }

    private void circuitParallele() {
        Fil fil1 = new Fil();
        Fil fil2 = new Fil();

        Source source = new Source();
        Resistor resistor = new Resistor();

        Bobine bobine = new Bobine();

        source.addNext(fil1);
        fil1.addNext(resistor);
        fil1.addNext(bobine);
        resistor.addNext(fil2);
//        fil2.addNext(source);

        Circuit circuit = new Circuit(source);

        circuit.traverser();
        System.out.println("Fini");
    }


    public static void main(String[] args) {
        new CircuitTest();
    }
}
*/
