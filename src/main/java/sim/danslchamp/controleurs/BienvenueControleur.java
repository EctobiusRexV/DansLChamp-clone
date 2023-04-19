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
    private MFXTitledPane recentsTitlePane;

    @FXML
    private MFXTitledPane deBaseTitledPane;


    // ===============================
    //             INIT
    // ===============================
    @FXML
    public void initialize() {
        FlowPane recentsFlowPane = new FlowPane();
        recentsFlowPane.getChildren().addAll(
                getCircuitVBox(Path.of(Config.circuitRecent1)),
                getCircuitVBox(Path.of(Config.circuitRecent2)),
                getCircuitVBox(Path.of(Config.circuitRecent3))
        );
        FlowPane deBaseFlowPane = new FlowPane();
        try {
            Files.list(Path.of("circuits")).forEach(circuit ->
            deBaseFlowPane.getChildren().add(
                    getCircuitVBox(circuit)
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        recentsTitlePane.setContent(recentsFlowPane);
        deBaseTitledPane.getStylesheets().add(getClass().getResource("titlepane.css").toExternalForm());
        deBaseTitledPane.setContent(deBaseFlowPane);
        recentsTitlePane.getStylesheets().add(getClass().getResource("titlepane.css").toExternalForm());

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
vBox.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.RED, CornerRadii.EMPTY, new Insets(10))));
vBox.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        vBox.setUserData(path.toFile());
        vBox.setOnMouseClicked(this::circuitPressed);
        return vBox;
    }

    // ===============================
    //         ACTIONS UI
    // ===============================
    @FXML
    void circuitPressed(MouseEvent event) {
        try {
            DansLChampApp.showConcepteurDeCircuit((File) ((VBox) event.getSource()).getUserData());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage()); // Au moins on va connaître le bug.
        }
    }


    // ===============================
    //         ACTIONS MENU
    // ===============================
   @FXML
    void fermerApp()  {
        Platform.exit();
    }


}
