package sim.danslchamp.Util;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ComposantesListCell extends ListCell<Composant> {

    private Circuit circuit;
    public ComposantesListCell(Circuit circuit) {
        this.circuit = circuit;
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

            for (Composant.ValeurNomWrapper valeurNomWrapper :
                    item.getValeursModifiables()) {
                HBox hBox = new HBox();
                HBox.setHgrow(hBox, Priority.ALWAYS);
                hBox.setMinWidth(300);
                hBox.setSpacing(10);

                Label label = new Label(valeurNomWrapper.nom + ": ");
                label.setMinWidth(120);

                TextField textField = new TextField(valeurNomWrapper.valeur.getValeurStr());
                textField.setOnKeyTyped(eh -> {
                    valeurNomWrapper.valeur.setValeur(textField.getText(), Composant.Unite.UNITE);
                    circuit.calculCircuit();
                });    // TODO: 2023-04-03 Unités (ComboBox)

                hBox.getChildren().addAll(label, textField);
                vBox.getChildren().add(hBox);
                setGraphic(vBox);
            }
        }
    }
}
