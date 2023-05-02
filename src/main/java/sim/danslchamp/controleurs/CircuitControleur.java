package sim.danslchamp.controleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sim.danslchamp.Config;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.Util.ComposantsListCell;
import sim.danslchamp.Util.DanslChampUtil;
import sim.danslchamp.circuit.Bobine;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;
import sim.danslchamp.circuit.Diagramme;
import sim.danslchamp.svg.FXASvg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

import static sim.danslchamp.DansLChampApp.*;

/**
 * Contrôleur de la fenêtre Concepteur de circuit
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 * @author Thierry Rhéaume
 */
public class CircuitControleur extends ParentControleur {

    public Menu ouvrirRecentsMenu;
    private Circuit circuit = new Circuit();


    Tooltip infobulleC = new Tooltip();
    private File fichierEnregistrement;

    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);

        stage.setMaximized(Config.circuitMaximise);
        if (!Config.circuitMaximise) {
            stage.setX(Config.circuitPosX);
            stage.setY(Config.circuitPosY);
            stage.setWidth(Config.circuitLargeur);
            stage.setHeight(Config.circuitHauteur);
        }


        stage.setOnHidden(event -> {
            Config.circuitDiagrammesSplitPanePosition0 = diagrammesSplitPane.getDividerPositions()[0];
            if (diagrammesSplitPane.getDividers().size() > 1)
                Config.circuitDiagrammesSplitPanePosition1 = diagrammesSplitPane.getDividerPositions()[1];
            Config.circuitMaximise = stage.isMaximized();
            if (!stage.isMaximized()) {
                Config.circuitPosX = stage.getX();
                Config.circuitPosY = stage.getY();
                Config.circuitLargeur = stage.getWidth();
                Config.circuitHauteur = stage.getHeight();
            }

            Config.circuitAfficherDiagramme2D = diagramme2DCheckMenuItem.isSelected();
            Config.circuitAfficherDiagramme3D = diagramme3DCheckMenuItem.isSelected();
            Config.circuitAfficherListeDesComposants = listeDesComposantsCheckMenuItem.isSelected();

            Config.sauvegarder();
        });
    }

    // FXML
    @FXML
    private ListView<Composant> composantsListView;
    @FXML
    private VBox vBox2D, vBox3D;
    @FXML
    private SubScene subSceneConcepteur,
            subScene3D;

    // FXML fields for each CheckMenuItem in Circuit.fxml
    @FXML
    private CheckMenuItem diagramme2DCheckMenuItem, diagramme3DCheckMenuItem, listeDesComposantsCheckMenuItem;

    @FXML
    private SplitPane diagrammesSplitPane;

    @FXML
    private TabPane listeDesComposantsTabPane;


    // ===============================
    //             INIT
    // ===============================
    @FXML
    public void initialize() {
        ouvrirRecentsMenu.getItems().addAll(
                getOuvrirRecentsMenuItem(Config.circuitRecent1),
                getOuvrirRecentsMenuItem(Config.circuitRecent2),
                getOuvrirRecentsMenuItem(Config.circuitRecent3)
        );

        affichageCheckBoxMenuItem(diagramme2DCheckMenuItem, vBox2D);
        affichageCheckBoxMenuItem(diagramme3DCheckMenuItem, vBox3D);
        affichageCheckBoxMenuItem(listeDesComposantsCheckMenuItem, listeDesComposantsTabPane);

        diagrammesSplitPane.getItems().clear();
        diagramme2DCheckMenuItem.setSelected(Config.circuitAfficherDiagramme2D);
        diagramme3DCheckMenuItem.setSelected(Config.circuitAfficherDiagramme3D);
        listeDesComposantsCheckMenuItem.setSelected(Config.circuitAfficherListeDesComposants);


        diagrammesSplitPane.setDividerPosition(0, Config.circuitDiagrammesSplitPanePosition0);
        diagrammesSplitPane.setDividerPosition(1, Config.circuitDiagrammesSplitPanePosition1);


        composantsListView.setCellFactory(item ->
                new ComposantsListCell(circuit));

        vBox2D.addEventHandler(ScrollEvent.SCROLL, event -> {
            if (vBox2D.getScaleX() + event.getDeltaY() / 100 < 0)
                return; // empêcher d'obtenir un scale négatif

            vBox2D.scaleXProperty().set(vBox2D.getScaleX() + event.getDeltaY() / 1000);
            vBox2D.scaleYProperty().set(vBox2D.getScaleY() + event.getDeltaY() / 1000);

        });


        vBox2D.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            Bobine bob = null;
            Bobine bob2 = null;
            int cpt = 0;
            for (int i = 0; i < circuit.getComposantsSansFils().size(); i++) {
                if (circuit.getComposantsSansFils().get(i).getClass() == Bobine.class) {
                    cpt = cpt + 1;
                    if (cpt > 1) {
                        bob2 = (Bobine) circuit.getComposantsSansFils().get(i);
                        break;
                    }
                    bob = (Bobine) circuit.getComposantsSansFils().get(i);
                    break;
                }
            }

            if (bob != null) {

                javafx.scene.control.Label valeursLabel = new javafx.scene.control.Label();
                VBox infobulleVBox = new VBox(
                        new Label("champ magnétique"),
                        new Separator(Orientation.HORIZONTAL),
                        valeursLabel);
                //TODO faire les distances correct
                infobulleC.setGraphic(infobulleVBox);
                double x = bob.getPosX() + circuit.getDiagramme2D().getGroup().getLayoutX();
                double y = bob.getPosY() + circuit.getDiagramme2D().getGroup().getLayoutY();

                double d = Math.hypot((x - event.getX()), (y - event.getY()));
                double B = 4 * Math.PI * (bob.nombreDeSpires.getValeur(Composant.Unite.UNITE) / bob.longueur.getValeur(Composant.Unite.UNITE)) * bob.courant.getValeur(Composant.Unite.UNITE) / 10000000;
                double Bext = (B * Math.pow(bob.rayon.getValeur(Composant.Unite.UNITE), 2)) / (2 * Math.pow(Math.pow(bob.rayon.getValeur(Composant.Unite.UNITE), 2) + Math.pow(d/100, 2), (3 / 2)));
                Composant.Valeur dist = new Composant.Valeur(d, Composant.Unite.UNITE, "cm");
                Composant.Valeur valTesla = new Composant.Valeur(Bext, Composant.Unite.UNITE, "T");

                if (cpt > 1){
                    System.out.println("il y a 2 bob");
                    double x2 = bob.getPosX() + circuit.getDiagramme2D().getGroup().getLayoutX();
                    double y2 = bob.getPosY() + circuit.getDiagramme2D().getGroup().getLayoutY();
                    ;
                    double d2 = Math.hypot((x2 - event.getX()), (y2 - event.getY()));
                    double B2 = 4 * Math.PI * (bob2.nombreDeSpires.getValeur(Composant.Unite.UNITE) / bob2.longueur.getValeur(Composant.Unite.UNITE)) * bob2.courant.getValeur(Composant.Unite.UNITE) / 10000000;
                    double Bext2 = (B2 * Math.pow(bob2.rayon.getValeur(Composant.Unite.UNITE), 2)) / (2 * Math.pow(Math.pow(bob2.rayon.getValeur(Composant.Unite.UNITE), 2) + Math.pow(d2, 2), (3 / 2)));
                     dist = new Composant.Valeur(d + d2, Composant.Unite.UNITE, "m");
                     valTesla = new Composant.Valeur(Bext + Bext2, Composant.Unite.UNITE, "T");

                }
                valeursLabel.setText("La force du champ magnétique à " + dist + " de la bobine est de: " + "\n" + valTesla);     // Clear


                infobulleC.show(vBox2D, event.getScreenX(), event.getScreenY());
            }

        });

        vBox2D.addEventHandler(MouseEvent.MOUSE_RELEASED, event ->

        {
            infobulleC.hide();

        });

        composantsListView.getSelectionModel().

                selectedItemProperty().

                addListener((l, old, composant) ->

                {
                    Diagramme.surligner(old, composant);
                });

        subScene3D.heightProperty().

                bind(vBox3D.heightProperty());
        subScene3D.widthProperty().

                bind(vBox3D.widthProperty());
    }

    private void affichageCheckBoxMenuItem(CheckMenuItem checkMenuItem, Node node) {
        checkMenuItem.selectedProperty().addListener((l, old, selected) -> {
            if (selected)
                diagrammesSplitPane.getItems().add(node);
            else
                diagrammesSplitPane.getItems().remove(node);
        });
    }

    @NotNull
    private MenuItem getOuvrirRecentsMenuItem(String text) {
        if (text.isBlank()) return new MenuItem();

        MenuItem menuItem = new MenuItem(text);

        menuItem.setOnAction(event -> {
            try {
                chargerCircuit(new File(text));
            } catch (FileNotFoundException e) {
                DanslChampUtil.erreur("Impossible d'ouvrir le circuit", e.getMessage());
            }
        });

        return menuItem;
    }

    // ===============================
    //         ACTIONS MENU
    // ===============================

    /**
     * Charge un circuit depuis SVG.
     *
     * @param file Le fichier à charger.
     */
    public void chargerCircuit(@Nullable File file) throws FileNotFoundException {
        circuit = Circuit.chargerCircuit(file);
        pousserCircuitRecent(file);
        fichierEnregistrement = file;
        composantsListView.setItems(circuit.getComposantsSansFils());

        vBox2D.getChildren().setAll(circuit.getDiagramme2D().getGroup());


        init3D();
    }

    private void init3D() {
        Group group3D = circuit.getDiagramme3D().getGroup();
        subScene3D.addEventHandler(ScrollEvent.SCROLL, event -> {
            group3D.translateZProperty().set(group3D.getTranslateZ() - event.getDeltaY());
        });


        Camera camera = new PerspectiveCamera();
        subScene3D.setFill(Color.WHITE);
        subScene3D.setCamera(camera);

        VBox vBox = new VBox(group3D);
        vBox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.CENTER);
        subScene3D.setRoot(hBox);
        circuit.getDiagramme3D().initMouseControl(group3D, subScene3D);
    }

    private static void pousserCircuitRecent(File file) {
        String circuitActuel = file.getAbsolutePath();

        if (!(Config.circuitRecent2.equals(circuitActuel) || Config.circuitRecent1.equals(circuitActuel)))
            Config.circuitRecent3 = Config.circuitRecent2;
        if (!Config.circuitRecent1.equals(circuitActuel))
            Config.circuitRecent2 = Config.circuitRecent1;

        Config.circuitRecent1 = circuitActuel;
    }


    public static void nouveau() {
        ControllerUtil.loadFenetre("Circuit.fxml", 500, 600);
    }

    public void nouveau(ActionEvent actionEvent) {
        nouveau();
    }

    public void ouvrirCircuit(ActionEvent actionEvent) {
        try {
            File file = FC.showOpenDialog(null);
            if (file != null)
                chargerCircuit(file);  // Ne pas ouvrir si aucune sélection n'est faite!
        } catch (FileNotFoundException neSappliquePas) {
        }
    }

    public void enregistrer() {
        if (fichierEnregistrement == null) enregistrerSous();
        else {
            try {
                Files.write(fichierEnregistrement.toPath(), Collections.singleton(FXASvg.aSvg(circuit)), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                DanslChampUtil.erreur("Impossible d'enregistrer le fichier", e.getMessage());
            }
        }
    }

    public void enregistrerSous() {
        fichierEnregistrement = FC.showSaveDialog(null);
        if (fichierEnregistrement != null) {
            if (FC.getSelectedExtensionFilter() == EXTENSION_FILTER && !fichierEnregistrement.getPath().matches("[" + FILE_EXTENSION + "]^")) {
                fichierEnregistrement = new File(fichierEnregistrement.getPath() + FILE_EXTENSION);
            }

            enregistrer();
        }
    }

    public void ouvrirConcepteur(ActionEvent actionEvent) {
        ConcepteurControleur.nouveau(circuit);
    }
}
