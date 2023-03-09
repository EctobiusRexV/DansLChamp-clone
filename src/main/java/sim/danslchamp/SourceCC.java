package sim.danslchamp;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class SourceCC extends Source {

    private String name;

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



    public void setVoltage_mV(String ddp_mV) {
        if (!ddp_mV.isEmpty()) {
            if (ddp_mV.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Voltage (mV)");
            } else {
                try {
                    setVoltage_mV(Long.parseLong(ddp_mV));
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Voltage (mV)");
                }
            }
        } else {
            setVoltage_mV(0);
        }

    }
}
