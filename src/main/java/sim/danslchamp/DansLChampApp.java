package sim.danslchamp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.Nullable;
import sim.danslchamp.controleurs.ConcepteurControleur;
import sim.danslchamp.controleurs.BienvenueControleur;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DansLChampApp extends Application {

    // PAS TOUCHE
    // ====================
    public static final FileChooser FC = new FileChooser();
    private static final String FILE_EXTENSION = "*.svg";
    private static final FileChooser.ExtensionFilter EXTENSION_FILTER =
            new FileChooser.ExtensionFilter("Circuit DANS L'CHAMP", FILE_EXTENSION);

    public static final SvgLoader SVG_LOADER = new SvgLoader();
    // ====================

    @Override
    public void init() throws Exception {
        super.init();

        FC.getExtensionFilters().add(EXTENSION_FILTER);
//        FC.setSelectedExtensionFilter(EXTENSION_FILTER);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Scene scene = new Scene(fxmlLoader.load(this.getClass().getResourceAsStream("fxml\\Bienvenue.fxml")));

        BienvenueControleur bienvenueControleur = fxmlLoader.getController();
        bienvenueControleur.setStage(primaryStage);
        primaryStage.setMinHeight(600.0);
        primaryStage.setMinWidth(600.0);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(true);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.show();
    }

    /**
     * Ouvre le concepteur de circuit
     * @param file Le fichier chargé à l'ouverture, ou null.
     */
    public static void showConcepteurDeCircuit(@Nullable File file) throws FileNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load(DansLChampApp.class.getResourceAsStream("fxml\\Concepteur.fxml")));
            stage.setScene(scene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur dans l'ouverture de la fenêtre");
            alert.setContentText("Veuillez réessayer...");
        }

        ConcepteurControleur concepteurControleur = fxmlLoader.getController();
        concepteurControleur.setStage(stage);
        concepteurControleur.chargerCircuit(file);
        stage.show();
        stage.setMaximized(true);
        stage.setMinWidth(800.0);
        stage.setMinHeight(600.0);
    }

    public static Stage loadFenetre(String path){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();

        try {
            Scene scene = new Scene(fxmlLoader.load(DansLChampApp.class.getResourceAsStream("fxml\\"+path)));
            stage.setScene(scene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur dans l'ouverture de la fenêtre");
            alert.setContentText("Veuillez réessayer...");
        }

        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
