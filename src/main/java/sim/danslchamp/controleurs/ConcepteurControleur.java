package sim.danslchamp.controleurs;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
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
                Method method = Composant.class.getMethod("getSymbole2D", Class.class);
                Group group = (Group) method.invoke(Composant.class, composantClass);
                Button button = new Button("", group);
                button.setOnAction(event -> {
                    circuit.addComposant(composantClass, posX, posY, vertical);
                });
                toolbar.getItems().add();
            } catch (ClassCastException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println(e);        // NE DEVRAIT PAS ARRIVER
            }
        }
    }

    @FXML
    void mouseClicked(MouseEvent event) {
        currentLine = new Line(posX, posY, event.getX(), event.getY());

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
        currentLine.setEndX(event.getX());
        currentLine.setEndY(event.getY());
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        posX = (int) event.getX();
        posY = (int) event.getY();
    }
}
