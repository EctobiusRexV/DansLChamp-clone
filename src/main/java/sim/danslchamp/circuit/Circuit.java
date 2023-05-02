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

    private List<Composant> circuit = new ArrayList<>();

    private List<SousCircuit> sousCircuits;
    private double resistanceEqui = 0;

    private double frequence = 0;
    private final List<Jonction> noeuds;


    private final ObservableList<Composant> composants = FXCollections.observableArrayList();
    private final ObservableList<Composant> composantsSansFils = FXCollections.observableArrayList();

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
    private final ObservableList<Jonction> jonctions = FXCollections.observableArrayList();


    // fixme
    public Circuit() {
        circuit = null;
        noeuds = null;
    }

    private Circuit(File file) throws FileNotFoundException {
        SvgLoader loader = new SvgLoader(this);

        loader.loadSvg(new FileInputStream(file));

        noeuds = jonctions.stream().filter(Jonction::estNoeud).toList();

        sousCircuits = new ArrayList<>();


        if (sources.size() == 0) return;
        circuit = trouverCircuit();
        calculCircuit();
        System.out.println(resistanceEqui);
    }

    public static Circuit chargerCircuit(File file) throws FileNotFoundException {
        return new Circuit(file);
    }

    public void calculCircuit() {

        if (sources.get(0) instanceof Générateur) {
            frequence = ((Générateur) sources.get(0)).frequence.getValeur(Composant.Unite.UNITE);
        } else {
            frequence = 0;
        }

        ((Composant) sources.get(0)).voltage.setValeur(sources.get(0).voltage.getValeur(Composant.Unite.UNITE), Composant.Unite.UNITE);

        resistanceEqui = trouverResistanceEqui();
        trouverCourantSimple();
        trouverDDPSimple();
        trouverCourantBranchesParalleles(sousCircuits);
        trouverDDPBranchesParalleles();


    }

    private void trouverDDPBranchesParalleles() {
        for (SousCircuit sousCircuit : sousCircuits) {
            for (Composant c : sousCircuit.getComposants()) {
                c.voltage.setValeur(c.reactance.getValeur(Composant.Unite.UNITE) * c.courant.getValeur(Composant.Unite.UNITE), Composant.Unite.UNITE);
                if (c instanceof SousCircuit){

                    for (Composant composant : ((SousCircuit) c).getComposants()){
                        composant.voltage.setValeur(c.courant.getValeur(Composant.Unite.UNITE) * composant.reactance.getValeur(Composant.Unite.UNITE), Composant.Unite.UNITE);
                    }

                }



            }
        }
    }

    private void trouverCourantBranchesParalleles(List<SousCircuit> sousCircuits) {

        for (SousCircuit sousCircuit : sousCircuits) {
            sousCircuit.courant.setValeur(sousCircuit.voltage.getValeur(Composant.Unite.UNITE) / sousCircuit.reactance.getValeur(Composant.Unite.UNITE), Composant.Unite.UNITE);
//            for (Composant c : sousCircuit.getComposants()) {
//
//                if (c instanceof SousCircuit) {
//
//                } else {
//                    c.courant.setValeur(sousCircuit.courant.getValeur(Composant.Unite.UNITE), Composant.Unite.UNITE);
//
//                }
//
//            }
            List<SousCircuit> parallelesDansParallele = new ArrayList<>();
            for (int i = 0; i < sousCircuit.getComposants().size(); i++) {

                if (sousCircuit.getComposants().get(i) instanceof SousCircuit) {
                    if (sousCircuit.getComposants().get(i + 1) instanceof SousCircuit){
                        sousCircuit.getComposants().get(i).voltage.setValeur(sousCircuit.courant.getValeur(Composant.Unite.UNITE) * ((SousCircuit) sousCircuit.getComposants().get(i)).getResistanceEquiSousCircuits(), Composant.Unite.UNITE);
                        parallelesDansParallele.add((SousCircuit) sousCircuit.getComposants().get(i));
                    } else {
                        sousCircuit.getComposants().get(i).voltage.setValeur(sousCircuit.courant.getValeur(Composant.Unite.UNITE) * ((SousCircuit) sousCircuit.getComposants().get(i)).getResistanceEquiSousCircuits(), Composant.Unite.UNITE);
                        parallelesDansParallele.add((SousCircuit) sousCircuit.getComposants().get(i));
                        trouverCourantBranchesParalleles(parallelesDansParallele);
                    }
                } else {
                    sousCircuit.getComposants().get(i).courant.setValeur(sousCircuit.courant.getValeur(Composant.Unite.UNITE), Composant.Unite.UNITE);
                }
            }
        }
    }

    private void trouverDDPSimple() {

        for (int i = 0; i < circuit.size(); i++) {

            Composant actuel = circuit.get(i);

            if (actuel instanceof SousCircuit) {

//                actuel.voltage.setValeur(circuit.get(i - 1).voltage.getValeur(), Composant.Unite.UNITE);

                if (!(circuit.get(i - 1) instanceof SousCircuit)) {
                    actuel.voltage.setValeur(actuel.courant.getValeur(Composant.Unite.UNITE) * ((SousCircuit) actuel).getResistanceEquiSousCircuits(), Composant.Unite.UNITE);
                } else {
                    actuel.voltage.setValeur(circuit.get(i - 1).voltage.getValeur(Composant.Unite.UNITE), Composant.Unite.UNITE);
                }

            } else
                actuel.voltage.setValeur(actuel.courant.getValeur(Composant.Unite.UNITE) * actuel.reactance.getValeur(Composant.Unite.UNITE), Composant.Unite.UNITE);

        }


    }

    private void trouverCourantSimple() {
        double ddpSource = sources.get(0).voltage.getValeur(Composant.Unite.UNITE);

        if (resistanceEqui != 0) {
            double courantSimple = ddpSource / resistanceEqui;

            for (Composant c : circuit) {
                c.courant.setValeur(courantSimple, Composant.Unite.UNITE);
            }
        } else {
            for (Composant c : circuit) {
                c.courant.setValeur(Double.MAX_VALUE, Composant.Unite.UNITE);
            }
        }


    }

    private double trouverResistanceEqui() {

        double resistance = 0;
        double reactanceBobine = 0;
        double reactanceCondensateur = 0;
        double inverseImpedenceSousCircuit = 0;
        double impedenceTotaleSousCircuit = 0;

        for (int i = 0; i < circuit.size() - 1; i++) {

            Composant c = circuit.get(i);

            if (c instanceof Condensateur) {
                reactanceCondensateur += c.calculResistance(frequence);
            } else if (c instanceof Bobine) {
                reactanceBobine += c.calculResistance(frequence);
            } else if (c instanceof SousCircuit) {
                inverseImpedenceSousCircuit += 1 / c.calculResistance(frequence);
                if (!(circuit.get(i + 1) instanceof SousCircuit)) {
                    impedenceTotaleSousCircuit += 1 / inverseImpedenceSousCircuit;

                    for (int j = i; j > 0; j--) {
                        if (!(circuit.get(j) instanceof SousCircuit)) {
                            break;
                        }

                        ((SousCircuit) circuit.get(j)).ISetResistanceEquiSousCircuits(impedenceTotaleSousCircuit);
                    }
                }
            } else resistance += c.calculResistance(frequence);

        }

        return Math.sqrt(Math.pow(resistance, 2) + Math.pow(reactanceCondensateur - reactanceBobine, 2)) + impedenceTotaleSousCircuit;
    }

    /**
     * Trouve le sens du courant.
     * En partant des bornes positives des sources, définit les bornes positives des composants.
     */
    private void trouverSensDuCourant() {
        List<Jonction> departs = new ArrayList<>(sources.stream().map(Composant::getBornePositive).toList());
        departs.addAll(noeuds);

        for (Jonction jonction : departs) {
            parcourirBranche(jonction);
        }
    }

    private void parcourirBranche(Jonction aPartirDe) {
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

    private List<Composant> trouverCircuit() {
        trouverSensDuCourant();
        circuit.add(sources.get(0));


        circuit = parcourirCircuit();

        for (Composant c : circuit) {
            if (c instanceof SousCircuit) {
                sousCircuits.add((SousCircuit) c);
            }
        }

        return circuit;
    }

    private List<Composant> parcourirCircuit() {
        Composant dernier = circuit.get(circuit.size() - 1);

        Jonction jonctionPlus = dernier.getBornePositive();

        if (jonctionPlus.estNoeud()) {
            SousCircuit newSousCircuit = null;

            for (int i = 0; i < jonctionPlus.getComposants().size() - 1; i++) {
                SousCircuit sousCircuit = new SousCircuit();
                sousCircuit.addComposant(jonctionPlus.getComposants().get(i + 1));

                newSousCircuit = parcourirSousCircuit(sousCircuit);

                circuit.add(newSousCircuit);

            }
            for (Composant c : newSousCircuit.getBornePositive().getComposants()) {
                if (!circuit.contains(c)) {
                    circuit.add(c);
                }
            }
            parcourirCircuit();
        } else {
            for (Composant composant : jonctionPlus.getComposants()) {
                if (!circuit.contains(composant)) {
                    circuit.add(composant);
                    parcourirCircuit();
                }
            }
        }
        return circuit;
    }

    private SousCircuit parcourirSousCircuit(SousCircuit sousCircuit) {
        Composant dernier = sousCircuit.getLast();

        Jonction jonctionPlus = dernier.getBornePositive();

        jonctionPlus = jonctions.get(jonctions.indexOf(jonctionPlus));

        int nbCompPositives = 0;

        for (Composant c : jonctionPlus.getComposants()) {
            if (c.getBornePositive().equals(jonctionPlus)) {
                nbCompPositives++;
            }
        }

        if (!jonctionPlus.estNoeud()) {
            for (Composant composant : jonctionPlus.getComposants()) {
                if (!sousCircuit.getComposants().contains(composant)) {
                    sousCircuit.addComposant(composant);
                    parcourirSousCircuit(sousCircuit);
                }
            }
        } else if (nbCompPositives < 2) {

            for (Composant c : jonctionPlus.getComposants()) {
                if (!(c.getBornePositive().equals(jonctionPlus))) {
                    SousCircuit newSousCircuit = new SousCircuit();
                    newSousCircuit.addComposant(c);
                    parcourirSousCircuit(newSousCircuit);
                    sousCircuit.addComposant(newSousCircuit);
                }//else {
//                    for (Composant composant : jonctionPlus.getComposants()) {
//                        if (!sousCircuit.getComposants().contains(composant)) {
//                            sousCircuit.addComposant(composant);
//                            parcourirSousCircuit(sousCircuit);
//                        }
//                    }
//                }
            }

            for (Composant c : sousCircuit.getLast().getBornePositive().getComposants()) {
                if (!c.getBornePositive().equals(sousCircuit.getLast().getBornePositive())) {
                    sousCircuit.addComposant(c);
                }
            }

            parcourirSousCircuit(sousCircuit);

        } else {
            sousCircuit.setBornePositive(jonctionPlus);
        }

        return sousCircuit;
    }

    public Composant addComposant(String composantType, int posX, int posY, int angleRotation) {
        try {
            // Instancier la classe
            Composant composant = Composant.getInstance((Class<Composant>) Class.forName("sim.danslchamp.circuit." + composantType));
            composant.setAngleRotation(angleRotation);
            composant.setPosXY(posX, posY);
            return addComposant(composant);
        } catch (ClassCastException | ClassNotFoundException e) {
            DanslChampUtil.erreur("Impossible de charger " + composantType, e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public Composant addComposant(Class<? extends Composant> composantClass, int posX, int posY, int angleRotation) {
        Composant composant = null;
        try {
            // Instancier la classe
            composant = (Composant) composantClass
                    .getDeclaredConstructors()[0]   // SVP qu'un seul constructeur!
                    .newInstance(posX, posY, angleRotation);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            DanslChampUtil.erreur("Impossible de charger " + composantClass.getSimpleName(), e.getMessage());
            e.printStackTrace();
        }

        return addComposant(composant);
    }

    public Composant addComposant(Composant composant) {
        composants.add(composant);
        if (!(composant instanceof Fil)) composantsSansFils.add(composant);
        if (composant instanceof Source) sources.add((Source) composant);

        addJonction(composant);

        for (Diagramme diagramme :
                getDiagrammes()) {
            diagramme.addComposant(composant);
        }

        return composant;
    }

    /**
     * Ajoute les jonction de {@code composant} à la liste des jonctions.
     * Si la jonction existe déjà, le composant est ajouté à la liste.
     * Sinon, la jonction est ajoutée et une nouvelle liste contenant le composant y est associé.
     *
     * @param composant
     * @see Circuit#jonctions
     */
    private void addJonction(Composant composant) {
        for (Jonction jonction :
                composant.getJonctions()) {

            int jonctionIdx = jonctions.indexOf(jonction);

            if (jonctionIdx == -1) {
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

    public ObservableList<Composant> getComposantsSansFils() {
        return composantsSansFils;
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
    public ObservableList<Jonction> getJonctions() {
        return jonctions;
    }

    public List<Composant> getCircuit() {
        return circuit;
    }
}
