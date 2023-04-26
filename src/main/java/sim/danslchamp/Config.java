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

    public static int defautGenerateurFrequence_Hz = 60;

    public static int defautComposantStrokeWidth = 3;
    public static String defautComposantStrokeColor = "0x000000ff";

    public static String circuitRecent1 = "";
    public static String circuitRecent2 = "";
    public static String circuitRecent3 = "";

    // ============ Circuit ============

    public static double circuitDiagrammesSplitPanePosition0;
    public static double circuitDiagrammesSplitPanePosition1;
    public static double circuitPosX;
    public static double circuitPosY;
    public static double circuitLargeur;
    public static double circuitHauteur;
    public static boolean circuitMaximise = true;
    public static boolean circuitAfficherDiagramme2D = true;
    public static boolean circuitAfficherDiagramme3D = true;
    public static boolean circuitAfficherBarreDOutils = true;
    public static boolean circuitAfficherListeDesComposants = true;
    public static boolean circuitAfficherTitre = true;

    static void charger() {
        try {
            Field[] champs = Config.class.getFields();
            List<String> lines = Files.readAllLines(Path.of("config"));
            for (int i = 0; i < champs.length; i++) {
                switch (champs[i].getType().getName()) {
                    case "int" -> champs[i].setInt(null, Integer.parseInt(lines.get(i)));
                    case "double" -> champs[i].setDouble(null, Double.parseDouble(lines.get(i)));
                    case "boolean" -> champs[i].setBoolean(null, Boolean.parseBoolean(lines.get(i)));
                    default -> champs[i].set(null, lines.get(i));
                }
            }

        } catch (IOException | IllegalAccessException e) {
            System.err.println("Impossible de charger la config.");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Mauvaise version de la config.");
        }
    }

    public static void sauvegarder() {
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
