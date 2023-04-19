package sim.danslchamp.circuit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.reflections.Reflections;
import sim.danslchamp.Util.ComposantsListCell;

import java.util.List;

public class TestListeDesComposants extends Application {

    private static final int MOCK_POS = 0;
    private static final boolean MOCK_ROTATION90 = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        ListView<Composant> composantsListView = new ListView<>();
        composantsListView.setCellFactory(value -> new ComposantsListCell(new Circuit()));

        List<Class> excludes = List.of(Fil.class, SousCircuit.class, Source.class);
        for (Class<? extends Composant> composantClass :
                new Reflections("sim.danslchamp.circuit")
                        .getSubTypesOf(Composant.class)) {
            if (excludes.contains(composantClass)) continue;
            composantsListView.getItems().add(
                    (Composant) composantClass.getConstructors()[0].newInstance(MOCK_POS, MOCK_POS, MOCK_ROTATION90));
        }

        primaryStage.setScene(new Scene(composantsListView));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
