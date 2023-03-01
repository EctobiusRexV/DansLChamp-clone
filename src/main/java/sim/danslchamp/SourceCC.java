package sim.danslchamp;

import java.awt.*;

public class SourceCC extends Source {

    private final String name = "Source CC";

    private long ddp_mv;
    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(20,0), new Point(20,20)};
    }

    @Override
    public String getName() {
        return name;
    }

    public SourceCC(String voltage_mV) {
        super(voltage_mV);
    }

    public void setDdp_mv(String ddp_mv) {
        this.ddp_mv = Long.parseLong(ddp_mv);
    }
}
