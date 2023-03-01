package prototypes;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Rotation3DWithMouse extends Application {

    private SmartGroup group = new SmartGroup();
    private static final int WIDTH = 1400;
    private static final int HEIGHT = 1000;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    @Override
    public void start(Stage primaryStage) {
//        Box box = new Box(100, 50, 20);
//        Box box2 = new Box(100, 50, 20);
//        box2.setLayoutX(200);
//        box2.setLayoutY(200);
        Cylinder cylindre = new Cylinder(25, 75);
//        cylindre.setLayoutX(200);
//        cylindre.setLayoutY(-100);
//        Line ligne2 = new Line(cylindre.getLayoutX(),cylindre.getLayoutY()+cylindre.getRadius(),box2.getLayoutX(),box2.getLayoutY()-box2.getHeight()/2);
//        Line ligne = new Line(box.getLayoutX()+box.getWidth()/2, box.getLayoutY(), cylindre.getLayoutX()-cylindre.getRadius(), cylindre.getLayoutY());
//        ligne.setFill(Color.RED);
//        cylindre.setRotationAxis(new Point3D(1, 0, 0));
//        cylindre.setRotate(90);
//        Cylinder cylindre2 = new Cylinder(15,75);
//        cylindre2.setLayoutY(200);
//        cylindre2.setRotate(90);
//        cylindre2.setMaterial(new PhongMaterial(Color.AQUA));
//        Line ligne3 = new Line(box2.getLayoutX()-box2.getWidth()/2,box2.getLayoutY(),cylindre2.getLayoutX()+cylindre2.getHeight()/2,cylindre2.getLayoutY());
//        Line ligne4 = new Line(cylindre2.getLayoutX()-cylindre2.getHeight()/2,cylindre2.getLayoutY(),box.getLayoutX()-box.getWidth()/2,box.getLayoutY());
//        PhongMaterial a = new PhongMaterial();
//        try {
//            a.setDiffuseMap(new Image(new File("2.png").toURI().toURL().toExternalForm()));
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }



        Circuit c = new Circuit(new Composante2[]{new Source(), new Condensateur(), new Resistor(), new Condensateur(), new Condensateur(), new Condensateur()});

        Rectangle r = new Rectangle(100,100);
        PhongMaterial a = new PhongMaterial();
        try {
            a.setDiffuseMap(new Image(new File("i.png").toURI().toURL().toExternalForm()));

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Image img = null;
        try {
            img = new Image(new File("i.png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        r.setFill(new ImagePattern(img));


       ListPoint list = new ListPoint();
        for (int i = 0; i < 5; i++) {
            list.addPoint(i*30,i*70);
        }

        SmartGroup group = new SmartGroup();
        group.getChildren().add(c.getGroupe());

        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(group, WIDTH, HEIGHT);
//        scene.setFill(Color.TRANSPARENT);
        scene.setFill(Color.LIGHTGRAY);
        scene.setCamera(camera);

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(-500);

        initMouseControl(group, scene);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    group.translateZProperty().set(group.getTranslateZ() + 100);
                    break;
                case S:
                    group.translateZProperty().set(group.getTranslateZ() - 100);
                    break;
                case ESCAPE:
                    primaryStage.close();
                    break;
            }
        });

        primaryStage.setScene(scene);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    private void initMouseControl(SmartGroup group, Scene scene) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
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

    public static void main(String[] args) {
        launch(args);
    }


    class SmartGroup extends Group {

        Rotate r;
        Transform t = new Rotate();

        void rotateByX(int ang) {
            r = new Rotate(ang, Rotate.X_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

        void rotateByY(int ang) {
            r = new Rotate(ang, Rotate.Y_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
    }
}

