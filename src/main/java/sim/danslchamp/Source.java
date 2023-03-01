package sim.danslchamp;

public abstract class Source extends Composante {

    public static final long DEFAUT_VOLTAGE_mV = 1000;

    public Source(String voltage_mV) {
        try {
            setVoltage_mV(Long.parseLong(voltage_mV));
        } catch (NumberFormatException e) {
            setVoltage_mV(DEFAUT_VOLTAGE_mV);
        }
    }
}
