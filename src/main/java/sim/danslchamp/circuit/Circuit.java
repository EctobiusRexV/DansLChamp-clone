package sim.danslchamp.circuit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Circuit {

    private String nom;

    private final List<Maille> mailles;
    private final List<Jonction> noeuds;

    private final ObservableList<Composant> composants;
    private Group diagramme2D, diagramme3D = new Group();

    public Group getGroupe2D() {
        return diagramme2D;
    }

    public Group getDiagramme3D() {
        return diagramme3D;
    }

    public Circuit() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Group());
    }

    private Circuit(List<Maille> mailles, List<Jonction> noeuds, List<Composant> composants, Group diagramme2D) {
        this.mailles = mailles;
        this.noeuds = noeuds;
        this.composants = FXCollections.observableArrayList(composants);
        this.diagramme2D = diagramme2D;
    }

    public static Circuit chargerCircuit(File file) throws FileNotFoundException {
        SvgLoader loader = new SvgLoader();
        Group diagramme2D = loader.loadSvg(new FileInputStream(file));
        List<Jonction> jonctions = loader.getSvgElementHandler().getJonctions();
        List<Jonction> noeuds = jonctions.stream().filter(Jonction::estNoeud).toList();
        List<Composant> composants = loader.getSvgElementHandler().getComposants();
        List<Source> sources = loader.getSvgElementHandler().getSources();
        trouverSensDuCourant(jonctions, sources);
        return new Circuit(trouverMailles(), noeuds, composants, diagramme2D);
    }

    /**
     * Trouve le sens du courant.
     * En partant des bornes positives des sources, définit les bornes positives et négatives des composantes.
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
