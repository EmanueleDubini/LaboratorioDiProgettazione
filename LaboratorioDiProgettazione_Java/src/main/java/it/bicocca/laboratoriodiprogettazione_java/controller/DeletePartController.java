package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.entity.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

public class DeletePartController {

    @FXML
    Label labelIdPart;

    @FXML
    Label labelNamePart;

    @FXML
    Label labelBrandPart;

    @FXML
    Label labelPricePart;

    @FXML
    Label labelQuantityPart;

    String id;
    /**
     * Popola i campi dell'interfaccia per la modifica dei ricambi.
     *
     * @param selectedpart Il ricambio selezionato di cui visualizzare le informazioni.
     */
    public void partDetails (Part selectedpart){
        id = selectedpart.getId();
        String name = selectedpart.getName();
        String brand = selectedpart.getBrand();
        String price = selectedpart.getPrice();
        String quantity = String.valueOf(selectedpart.getQuantity());

        labelIdPart.setText(id);
        labelNamePart.setText(name);
        labelBrandPart.setText(brand);
        labelPricePart.setText(price);
        labelQuantityPart.setText(quantity);
    }

    /**
     * Gestisce l'evento di clic sul pulsante "Elimina" per cancellare un ricambio.
     *
     * @param actionEvent L'evento di azione che ha scatenato la chiamata a questo metodo.
     */
    public void DeletePartButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma cancellazione");
        alert.setHeaderText("Conferma cancellazione");
        alert.setContentText("Sei sicuro di voler cancellare questo ricambio?");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = DbOperations.deletePart(id);
            if (isDeleted) {
                Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmationAlert.setTitle("Cancellazione avvenuta");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Il ricambio è stato cancellato con successo.");

                DialogPane confirmationDialogPane = confirmationAlert.getDialogPane();
                confirmationDialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                confirmationDialogPane.getStyleClass().add("alert");

                confirmationAlert.showAndWait();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Errore");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Si è verificato un errore durante la cancellazione del ricambio.");

                DialogPane errorDialogPane = errorAlert.getDialogPane();
                errorDialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                errorDialogPane.getStyleClass().add("alert");

                errorAlert.showAndWait();
            }
        }
    }

    /**
     * Gestisce l'evento di clic sul pulsante "Annulla" per chiudere la finestra di modifica del personale.
     *
     * @param actionEvent L'evento di azione che ha scatenato la chiamata a questo metodo.
     */
    public void DeleteButton(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }
}
