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

    private Diagramme.Diagramme2D diagramme2D;

    private Diagramme.Diagramme3D diagramme3D;


    // fixme
    public Circuit() {
        mailles = null;
        noeuds = null;
        composants = null;
    }

    private Circuit(File file) throws FileNotFoundException {
        SvgLoader loader = new SvgLoader(this);

        loader.loadSvg(new FileInputStream(file));

        List<Jonction> jonctions = loader.getSvgElementHandler().getJonctions();
        List<Source> sources = loader.getSvgElementHandler().getSources();

        noeuds = jonctions.stream().filter(Jonction::estNoeud).toList();
        composants = FXCollections.observableArrayList(
                loader.getSvgElementHandler().getComposants()
        );

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
}
