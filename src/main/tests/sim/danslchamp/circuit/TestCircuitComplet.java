package sim.danslchamp.circuit;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import testUtil.ListPoint2D;

import java.io.File;
import java.util.List;

import static sim.danslchamp.DansLChampApp.FC;

public class TestCircuitComplet extends Application {

    Group noeuds;
    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = null;
        while (file == null)
            file = FC.showOpenDialog(primaryStage);

        Circuit circuit = Circuit.chargerCircuit(file);

        testJonctions(circuit);

        testNoeuds(circuit);

        testSensDuCourant(circuit);

        Pane pane = new Pane(circuit.getDiagramme2D().getGroup(), noeuds);
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void testJonctions(Circuit circuit) {
        List<Jonction> jonctions = circuit.getJonctions();

        jonctions.forEach(jonction -> {
            if (jonction.getComposants().size()==1) System.out.println(jonction.getComposants().get(0) + " n'est pas connecté ! "+jonction.getComposants());
        });

        System.out.println();
    }

    private void testNoeuds(Circuit circuit) {
        List<Jonction> jonctions = circuit.getJonctions();

        // DEBUG
        noeuds = new ListPoint2D(jonctions.stream().filter(Jonction::estNoeud).toList()).getGroupe();
    }

    private void testSensDuCourant(Circuit circuit) {
        circuit.getComposants().forEach(composant -> {
            for (Jonction jonction : composant.getJonctions()) {
                if (composant.getBornePositive() == null) System.out.println(composant + " n'a pas de borne positive");
                else circuit.getDiagramme2D().getGroup().getChildren().add(
                        new Text(jonction.getPositionXY().x, jonction.getPositionXY().y + 10,
                                jonction == composant.getBornePositive()? "+" : "-"));
            }
        });
    }
}
