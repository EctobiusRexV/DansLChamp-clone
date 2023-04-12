package sim.danslchamp.controleurs;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import org.reflections.Reflections;
import sim.danslchamp.Config;
import sim.danslchamp.circuit.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
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

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @FXML
    void mousePressed(MouseEvent event) {
        currentLine = new Line(posX, posY,
                Math.round((int) event.getX() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px),
                Math.round((int) event.getY() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));

        currentLine.setStrokeWidth(Config.defautComposantStrokeWidth);

        ajouterComportementDuComposant();

        conceptionAnchorPane.getChildren().add(currentLine);
    }

    private void ajouterComportementDuComposant() {
        currentLine.setOnMouseEntered(e -> {
                    Line line = (Line) e.getTarget();

                    line.setStrokeWidth(10);
                    line.setStyle("-fx-stroke: blue");
                    line.setOnMouseExited(ev -> {
                        // Revenir à la normale
                        line.setStrokeWidth(Config.defautComposantStrokeWidth);
                        line.setStyle("");
                    });
                }
        );
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        currentLine.setEndX(Math.round((int) event.getX() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));
        currentLine.setEndY(Math.round((int) event.getY() / TAILLE_QUADRILLAGE_px * TAILLE_QUADRILLAGE_px));
    }

    @FXML
    void mouseReleased() {
        posX = (int) currentLine.getEndX();
        posY = (int) currentLine.getEndY();

        circuit.addComposant(new Fil((int) currentLine.getStartX(), (int) currentLine.getStartY(), posX, posY));

        conceptionAnchorPane.getChildren().add(new ListPoint2D(circuit.getJonctions()).getGroupe());
    }

    public class ListPoint2D {

        private List<Jonction> jonctionList;


        public ListPoint2D(List<Jonction> jonctionList) {
            this.jonctionList = jonctionList;
        }

        public Group getGroupe() {
            Group groupe = new Group();
            for (Jonction jonction : jonctionList) {
                Sphere sp = new Sphere(5);
                sp.setMaterial(new PhongMaterial(Color.RED));

                int x = (int) jonction.getPositionXY().getX();
                int y = (int) jonction.getPositionXY().getY();
                sp.setLayoutX(x);
                sp.setLayoutY(y);

                sp.setOnMousePressed(event -> setPos(x, y));

                groupe.getChildren().add(sp);
            }

            return groupe;
        }
    }

}
