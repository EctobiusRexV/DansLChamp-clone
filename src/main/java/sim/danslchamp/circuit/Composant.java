package sim.danslchamp.circuit;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import sim.danslchamp.Config;
import sim.danslchamp.Util.DanslChampUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import static sim.danslchamp.DansLChampApp.SVG_LOADER;
import static sim.danslchamp.Util.DanslChampUtil.Capitalize;

public abstract class Composant {

    /**
     * Désigne les champs affichables dans l'infobulle d'un composant
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Affichable {
    }

    /**
     * Désigne les champs modifiables dans la liste des composants (par le fait même sauvegardés)
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Modifiable {
    }


    /**
     * Les composants concrets doivent définir leurs connecteurs relativement à la position (0, 0)
     */
    private Jonction[] jonctionsRelatives;


    private String label;

    @Affichable
    public Valeur reactance = new Valeur(0, Unite.UNITE, "Ω"),
            voltage = new Valeur(0, Unite.UNITE, "V"),
            courant = new Valeur(0, Unite.UNITE, "A");

    /**
     * Position absolue des connecteurs
     */
    private Jonction[] jonctions;

    /**
     * Référence sur la jonction de la borne positive
     */
    private Jonction bornePositive;

    // STYLE
    // ==================
    /**
     * Hauteur, largeur de l'image du composant
     */
    private int hauteur, largeur;

    private int posX, posY;

    // Indique si le composant est tournée de 90°
    boolean rotation90;

    private int strokeWidth = Config.defautComposantStrokeWidth;

    private Color strokeColor = Color.valueOf(Config.defautComposantStrokeColor);


    /**
     * Aide aux tests
     */
    protected Composant() {
    }

    /**
     * Construit un composant à partir d'un élément SVG.
     *
     * @param jonctionsRelatives
     * @param hauteur
     * @param largeur
     * @param posX               la position en X
     * @param posY               la position en Y
     * @param rotation90         le composant est tournée de 90°?
     */
    protected Composant(Jonction[] jonctionsRelatives, int hauteur, int largeur, int posX, int posY, boolean rotation90) {
        this.jonctionsRelatives = jonctionsRelatives;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.rotation90 = rotation90;

        setPosXY(posX, posY);
    }

    /**
     * Set la position en X et Y
     *
     * @param posX
     * @param posY
     */
    public void setPosXY(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        setJonctions();
    }

    /**
     * Ajuste les jonctions en fonction de la position absolue
     */
    private void setJonctions() {
        jonctions = Arrays.stream(jonctionsRelatives)
                .peek(jonction ->
                        jonction.translate(posX, posY))
                .toArray(Jonction[]::new);
    }

    public void setBornePositive(Jonction bornePositive) {
        this.bornePositive = bornePositive;
    }

    public Group getSymbole2D() {
        return SVG_LOADER.loadSvg(this.getClass().getResourceAsStream("symboles/" + getClass().getSimpleName() + ".svg"));
    }

    public static Group getSymbole2D(String symbole) {
        return SVG_LOADER.loadSvg(Composant.class.getResourceAsStream("symboles/" + symbole + ".svg"));
    }

    abstract Group getSymbole3D();

    abstract Group getChamp();

    // Getters & Setters

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Jonction[] getJonctions() {
        return jonctions;
    }

    public Jonction getBornePositive() {
        return bornePositive;
    }

    private ValeurNomWrapper[] getValeurs(Class annotationClass) {
        return Arrays.stream(getClass().getFields())
                .filter(field -> field.isAnnotationPresent(annotationClass))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return new ValeurNomWrapper(field.getName(), (Valeur) field.get(this));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(ValeurNomWrapper[]::new);
    }

    /**
     * @return Un tableau de Valeurs portant l'annotation Affichable
     */
    public ValeurNomWrapper[] getValeursAffichables() {
        return getValeurs(Affichable.class);
    }

    /**
     * @return Un tableau de Valeurs portant l'annotation Modifiable
     */
    public ValeurNomWrapper[] getValeursModifiables() {
        return getValeurs(Modifiable.class);
    }


    public abstract double calculResistance(double frequence);

    /**
     * @return le nom du composant (Résistor, Condensateur, etc.)
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public enum Unite {
        PLUS_PETITE_POSSIBLE(""), PICO("p"), NANO("n"), MICRO("μ"), MILLI("m"), UNITE(""), KILO("K"), MEGA("M"), GIGA("G");

        private final String symbole;

        Unite(String symbole) {
            this.symbole = symbole;
        }

        public String getSymbole() {
            return symbole;
        }

        @Override
        public String toString() {
            return getSymbole();
        }
    }

    public static class Valeur {
        private double valeur;
        private Unite unite;
        private final String symbole;

        private final static NumberFormat formatter = new DecimalFormat();

        public Valeur(double valeur, Unite unite, String symbole) {
            setValeur(valeur, unite);

            this.symbole = symbole;
        }

        public String getValeurStr() {
            return formatter.format(valeur);
        }

        public void setValeur(String valeur, Unite unite) {
            if (valeur.isEmpty()) this.valeur = 0;
            else
                try {
                    valeur = valeur.replace(',', '.');
                    setValeur(Double.parseDouble(valeur), unite);
                } catch (NumberFormatException e) {
                    DanslChampUtil.lanceAlerte("Entrée non-conforme", ""/*fixme*/);
                }
        }

        public void setValeur(double valeur, Unite unite) {
            if (unite == Unite.PLUS_PETITE_POSSIBLE)
                throw new IllegalArgumentException("L'argument Unite.PLUS_PETITE_POSSIBLE ne peut être utilisé que dans une conversion.");

            this.valeur = valeur;
            this.unite = unite;
        }

        @Override
        public String toString() {
            double valeur = getValeur(Unite.PLUS_PETITE_POSSIBLE);
            return formatter.format(valeur) + " " + unite + symbole;
        }

        /**
         * @return Une <u>NOUVELLE</u> valeur convertie à l'unité désirée.
         */
        public double getValeur(Unite unite) {
            if (unite == Unite.PLUS_PETITE_POSSIBLE) return convertirPlusPetitePossible();

            int facteur = unite.ordinal() - this.unite.ordinal();
            return valeur * Math.pow(10, -(3 * facteur));
        }

        /**
         * @return La valeur convertie à la plus petite unité possible au format ingénieur (micro, milli, ..., kilo, méga, etc.)
         */
        private double convertirPlusPetitePossible() {
            int i;
            if (valeur==0) return 0;
            if (valeur > 1_000)
                for (i = 0; valeur > 1_000; i--)
                    valeur /= 1_000;
            else
                for (i = 0; valeur < 1; i++)
                    valeur *= 1_000;

            unite = Unite.values()[this.unite.ordinal() - i];   // FIXME: 2023-04-19 Index out of bounds (-108)
            return valeur;
        }

        public Unite getUnite() {
            return unite;
        }

        public String getSymbole() {
            return symbole;
        }
    }

    /**
     * Permet de coupler une valeur avec le nom d'un champ.
     */
    public static class ValeurNomWrapper {
        public String id;
        public String nom;
        public Valeur valeur;

        public ValeurNomWrapper(String nom, Valeur valeur) {
            this.id = nom;
            this.nom = Capitalize(espacerCamelCase(nom));
            this.valeur = valeur;
        }

        /**
         * Dans un mot en camelCase, espace les majuscules (ex.: camel Case)
         */
        private static String espacerCamelCase(String str) {
            return str.replaceAll("[A-Z]", " $0");
        }
    }
}
