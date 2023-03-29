package sim.danslchamp;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Config {
    public static int defautBobineLongueur_mm = 0;
    public static int defautBobineNombreDeSpires = 0;
    public static int defautBobineRayon_mm = 0;

    public static int defautCondensateurCapacite_pF = 0;

    public static int defautResistorResistance_mOhms = 0;

    public static int defautSourceVoltage_mV = 0;

    public static int defautComposantStrokeWidth = 3;

    static void charger() {
        try {
            Field[] champs = Config.class.getFields();
            List<String> lines = Files.readAllLines(Path.of("config"));
            for (int i = 0; i < champs.length; i++) {
                champs[i].setInt(null, Integer.parseInt(lines.get(i)));
            }

        } catch (IOException | IllegalAccessException e) {
            System.err.println("Impossible de charger la config.");
            e.printStackTrace();
        }
    }

    static void sauvegarder() {
        try {
            Files.write(Path.of("config"), Arrays.stream(Config.class.getFields()).map(field ->
                    {
                        try {
                            return field.get(null).toString();
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
            ).collect(Collectors.toList()));
        } catch (IOException e) {
            System.err.println("Impossible de sauvegarder la config.");
            e.printStackTrace();
        }
    }
}
