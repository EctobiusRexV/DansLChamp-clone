package sim.danslchamp.circuit;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sim.danslchamp.circuit.Bobine;
import sim.danslchamp.circuit.Composant;
import sim.danslchamp.Util.ComposantesListCell;

import java.util.List;

public class TestListeDesComposantes extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // fixme instancier toutes les classes enfant de Composante
//        Composante.class

        ListView<Composant> composanteListView = new ListView<Composant>(FXCollections.observableList(List.of(new Bobine(4, 4, false))));

        composanteListView.setCellFactory(value -> new ComposantesListCell());

        primaryStage.setScene(new Scene(composanteListView));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
