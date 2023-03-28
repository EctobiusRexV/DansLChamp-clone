package sim.danslchamp.Util;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sim.danslchamp.circuit.Composant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ComposantesListCell extends ListCell<Composant> {
    public ComposantesListCell() {

    }

    @Override
    protected void updateItem(Composant item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setItem(null);
            setGraphic(null);
        } else {
            VBox vBox = new VBox();

            vBox.getChildren().add(new Label(item.getClass().getSimpleName()));

            for (Method m : item.getSetMethods()) {
                HBox hBox = new HBox();
                HBox.setHgrow(hBox, Priority.ALWAYS);
                hBox.setMinWidth(300);
                hBox.setSpacing(10);

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

                hBox.getChildren().addAll(getLabelFromMethod(m), textField);
                vBox.getChildren().add(hBox);
                setGraphic(vBox);
            }
        }
    }

    /**
     * Crée un fx.Label depuis un nom de méthode au format set_Attribut_Unités au format Attribut (Unités) :
     * @param method
     * @return fx.Label
     */
    // fixme ou method.getAnnotation()
    private Label getLabelFromMethod(Method method) {
        String[] parts =        // attribut, unités
                method.getName()
                        .substring(3)   // set
                        .split("_");

        Label label = new Label(
                parts[0].replaceAll("[A-Z]", " $0")
                + (parts.length > 1 ? " (" + parts[1] + ") :" : ""));
        label.setMinWidth(120);

        return label;
    }
}
