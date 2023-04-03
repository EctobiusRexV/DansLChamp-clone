package sim.danslchamp.circuit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Diagramme {
    private Circuit circuit;
    private final Group group = new Group();

    /**
     * Ajoute un composant au diagramme. Construit le composant avec ses paramètres par défaut.
     *
     * @param composant La classe du composant.
     * @throws ClassNotFoundException Le classe du composant ne correspond pas à une classe Java du package {@code circuit}.
     */
    public final void addComposant(Composant composant) {
        genererInfobulle(composant, addComposant_internal(composant));
    }

    abstract Group addComposant_internal(Composant composant);


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
    private void genererInfobulle(Composant composant, Group composantGroup) {
        Tooltip tooltip = new Tooltip();
        for (Composant.Valeur valeur :
                composant.getValeursAffichables()) {
                tooltip.setText(tooltip.getText().concat(valeur + "\n"));
        }

        composantGroup.setOnMousePressed(event -> {
                tooltip.show(composantGroup, event.getScreenX(), event.getScreenY());
        });
        composantGroup.setOnMouseReleased(event -> {
            tooltip.hide();
        });
    }

    public Group getGroup() {
        return group;
    }

    public static class Diagramme2D extends Diagramme {

        public Diagramme2D() {
            // Zoom/dézoom
            getGroup().addEventHandler(ScrollEvent.SCROLL, event -> {
                if (getGroup().getScaleX() + event.getDeltaY() / 100 < 0) return; // empêcher d'obtenir un scale négatif

                getGroup().scaleXProperty().set(getGroup().getScaleX() + event.getDeltaY() / 100);
                getGroup().scaleYProperty().set(getGroup().getScaleY() + event.getDeltaY() / 100);

            });
        }

        @Override
        Group addComposant_internal(Composant composant) {
            Group symbole = composant.getSymbole2D();
            symbole.setTranslateX(composant.getPosX());
            symbole.setTranslateY(composant.getPosY());

            getGroup().getChildren().add(symbole);
            return symbole;
        }
    }

    public static class Diagramme3D extends Diagramme {

        private final DoubleProperty angleX = new SimpleDoubleProperty(0), angleY = new SimpleDoubleProperty(0);
        private double anchorX, anchorY, anchorAngleX = 0, anchorAngleY = 0;

        public void initMouseControl(Group group, SubScene scene) {
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
        Group addComposant_internal(Composant composant) {
            Group symbole = composant.getSymbole3D();
            symbole.setTranslateX(composant.getPosX());
            symbole.setTranslateY(composant.getPosY());

            getGroup().getChildren().add(symbole);
            return symbole;
        }
    }
}
