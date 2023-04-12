package sim.danslchamp.svg;

import javafx.application.Application;
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
    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle point = new Circle(0, 0, 3);
        AnchorPane.setLeftAnchor(point, POS_POINT);
        AnchorPane.setTopAnchor(point, POS_POINT);

        Résistor résistor = new Résistor(0, 0, false);
        Group group = résistor.getSymbole2D();
        AnchorPane.setLeftAnchor(group, POS_POINT);
        AnchorPane.setTopAnchor(group, POS_POINT);

        group.setRotationAxis(new Point3D(250.0, 250d, 250d));

        Slider slider = new Slider(0,360,0);
        slider.valueProperty().addListener((c, o, v) -> {
            if (v != null)
                group.setRotate(v.doubleValue());
        });
        AnchorPane.setBottomAnchor(slider, 10d);
        AnchorPane.setLeftAnchor(slider, 5d);
        AnchorPane.setRightAnchor(slider, 5d);

        AnchorPane anchorPane = new AnchorPane(point, group, slider);

        primaryStage.setScene(new Scene(anchorPane, POS_POINT*2+résistor.getLargeur(), POS_POINT*2+résistor.getHauteur()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
