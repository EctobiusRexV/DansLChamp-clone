package prototypes;

import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class DragAndDropControleur {

    @FXML
    private AnchorPane conceptionAnchorPane;
    private Line currentLine;

    @FXML
    void dragDetected(MouseEvent event) {
        System.out.println("drag Detected\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
    }

    @FXML
    void dragDone(DragEvent event) {
        System.out.println("drag Done\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
    }

    @FXML
    void dragDropped(DragEvent event) {
        System.out.println("drag Dropped\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
    }

    @FXML
    void dragEntered(DragEvent event) {
        System.out.println("drag Entered\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
    }

    @FXML
    void dragExited(DragEvent event) {
        System.out.println("drag Exited\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
    }

    @FXML
    void dragOver(DragEvent event) {
        System.out.println("drag Over\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
    }

    @FXML
    void mouseDragReleased(MouseDragEvent event) {
        System.out.println("mouse Drag Released\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
    }

    @FXML
    void mouseClicked(MouseEvent event) {
        System.out.println("mouse Clicked\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
        currentLine = new Line(event.getX(), event.getY(), event.getX(), event.getY());
        conceptionAnchorPane.getChildren().add(currentLine);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        System.out.println("mouse Dragged\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
        // Drag jusqu'au mouseReleased()
        currentLine.setEndX(event.getX());
        currentLine.setEndY(event.getY());
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        System.out.println("mouse Released\n\tx: " + event.getX() + "\n\ty: " + event.getY() + "\n");
    }
}
