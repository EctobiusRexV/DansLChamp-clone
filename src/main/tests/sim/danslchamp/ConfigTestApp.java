package sim.danslchamp;

import java.util.Arrays;

public class ConfigTestApp {
    public static void main(String[] args) {
        Config.sauvegarder();
        Config.charger();
        Arrays.stream(Config.class.getFields()).forEach(field -> {
            try {
                System.out.println(field + ": " + field.get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
