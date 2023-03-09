package sim.danslchamp.Util;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sim.danslchamp.Composante;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ComposantesListCell extends ListCell<Composante> {
    public ComposantesListCell() {

    }

    @Override
    protected void updateItem(Composante item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setItem(null);
            setGraphic(null);
        } else {
            VBox vBox = new VBox();

            vBox.getChildren().add(new Label(item.getName()));

            for (Method m : item.getSetMethods()) {
                HBox hBox = new HBox();
                HBox.setHgrow(hBox, Priority.ALWAYS);
                hBox.setMinWidth(300);
                hBox.setSpacing(10);
                Label label = new Label(m.getName().replace("set", ""));
                label.setMinWidth(120);
                hBox.getChildren().add(label);
                TextField textField = new TextField();
                textField.setOnKeyTyped(eh -> {
                    try {
                        if (textField.getText() != null) {
                            m.invoke(item, textField.getText());
                        }

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
                hBox.getChildren().add(textField);
                vBox.getChildren().add(hBox);
                setGraphic(vBox);
            }
        }
    }
}
