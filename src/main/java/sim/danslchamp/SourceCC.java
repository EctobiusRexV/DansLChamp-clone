package sim.danslchamp;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class SourceCC extends Source {

    private final String name = "Source CC";

    private long ddp_mv;

    public SourceCC(String voltage_mV) {
        super(voltage_mV);
    }

    @Override
    Point[] getConnecteursRelatifs() {
        return new Point[]{new Point(20, 0), new Point(20, 20)};
    }


    public String getName() {
        return name;
    }



    public void setDdp_mv(String ddp_mv) {
        if (!ddp_mv.isEmpty()) {
            if (ddp_mv.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme, enlevez le dernier caractère entré", "ddp_mv");
            } else {
                try {
                    this.ddp_mv = Long.parseLong(ddp_mv);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme, enlevez le dernier caractère entré", "ddp_mv");
                }
            }
        } else {
            this.ddp_mv = 0;
        }

    }
}
