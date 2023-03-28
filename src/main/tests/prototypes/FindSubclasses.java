package prototypes;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import sim.danslchamp.circuit.Composant;

import java.util.Set;

public class FindSubclasses {
    public static void main(String[] args) {
        Reflections reflections = new Reflections("sim.danslchamp.circuit");

        Set<Class<? extends Composant>> composantsClasses = reflections.getSubTypesOf(Composant.class);

        for (Class<? extends Composant> composantClass : composantsClasses) {
            System.out.println(composantClass.getSimpleName());
        }
    }
}
