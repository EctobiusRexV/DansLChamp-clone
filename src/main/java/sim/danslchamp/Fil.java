package sim.danslchamp;

import java.awt.*;

public class Fil extends Composante{
    @Override
    Point[] getConnecteursRelatifs() {
        throw new RuntimeException("getConnecteursRelatifs() non-implémenté pour les fils.");
    }
}
