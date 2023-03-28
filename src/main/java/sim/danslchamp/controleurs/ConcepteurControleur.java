package sim.danslchamp.controleurs;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import org.reflections.Reflections;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;
import sim.danslchamp.circuit.Fil;
import sim.danslchamp.circuit.Source;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class ConcepteurControleur {

    private static final int TAILLE_QUADRILLAGE_px = 25;

    private int posX = 0, posY = 0;

    private Circuit circuit;

    @FXML
    private AnchorPane conceptionAnchorPane;

    @FXML
    private ToolBar toolbar;
    private Line currentLine = new Line();

    private boolean vertical;

    @FXML
    void initialize() {
        Reflections reflections = new Reflections("sim.danslchamp.circuit");

//        Résistor résistor = new Résistor(10, 10, false, "10");
//        Composant.getSymbole2D(Résistor.class);

        Set<Class<? extends Composant>> composantsClasses = reflections.getSubTypesOf(Composant.class);
        composantsClasses.remove(Fil.class);
        composantsClasses.remove(Source.class);
//        composantsClasses.remove(SousCircuit.class)
//        Set<Class<? extends Composant>> composantsClasses = Set.of(Bobine.class);
        for (Class<? extends Composant> composantClass :
                composantsClasses) {
            try {
                Method method = Composant.class.getMethod("getSymbole2D", String.class);
                Group group = (Group) method.invoke(Composant.class, composantClass.getSimpleName());
                Button button = new Button("", group);
                    button.setTooltip(new Tooltip(composantClass.getSimpleName()));
                button.setOnAction(event -> {
                    circuit.addComposant(composantClass, posX, posY, vertical);
                });
                toolbar.getItems().add(button);
            } catch (ClassCastException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println(e);        // NE DEVRAIT PAS ARRIVER
            }
        }
    }

    @FXML
    void mouseClicked(MouseEvent event) {
        currentLine = new Line(posX, posY,
                Math.round((int) event.getX() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px),
                Math.round((int) event.getY() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));

        currentLine.setOnMouseEntered(e -> {
                    Line line = (Line) e.getTarget();
                    System.out.println(line + " mouse Entered");
                    line.setStrokeWidth(13);
                    line.setStyle("-fx-border-width: 13; -fx-border-color: blue; -fx-border-style: dotted;");
                    line.setOnMouseExited(ev -> {
                        line.setStrokeWidth(1);
                        line.setStyle("");
                    });
                }
        );
        conceptionAnchorPane.getChildren().add(currentLine);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        currentLine.setEndX(Math.round((int) event.getX() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));
        currentLine.setEndY(Math.round((int) event.getY() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        posX = (int) currentLine.getEndX();
        posY = (int) currentLine.getEndY();
    }
}
