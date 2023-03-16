package sim.danslchamp.circuit;

import sim.danslchamp.Util.DanslChampUtil;

import java.awt.*;

public class Pile extends Source {

    public Pile(int posX, int posY, boolean rotation90,
                String voltage_mV) {
        super(new Jonction[]{new Jonction(new Point(20, 0)), new Jonction(new Point(20, 30))},
                30, 40, posX, posY, rotation90);

        setVoltage_mV(voltage_mV);
    }

    public void setVoltage_mV(String voltage_mV) {
        if (!voltage_mV.isEmpty()) {
            if (voltage_mV.matches("[a-z]")) {
                DanslChampUtil.lanceAlerte("Entrée non-conforme", "Voltage (mV)");
            } else {
                try {
                    setVoltage_mV(Long.parseLong(voltage_mV));
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", "Voltage (mV)");
                }
            }
        } else {
            setVoltage_mV(0);
        }
    }
}
