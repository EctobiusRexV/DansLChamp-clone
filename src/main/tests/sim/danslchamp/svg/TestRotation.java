package sim.danslchamp.svg;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sim.danslchamp.circuit.Résistor;

public class TestRotation extends Application {

    private static final double POS_POINT = 250d;

    private double pointY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle point = new Circle(0, 0, 3);
        Résistor résistor = new Résistor(0, 0, 0);

        AnchorPane.setLeftAnchor(point, POS_POINT);
        AnchorPane.setTopAnchor(point, POS_POINT+résistor.getJonctions()[0].getPositionXY().y);

        Group group = résistor.getSymbole2D();
        AnchorPane.setLeftAnchor(group, POS_POINT);
        AnchorPane.setTopAnchor(group, POS_POINT);

//        group.setRotationAxis(new Point3D(250.0, 250d, 250d));

        AnchorPane anchorPane = new AnchorPane(point, group);

        anchorPane.setOnMousePressed(event -> {
            pointY = event.getY();
        });

        anchorPane.setOnMouseDragged(event -> {
            // Rotationner le résistor selon le déplacement de la souris
            double deltaY = event.getY() - pointY;
            rotationner(résistor, group, deltaY/100);

        });

        primaryStage.setScene(new Scene(anchorPane, POS_POINT*2+résistor.getLargeur(), POS_POINT*2+résistor.getHauteur()));
        primaryStage.show();
    }

    private void rotationner(Résistor résistor, Group group, Number v) {
        group.setRotate(Math.toDegrees(v.doubleValue()));

        group.setTranslateX(-group.getLayoutBounds().getWidth() * (1-Math.cos(v.doubleValue()))/2);
        group.setTranslateY(group.getLayoutBounds().getWidth() * Math.sin(v.doubleValue())/2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
