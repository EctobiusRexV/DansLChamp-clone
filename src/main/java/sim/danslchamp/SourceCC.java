package sim.danslchamp;

import java.awt.*;

public class SourceCC extends Source {
    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(20,0), new Point(20,20)};
    }

    public SourceCC(String voltage_mV) {
        super(voltage_mV);
    }
}
