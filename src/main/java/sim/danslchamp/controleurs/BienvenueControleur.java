package sim.danslchamp.controleurs;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXTitledPane;
import sim.danslchamp.Config;
import sim.danslchamp.DansLChampApp;
import sim.danslchamp.svg.SvgLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static sim.danslchamp.DansLChampApp.FC;
import static sim.danslchamp.DansLChampApp.SVG_LOADER;

/**
 * Contrôleur de la fenêtre de Démarrage
 *
 * @author Antoine Bélisle
 * @author Mathis Rosa-Wilson
 */
public class BienvenueControleur extends ParentControleur {
    public BorderPane titleBar;

    // FXML

    @FXML
    private FlowPane recentsFlowPane, deBaseFlowPane;

    private Color[] colors = new Color[]{Color.WHITESMOKE, Color.PAPAYAWHIP, Color.GHOSTWHITE, Color.NAVAJOWHITE, Color.ANTIQUEWHITE, Color.FLORALWHITE};

    private int counter;



    // ===============================
    //             INIT
    // ===============================
    @FXML
    public void initialize() {
        counter = (int) (Math.random() * colors.length);

        recentsFlowPane.getChildren().addAll(
                getCircuitVBox(Path.of(Config.circuitRecent1)),
                getCircuitVBox(Path.of(Config.circuitRecent2)),
                getCircuitVBox(Path.of(Config.circuitRecent3))
        );
        try {
            Files.list(Path.of("circuits")).forEach(circuit ->
            deBaseFlowPane.getChildren().add(
                    getCircuitVBox(circuit)
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VBox getCircuitVBox(Path path) {
        if (!path.toFile().exists()) return new VBox();
        SvgLoader svgLoader = new SvgLoader(null);
        Label label = new Label(path.getFileName().toString());
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("System", FontWeight.BOLD, 12));
        VBox vBox = new VBox(
                svgLoader.loadSvg(path.toString()),
                label
        );
Group group = new Group();
group.setAutoSizeChildren(true);
        vBox.setPrefWidth(200);
        vBox.setMaxWidth(200);
        vBox.setFillWidth(true);
        vBox.setPadding(new Insets(15));
        vBox.setAlignment(Pos.CENTER);
vBox.setBackground(new Background(new BackgroundFill(colors[counter % colors.length], new CornerRadii(10), new Insets(10))));
vBox.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(1))));
        vBox.setUserData(path.toFile());
        vBox.setOnMouseClicked(this::circuitPressed);

        counter++;
        return vBox;
    }

    // ===============================
    //         ACTIONS UI
    // ===============================
    @FXML
    void circuitPressed(MouseEvent event) {
        try {
            DansLChampApp.showConcepteurDeCircuit((File) ((VBox) event.getSource()).getUserData());
            stage.hide();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage()); // Au moins on va connaître le bug.
        }
    }

    public void nouveauCircuit(ActionEvent actionEvent) {
        CircuitControleur.nouveau();
    }

    // ===============================
    //         ACTIONS MENU
    // ===============================
   @FXML
    void fermerApp()  {
        Platform.exit();
    }

}
