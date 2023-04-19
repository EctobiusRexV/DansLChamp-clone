/*
package sim.danslchamp.circuit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComposantTest {

    // VALEUR
    // =================================

    // 3 GV
    final Composant.Valeur valeur = new Composant.Valeur(3, Composant.Unite.GIGA, "V");
    final Composant.Valeur valeurGrande = new Composant.Valeur(30_000d, Composant.Unite.KILO, "A");
    final Composant.Valeur valeurPetite = new Composant.Valeur(0.000_010_3, Composant.Unite.UNITE, "Pa");

    @Test
    void testToString() {
        assertEquals("3 GV", valeur.toString());
    }

    @Test
    void convertir() {
        Composant.Valeur valeurFinale = valeur.getValeur(Composant.Unite.UNITE);

        assertEquals("3000000000 V", valeurFinale.toString());
    }

    @Test
    void nePeutPasConstruireValeurAvecUnitePlusPetitePossible() {
        assertThrows(IllegalArgumentException.class, () ->
                new Composant.Valeur(3, Composant.Unite.PLUS_PETITE_POSSIBLE, "A")
        );
    }

    @Test
    void convertirALaPlusPetitePossible_increment() {
        Composant.Valeur valeurFinale = valeurGrande.getValeur(Composant.Unite.PLUS_PETITE_POSSIBLE);

        assertEquals("30 MA", valeurFinale.toString());
    }

    @Test
    void convertirALaPlusPetitePossible_decrement() {
        Composant.Valeur valeurFinale = valeurPetite.getValeur(Composant.Unite.PLUS_PETITE_POSSIBLE);

        assertEquals("10,3 μPa", valeurFinale.toString());
    }
}*/
