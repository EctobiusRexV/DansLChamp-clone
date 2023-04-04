package prototypes;

import org.junit.jupiter.api.Test;
import sim.danslchamp.circuit.Composant;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlusieursAnnotationsFields {

    @Composant.Affichable
    @Composant.Modifiable
    private int a,
                b,
                c;

    @Test
    void testAnnotationPlusieurFields() {
        for (Field field :
                getClass().getFields()) {
            assertTrue(field.isAnnotationPresent(Composant.Affichable.class)
                        && field.isAnnotationPresent(Composant.Modifiable.class));
        }
    }
}
