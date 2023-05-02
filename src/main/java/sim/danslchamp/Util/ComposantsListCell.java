package sim.danslchamp.Util;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;
import sim.danslchamp.circuit.Diagramme;

import java.util.List;

public class ComposantsListCell extends ListCell<Composant> {

    private Circuit circuit;
    public ComposantsListCell(Circuit circuit) {
        this.circuit = circuit;
    }

    private Composant oldSelection;

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
                double val = valeurNomWrapper.valeur.getValeur(Composant.Unite.PLUS_PETITE_POSSIBLE);
                Spinner spinner = new Spinner(
                        Double.MIN_VALUE,
                        Double.MAX_VALUE, // TODO: 2023-04-18 Ne devrait-il pas y avoir une limite?
                        val,
                        val * 0.1);

                spinner.focusedProperty().addListener((l, old, focused) -> {
                    if (focused)
                        Diagramme.surligner(oldSelection, item);
                    oldSelection = item;
                });
                // set Spinner Editable
                spinner.setEditable(true);
                // handle ParseException

//                TextField textField = new TextField(valeurNomWrapper.valeur.getValeurStr());


                // ComboBox pour choisir l'unité avec le symbole
                ComboBox<Composant.Unite> uniteComboBox = new ComboBox<>();
                uniteComboBox.getItems().addAll(List.of(Composant.Unite.values()));
                uniteComboBox.setValue(valeurNomWrapper.valeur.getUnite());

                spinner.valueProperty().addListener((l, oldvalue, newvalue) -> {
                    if (newvalue != null) {
                        valeurNomWrapper.valeur.setValeur(newvalue.toString(), uniteComboBox.getValue());
                        circuit.calculCircuit();
                        //TODO pas sur lala
                        circuit.getDiagramme2D().getGroup().getChildren().clear();
                        circuit.getDiagramme3D().getGroup().getChildren().clear();
                        for (int i = 0; i < circuit.getComposants().size(); i++) {
                            circuit.getDiagramme2D().addComposant(circuit.getComposants().get(i));
                        }
                        for (int i = 0; i < circuit.getComposants().size(); i++) {
                            circuit.getDiagramme3D().addComposant(circuit.getComposants().get(i));
                        }
                    }

                    // TODO: 2023-04-18 Le spinner devrait recalculer son step de 10%.

//                    circuit.calculCircuit();
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
