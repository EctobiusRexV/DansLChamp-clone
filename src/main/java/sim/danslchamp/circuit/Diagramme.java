package sim.danslchamp.circuit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;

public abstract class Diagramme {
    private Circuit circuit;
    private final Group group = new Group();

    public static void surligner(Composant old, Composant composant) {
        if (composant != null) {
            composant.getSymbole2D().getChildren().forEach(node -> {
                if (node instanceof Group) {
                    ((Group) node).getChildren().forEach(subnodes -> subnodes.setStyle("-fx-stroke: blue"));
                }
            });
        }
        if (old != null) {
            old.getSymbole2D().getChildren().forEach(node -> {
                if (node instanceof Group) {
                    ((Group) node).getChildren().forEach(subnodes -> subnodes.setStyle("-fx-stroke: black"));    // FIXME: 2023-04-25 old.getStrokeColor()
                }
            });
        }
    }

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


    abstract void afficherChampMagnetique();

    /*abstract void afficherSensDuCourant();

    abstract void afficherNoeuds();

    abstract void afficherGrille();

    abstract void afficherCharges(boolean tousLesComposants);

    abstract void afficherLoiDesNoeuds();

    abstract void afficherMailles();

    abstract void afficherBranches();

    abstract void afficherEtiquettesComposants(boolean avecValeurs);

    abstract void afficherChampElectrique();


    abstract void mesurerChampElectrique();

    abstract void mesurerChampMagnetique();*/
    private void genererInfobulle(Composant composant, Group composantGroup) {
        Tooltip infobulle = new Tooltip();
        Label valeursLabel = new Label();
        VBox infobulleVBox = new VBox(
                new Label(composant.toString()),
                new Separator(Orientation.HORIZONTAL),
                valeursLabel);

        infobulle.setGraphic(infobulleVBox);

        composantGroup.setOnMousePressed(event -> {
            valeursLabel.setText("");     // Clear

            for (Composant.ValeurNomWrapper valeurNomWrapper :
                    composant.getValeursAffichables()) {
                valeursLabel.setText(valeursLabel.getText().concat(valeurNomWrapper.nom + ": " + valeurNomWrapper.valeur + "\n"));
            }

            infobulle.show(composantGroup, event.getScreenX(), event.getScreenY());
            event.consume();
        });
        composantGroup.setOnMouseReleased(event -> {
            infobulle.hide();
        });

    }

    public Group getGroup() {
        return group;
    }

    public static void rotationner(Group group, Number v) {
        group.setRotate(v.doubleValue());

        group.setTranslateX(group.getTranslateX() + -group.getLayoutBounds().getWidth() * (1-Math.cos(Math.toRadians(v.doubleValue())))/2);
        group.setTranslateY(group.getTranslateY() + group.getLayoutBounds().getWidth() * Math.sin(Math.toRadians(v.doubleValue()))/2);
    }

    public static class Diagramme2D extends Diagramme {

        public Diagramme2D() {
        }

        @Override
        Group addComposant_internal(Composant composant) {
            Group symbole = composant.getSymbole2D();
            symbole.setTranslateX(composant.getPosX());
            symbole.setTranslateY(composant.getPosY());
            getGroup().getChildren().add(composant.getChamp());
            getGroup().getChildren().add(symbole);

            rotationner(symbole, composant.getAngleRotation());
            return symbole;
        }

        @Override
        void afficherChampMagnetique() {

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
            getGroup().getChildren().add(composant.getChamp());

            rotationner(symbole, composant.getAngleRotation());
            return symbole;
        }

        @Override
        void afficherChampMagnetique() {

        }
    }
}
