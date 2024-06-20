package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe controller per 02_NewPart.fxml
 */
public class NewPartController implements Initializable {

    @FXML
    TextField TextFieldPartId;

    @FXML
    TextField TextFieldPartName;

    @FXML
    TextField TextFieldPartBrand;

    @FXML
    TextField TextFieldPartPrice;

    @FXML
    private Spinner<Integer> spinnerPartQuan = new Spinner<>();
    //Definiamo la quantità massima di ogni ricambio
    SpinnerValueFactory<Integer> spinnerValueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999, 0);

    int nPart;

    /**
     * Metodo per inizializzare lo spinner della quantità del ricambio.
     * @param url l'URL della risorsa FXML associata a questa ComboBox (può essere null se non applicabile)
     * @param resourceBundle resourceBundle il bundle di risorse contenente le localizzazioni specifiche (può essere null se non applicabile)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerPartQuan.setValueFactory(spinnerValueFactory1);
        spinnerPartQuan.editorProperty().get().setAlignment(Pos.CENTER);
    }

    /**
     * Questo metodo gestisce l'evento di inserimento di un nuovo ricambio tramite l'interfaccia utente.
     *
     * @param actionEvent actionEvent l'evento di azione generato dall'interfaccia utente
     */
    public void InsertPartButton(ActionEvent actionEvent) {
        String partId = TextFieldPartId.getText().strip();
        String partName = TextFieldPartName.getText().strip();
        String partBrand = TextFieldPartBrand.getText().strip();
        String partPrice = TextFieldPartPrice.getText().strip();
        nPart = spinnerPartQuan.getValue();

        // Regex per validare il formato del prezzo
        String pricePattern = "^[0-9]*\\.?[0-9]+$";
        boolean isPriceValid = partPrice.matches(pricePattern);

        // Controlla se tutti i campi sono non vuoti
        if (!partId.isEmpty() && !partName.isEmpty() && !partBrand.isEmpty() && !partPrice.isEmpty() && isPriceValid) {
            // Tutti i campi sono stati inseriti, procedi con l'elaborazione
            boolean isInserted = DbOperations.insertPart(partId, partName, partBrand, partPrice, nPart);

            if(isInserted){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText("Ok");
                alert.setContentText("Il ricambio è stato inserito correttamente.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();

                //Se l'inserimento va a buon fine, chiudiamo la finestra di login
                Stage currentStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();

                PartsListController partsListController = PartsListController.getIstance();
                partsListController.initializeTable();
                partsListController.loadAllParts();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRORE");
                alert.setHeaderText("Errore");
                alert.setContentText("C'è stato un errore nell'inserimento del ricambio. Verifica che non sia già stato inserito.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();
            }
        } else {
            // Almeno uno dei campi è vuoto, gestisci il caso appropriato (ad esempio, mostra un messaggio di errore)
            // Esempio:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Errore");
            alert.setContentText("Non tutti i campi sono completi o il prezzo è errato.");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }

    }

    /**
     * Questo metodo è utile per chiudere la finestra.
     * @param actionEvent actionEvent l'evento associato alla chiusura della finestra
     */
    public void DeleteButton(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }


}
