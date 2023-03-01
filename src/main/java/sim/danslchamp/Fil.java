package sim.danslchamp;

import java.awt.*;

public class Fil extends Composante{

    private final String name = "fil";
    @Override
    Point[] getConnecteursRelatifs() {
        throw new RuntimeException("getConnecteursRelatifs() non-implémenté pour les fils.");
    }

    @Override
    public String getName() {
        return name;
    }
}
