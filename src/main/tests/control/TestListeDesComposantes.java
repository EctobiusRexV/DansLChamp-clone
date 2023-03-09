package control;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sim.danslchamp.Bobine;
import sim.danslchamp.Composante;
import sim.danslchamp.Util.ComposantesListCell;

import java.util.List;

public class TestListeDesComposantes extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // fixme instancier toutes les classes enfant de Composante
//        Composante.class

        ListView<Composante> composanteListView = new ListView<Composante>(FXCollections.observableList(List.of(new Bobine("4", "4", "4"))));

        composanteListView.setCellFactory(value -> new ComposantesListCell());

        primaryStage.setScene(new Scene(composanteListView));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
