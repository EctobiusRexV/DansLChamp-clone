package sim.danslchamp;

import java.awt.*;

public class SourceCC extends Source {

    public SourceCC(String voltage_mV) {
        super(voltage_mV);
    }

    public void setVoltage_mV(String voltage_mV) {
        this.setVoltage_mV(Long.parseLong(voltage_mV));
    }

    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(20,0), new Point(20,20)};
    }
}
