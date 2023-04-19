package sim.danslchamp.Util;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;

import java.util.List;

public class ComposantsListCell extends ListCell<Composant> {

    private Circuit circuit;
    public ComposantsListCell(Circuit circuit) {
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
                // Spinner avec la valeur qui s'incrémente à coup de 10% de la valeur actuelle
                Spinner spinner = new Spinner(
                        Double.MIN_VALUE,
                        Double.MAX_VALUE, // TODO: 2023-04-18 Ne devrait-il pas y avoir une limite?
                        valeurNomWrapper.valeur.getValeur(),
                        valeurNomWrapper.valeur.getValeur() * 0.1);
                // set Spinner Editable
                spinner.setEditable(true);
                // handle ParseException

//                TextField textField = new TextField(valeurNomWrapper.valeur.getValeurStr());


                // ComboBox pour choisir l'unité avec le symbole
                ComboBox<Composant.Unite> uniteComboBox = new ComboBox<>();
                uniteComboBox.getItems().addAll(List.of(Composant.Unite.values()));
                uniteComboBox.setValue(valeurNomWrapper.valeur.getUnite());

                spinner.getEditor().setOnAction(eh -> {
                    valeurNomWrapper.valeur.setValeur(spinner.getEditor().getText(), uniteComboBox.getValue());
                    // TODO: 2023-04-18 Le spinner devrait recalculer son step de 10%.
                    // FIXME: 2023-04-18 setOnAction ne gère pas l'incrémentation avec les boutons-flèches ou les flèches du clavier
                    circuit.calculCircuit();
                });

                uniteComboBox.setOnAction(event -> {
                    valeurNomWrapper.valeur.setValeur(spinner.getEditor().getText(), uniteComboBox.getValue());
                    circuit.calculCircuit();
                });

                hBox.getChildren().addAll(label, spinner, uniteComboBox, new Label(valeurNomWrapper.valeur.getSymbole()));
                vBox.getChildren().add(hBox);
                setGraphic(vBox);
            }
        }
    }
}
