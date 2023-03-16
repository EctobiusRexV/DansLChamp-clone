package sim.danslchamp.circuit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sim.danslchamp.svg.SvgLoader;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Circuit {

    private String nom;

    private final List<Maille> mailles;
    private final Set<Jonction> noeuds;

    private final ObservableList<Composant> composants;


    public Circuit() {
        this(new ArrayList<>(), new HashSet<>(), null);
    }

    private Circuit(List<Maille> mailles, Set<Jonction> noeuds, List<Composant> composants) {
        this.mailles = mailles;
        this.noeuds = noeuds;
        this.composants = FXCollections.observableArrayList(composants);
    }

    public static Circuit chargerCircuit(File file) throws FileNotFoundException {
        SvgLoader loader = new SvgLoader();

        trouverSensDuCourant();
        trouverMailles();
    }

    /**
     * Trouve le sens du courant.
     * En partant des bornes positives des sources, définit les bornes positives et négatives des composantes.
     */
    private void trouverSensDuCourant() {
        ArrayList<? extends Composant> depart = sources;

        while(!depart.isEmpty()) {
            traverser(depart.get(0))
            depart.remove(0);
        }
    }

    private void trouverMailles() {
    }

    private void traverser(Composant composant) {
        Point connecteur = composant.getBornePositive();
        ArrayList<Composant> composants = connecteurs.get(connecteur);
        for (Composant c :
                composants) {
            c.setBornePositive(connecteur);
            for (Point connecteurComposante:
                    c.getConnecteurs()) {
               if (connecteurComposante == connecteur) return; // C'est le même.
                traverser();
            }
        }
    }

    /*void setCircuit3D() {
        for (Composant composant : composantes) {
            groupe3D.getChildren().add(composant.getGroupe3D());
        }
    }*/

    public ObservableList<Composant> getComposants() {
        return composants;
    }
}
