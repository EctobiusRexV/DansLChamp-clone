package sim.danslchamp.circuit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Circuit {

    private String nom;

    private final List<Maille> mailles;
    private final Set<Jonction> noeuds;

    private final ObservableList<Composant> composants;

    public Group getGroupe2D() {
        return groupe2D;
    }

    public Group getGroupe3D() {
        return groupe3D;
    }

    private Group groupe2D, groupe3D;


    public Circuit() {
        this(new ArrayList<>(), new HashSet<>(), new ArrayList<>());
    }

    private Circuit(List<Maille> mailles, Set<Jonction> noeuds, List<Composant> composants) {
        this.mailles = mailles;
        this.noeuds = noeuds;
        this.composants = FXCollections.observableArrayList(composants);
    }

    public static Circuit chargerCircuit(File file) throws FileNotFoundException {
        SvgLoader loader = new SvgLoader();
        return new Circuit();
    }

    /**
     * Trouve le sens du courant.
     * En partant des bornes positives des sources, définit les bornes positives et négatives des composantes.
     */
    private void trouverSensDuCourant() {
    }

    private void trouverMailles() {
    }

    /*private void traverser(Composant composant) {
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
    }*/

    /*void setCircuit3D() {
        for (Composant composant : composantes) {
            groupe3D.getChildren().add(composant.getGroupe3D());
        }
    }*/

    public ObservableList<Composant> getComposants() {
        return composants;
    }
}
