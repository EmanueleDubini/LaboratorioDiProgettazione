package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.entity.Tire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewPneumController implements Initializable {

    @FXML
    private TextField TextFieldMarcaPneum;

    @FXML
    private TextField TextFieldDimensionePneum;

    @FXML
    private TextField TextFieldPrezzoPneum;

    @FXML
    private TextField TextFieldScontoPneum;

    @FXML
    private TextField TextFieldPfu;

    @FXML
    private Spinner<Integer> spinnerTireQuan = new Spinner<>();
    SpinnerValueFactory<Integer> spinnerValueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0);


    String tireBrand, tireDimension, tirePrice, tireDiscount, pricePfu;
    int nTires;

    /**
     * Inizializza il valore del selettore della quantità di pneumatici e imposta l'allineamento del campo editor del selettore al centro.
     * Questo metodo viene invocato automaticamente dopo che il file FXML è stato caricato e l'inizializzazione dei componenti è stata completata.
     *
     * @param url l'URL di localizzazione utilizzato per risolvere i percorsi relativi per l'oggetto radice, o null se la localizzazione non è nota.
     * @param resourceBundle le risorse utilizzate per localizzare l'oggetto radice, o null se l'oggetto radice non è stato localizzato.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerTireQuan.setValueFactory(spinnerValueFactory1);
        spinnerTireQuan.editorProperty().get().setAlignment(Pos.CENTER);
    }


    /**
     * Metodo per inserire un pneumatico dopo aver compilato i dettagli nel form.
     * @param actionEvent L'evento che scatena l'azione.
     * @throws IOException In caso di errore di I/O durante il caricamento della risorsa FXML.
     */
    public void InsertTireButton(ActionEvent actionEvent) throws IOException {
        tireBrand = TextFieldMarcaPneum.getText().trim();
        tireDimension = TextFieldDimensionePneum.getText().trim();
        tirePrice = TextFieldPrezzoPneum.getText().trim();
        tireDiscount = TextFieldScontoPneum.getText().trim();
        nTires = spinnerTireQuan.getValue();
        pricePfu = TextFieldPfu.getText().trim();

        if (tireBrand.equals("") || tirePrice.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Dati Pneumatico mancanti!");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        } else if (!tirePrice.matches("^\\d+([.,]\\d{1,2})?$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Formato prezzo pneumatici non valido");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        } else if (!pricePfu.matches("^\\d+([.,]\\d{1,2})?$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Formato prezzo PFU non valido");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }else if (!tireDiscount.matches("^\\d+([.,]\\d{1,2})?$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Formato sconto pneumatici non valido");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        } else {
            if (PrevTiresController.maxTires == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un Errore");
                alert.setContentText("Hai raggiunto il numero massimo di pneumatici inseribili in questo preventivo!");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");
                alert.showAndWait();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.hide();
            } else {
                //Creo il nome tire e creo l'oggetto tire
                String idPneumatico = "pneum" + PrevTiresController.iTire;
                Tire tire = new Tire(idPneumatico, tireBrand, tireDimension, tirePrice, tireDiscount, nTires, pricePfu);

                //Recupero lo stage per chiudere la finestra
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.hide();

                //Inserisco un nuovo elemento contenete il nome dello pneunmatico all'interno della grid richiamando il metodo
                Boolean controle = PrevTiresController.AddTire(tireBrand, tire);
                if (controle) {
                    PrevTiresController.maxTires--;
                }
                //Incremento la variabile per il nome degli pneumatici
                PrevTiresController.iTire++;
            }
        }
    }

    /**
     * Gestisce l'azione del pulsante chiudendo la finestra corrente.
     * Questo metodo viene invocato quando l'utente fa clic sul pulsante di annullamento
     * presente in una delle finestre dell'interfaccia utente.
     *
     * @param actionEvent L'evento di azione generato dal click dell'utente sul pulsante di annullamento.
     */
    public void CancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }


}
