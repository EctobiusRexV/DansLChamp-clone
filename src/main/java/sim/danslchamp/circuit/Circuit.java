package sim.danslchamp.circuit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sim.danslchamp.Util.DanslChampUtil;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Circuit {

    private String nom;

    private final List<Composant> circuit;
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
        circuit = null;
        noeuds = null;
    }

    private Circuit(File file) throws FileNotFoundException {
        SvgLoader loader = new SvgLoader(this);

        loader.loadSvg(new FileInputStream(file));

        noeuds = jonctions.stream().filter(Jonction::estNoeud).toList();

        trouverSensDuCourant(sources, noeuds);
        circuit = trouverCircuit(sources);
    }

    public static Circuit chargerCircuit(File file) throws FileNotFoundException {
        return new Circuit(file);
    }

    /**
     * Trouve le sens du courant.
     * En partant des bornes positives des sources, définit les bornes positives des composants.
     */
    private static void trouverSensDuCourant(List<Source> sources, List<Jonction> noeuds) {
        List<Jonction> departs = new ArrayList<>(sources.stream().map(Composant::getBornePositive).toList());
        departs.addAll(noeuds);

        for (Jonction jonction:
                departs) {
            parcourirBranche(jonction);
        }
    }

    private static void parcourirBranche(Jonction aPartirDe) {
        for (Composant composant : aPartirDe.getComposants()) {
            if (composant.getBornePositive() == null) {
                Jonction next = composant.getJonctions()[0].equals(aPartirDe) ?
                        composant.getJonctions()[1] : composant.getJonctions()[0];

                composant.setBornePositive(next);
                if (next.estNoeud()) return;

                parcourirBranche(next);
            }
        }
    }

    private static List<Composant> trouverCircuit(List<Source> sources) {

        List<Composant> circuit = new ArrayList<>();

        circuit.add(sources.get(0));

        circuit = parcourirCircuit(circuit);

        return circuit;
    }

    private static List<Composant> parcourirCircuit(List<Composant> circuit) {
        Composant dernier = circuit.get(circuit.size() - 1);

        Jonction jonctionPlus = dernier.getBornePositive();

        if (jonctionPlus.estNoeud()){

            for (int i = 0; i < jonctionPlus.getComposants().size() - 1; i++) {

                SousCircuit sousCircuit = new SousCircuit();

                sousCircuit.addComposant(jonctionPlus.getComposants().get(i + 1));

                circuit.add(parcourirSousCircuit(sousCircuit));

            }

        }
        else {
            for (Composant composant : jonctionPlus.getComposants()){
                if (!circuit.contains(composant)) {
                    circuit.add(composant);
                    parcourirCircuit(circuit);
                }
            }
        }
        return circuit;
    }

    private static SousCircuit parcourirSousCircuit(SousCircuit sousCircuit) {
        Composant dernier = sousCircuit.getLast();

        Jonction jonctionPlus = dernier.getBornePositive();

        if (!jonctionPlus.estNoeud()){
            for (Composant composant : jonctionPlus.getComposants()){
                if (!sousCircuit.getComposants().contains(composant)) {
                    sousCircuit.addComposant(composant);
                    parcourirSousCircuit(sousCircuit);
                }
            }
        }

        return sousCircuit;
    }

    public Composant addComposant(String composantType, int posX, int posY, boolean rotation90) {
        Composant composant = null;
        try {
            // Instancier la classe
            composant = (Composant) Class.forName("sim.danslchamp.circuit." + composantType)
                    .getDeclaredConstructors()[0]   // SVP qu'un seul constructeur!
                    .newInstance(posX, posY, rotation90);
        } catch (ClassCastException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            DanslChampUtil.erreur( "Impossible de charger " + composantType, e.getMessage());
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

    public List<Composant> getCircuit() {
        return circuit;
    }
}
