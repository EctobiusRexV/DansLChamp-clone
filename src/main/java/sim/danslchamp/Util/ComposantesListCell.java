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

            for (Composant.Valeur valeur :
                    item.getValeursModifiables()) {
                HBox hBox = new HBox();
                HBox.setHgrow(hBox, Priority.ALWAYS);
                hBox.setMinWidth(300);
                hBox.setSpacing(10);

                TextField textField = new TextField();
                textField.setOnKeyTyped(eh -> valeur.setValeur(textField.getText(), Composant.Unite.UNITE));    // TODO: 2023-04-03 Unités (ComboBox)

                hBox.getChildren().addAll(/*getLabelFromMethod(),*/ textField);     // TODO: 2023-04-03 Afficher nom
                vBox.getChildren().add(hBox);
                setGraphic(vBox);
            }
        }
    }
}
