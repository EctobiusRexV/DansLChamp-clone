package sim.danslchamp.circuit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.transform.Rotate;

import java.lang.reflect.InvocationTargetException;

public abstract class Diagramme {
    private Circuit circuit;
    private Group group;

    /**
     * Ajoute un composant au diagramme. Construit le composant avec ses paramètres par défaut.
     *
     * @param composant  La classe du composant.
     * @param posX
     * @param posY
     * @param rotation90
     * @throws ClassNotFoundException Le classe du composant ne correspond pas à une classe Java du package {@code circuit}.
     */
    public abstract void addComposant(Class<Composant> composant, int posX, int posY, boolean rotation90);

    /*abstract void afficherSensDuCourant();

    abstract void afficherNoeuds();

    abstract void afficherGrille();

    abstract void afficherCharges(boolean tousLesComposants);

    abstract void afficherLoiDesNoeuds();

    abstract void afficherMailles();

    abstract void afficherBranches();

    abstract void afficherEtiquettesComposants(boolean avecValeurs);

    abstract void afficherChampElectrique();

    abstract void afficherChampMagnetique();

    abstract void mesurerChampElectrique();

    abstract void mesurerChampMagnetique();*/

    public Group getGroup() {
        return group;
    }

    public class Diagramme2D extends Diagramme {
        @Override
        public void addComposant(Class<Composant> composantClass, int posX, int posY, boolean rotation90) {
            Composant composant;
            try {
                // Instancier la classe
                composant = (Composant) composantClass.getDeclaredConstructors()[0]   // SVP qu'un seul constructeur!
                        .newInstance(posX, posY, rotation90);

                /*if (composant instanceof Source) circuit.getSources().add((Source) composant);

                addJonction(composant);
                circuit.getComposants().add(composant);

                // Add group
                loader.handle(gEl);*/


            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Impossible de charger " + composantClass);
                alert.getDialogPane().setExpandableContent(new Label(e.getMessage()));
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }

    public class Diagramme3D extends Diagramme {

        private final DoubleProperty angleX = new SimpleDoubleProperty(0), angleY = new SimpleDoubleProperty(0);
        private double anchorX, anchorY, anchorAngleX = 0, anchorAngleY = 0;

        private void initMouseControl(Group group, SubScene scene) {
            Rotate xRotate;
            Rotate yRotate;
            group.getTransforms().addAll(xRotate = new Rotate(0, Rotate.X_AXIS), yRotate = new Rotate(0, Rotate.Y_AXIS));
            xRotate.angleProperty().bind(angleX);
            yRotate.angleProperty().bind(angleY);

            scene.setOnMousePressed(event -> {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                anchorAngleX = angleX.get();
                anchorAngleY = angleY.get();
            });

            scene.setOnMouseDragged(event -> {
                angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
                angleY.set(anchorAngleY + anchorX - event.getSceneX());
            });
        }

        @Override
        public void addComposant(Class<Composant> composant, int posX, int posY, boolean rotation90) {
            composant.setPosXY(posX,posY);
            this.getGroup().getChildren().add(composant.getSymbole3D(rotation90));
        }
    }
}
