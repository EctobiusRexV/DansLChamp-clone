package sim.danslchamp.circuit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import sim.danslchamp.Util.DanslChampUtil;
import sim.danslchamp.svg.SvgBasicElementHandler;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Circuit {

    private String nom;

    private final List<Maille> mailles;
    private final List<Jonction> noeuds;


    private final ObservableList<Composant> composants = FXCollections.observableArrayList();
    private final List<Source> sources = new ArrayList<>();

    private final Diagramme.Diagramme2D diagramme2D = new Diagramme.Diagramme2D();

    private final Diagramme.Diagramme3D diagramme3D = new Diagramme.Diagramme3D();

    /**
     * Un circuit est composé de multiples composants connectés les uns avec les autres.
     * Pour un point de connexion donné (jonction), on énumère la liste des composants qui y sont liés.
     * <p>
     * Chaque composant définit relativement la position de ses jonctions. Ce sont les positions où il est graphiquement
     * possible de joindre le composant à une autre. L'emplacement des jonctions absolu est calculé suivant l'emplacement absolu
     * du composant (x=, y=), additionné de la position relative de ses jonctions (translation).
     * <p>
     * Une jonctions liant plus de deux composants est un noeud, suivant la loi des noeuds.
     */
    private final ArrayList<Jonction> jonctions = new ArrayList<>();


    // fixme
    public Circuit() {
        mailles = null;
        noeuds = null;
    }

    private Circuit(File file) throws FileNotFoundException {
        SvgLoader loader = new SvgLoader(this);

        loader.loadSvg(new FileInputStream(file));

        noeuds = jonctions.stream().filter(Jonction::estNoeud).toList();

        trouverSensDuCourant(jonctions, sources);
        mailles = trouverMailles();
    }

    public static Circuit chargerCircuit(File file) throws FileNotFoundException {
        return new Circuit(file);
    }

    /**
     * Trouve le sens du courant.
     * En partant des bornes positives des sources, définit les bornes positives des composants.
     */
    private static void trouverSensDuCourant(List<Jonction> jonctions, List<Source> sources) {
        Set<Jonction> depart = new HashSet<>(sources.stream().map(Composant::getBornePositive).toList());

        Iterator<Jonction> it = depart.iterator();

        while (it.hasNext()){

            depart.add(parcourirBranche(it.next()));

        }
    }

    private static Jonction parcourirBranche(Jonction jonction) {
        if (!jonction.estNoeud()) {
            for (Composant c : jonction.getComposants()){
                if (c.getBornePositive() == null){
                    if (c.getJonctions()[0].equals(jonction)){
                        c.setBornePositive(c.getJonctions()[1]);
                        return parcourirBranche(c.getJonctions()[1]);
                    }else {
                        c.setBornePositive(c.getJonctions()[0]);
                        return parcourirBranche(c.getJonctions()[0]);
                    }
                }
            }
        }
        return jonction;
    }

    private static List<Maille> trouverMailles() {
        return new ArrayList<>();
    }

    public Composant addComposant(String composantType, int posX, int posY, boolean rotation90) {
        try {
            // Instancier la classe
            return addComposant((Class<Composant>) Class.forName("sim.danslchamp.circuit." + composantType), posX, posY, rotation90);
        } catch (ClassCastException | ClassNotFoundException e) {
            DanslChampUtil.erreur( "Impossible de charger " + composantType, e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public Composant addComposant(Class<? extends Composant> composantClass, int posX, int posY, boolean rotation90) {
        Composant composant = null;
        try {
            // Instancier la classe
            composant = (Composant) composantClass
                    .getDeclaredConstructors()[0]   // SVP qu'un seul constructeur!
                    .newInstance(posX, posY, rotation90);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            DanslChampUtil.erreur( "Impossible de charger " + composantClass.getSimpleName(), e.getMessage());
            e.printStackTrace();
        }

        return addComposant(composant);
    }

    public Composant addComposant(Composant composant) {
        composants.add(composant);
        if (composant instanceof Source) sources.add((Source) composant);

        addJonction(composant);

        for (Diagramme diagramme:
             getDiagrammes()) {
            diagramme.addComposant(composant);
        }

        return composant;
    }

    /**
     * Ajoute les jonction de {@code composant} à la liste des jonctions.
     * Si la jonction existe déjà, le composant est ajouté à la liste.
     * Sinon, la jonction est ajoutée et une nouvelle liste contenant le composant y est associé.
     * @see Circuit#jonctions
     * @param composant
     */
    private void addJonction(Composant composant) {
        for (Jonction jonction:
                composant.getJonctions()) {

            int jonctionIdx = jonctions.indexOf(jonction);

            if(jonctionIdx == -1) {
                jonction.addComposant(composant);
                jonctions.add(jonction);
            } else {
                jonctions.get(jonctionIdx).addComposant(composant);
            }
        }
    }



    // GETTERS & SETTERS

    public ObservableList<Composant> getComposants() {
        return composants;
    }

    public Diagramme.Diagramme2D getDiagramme2D() {
        return diagramme2D;
    }

    public Diagramme.Diagramme3D getDiagramme3D() {
        return diagramme3D;
    }

    public Diagramme[] getDiagrammes() {
        return new Diagramme[]{diagramme2D, diagramme3D};
    }

    // Pour les tests
    protected ArrayList<Jonction> getJonctions() {
        return jonctions;
    }
}
