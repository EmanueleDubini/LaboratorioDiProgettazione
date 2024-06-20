package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.entity.Rim;
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

public class NewRimController implements Initializable {

    String rimBrand, rimDimension, rimPrice, rimDiscount;

    int nRim;

    @FXML
    private TextField TextFieldMarcaCerchio;

    @FXML
    private TextField TextFieldDimensioneCerchio;

    @FXML
    private TextField TextFieldPrezzoCerchio;

    @FXML
    private TextField TextFieldScontoCerchio;

    @FXML
    private Spinner<Integer> spinnerRimQuan = new Spinner<>();
    SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0);

    /**
     * Inizializza il valore del selettore della quantità di cerchioni e imposta l'allineamento del campo editor del selettore al centro.
     * Questo metodo viene invocato automaticamente dopo che il file FXML è stato caricato e l'inizializzazione dei componenti è stata completata.
     *
     * @param url l'URL di localizzazione utilizzato per risolvere i percorsi relativi per l'oggetto radice, o null se la localizzazione non è nota.
     * @param resourceBundle le risorse utilizzate per localizzare l'oggetto radice, o null se l'oggetto radice non è stato localizzato.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerRimQuan.setValueFactory(spinnerValueFactory);
        spinnerRimQuan.editorProperty().get().setAlignment(Pos.CENTER);
    }

    /**
     * Metodo per inserire un cerchio dopo aver compilato i dettagli nel form.
     * @param actionEvent L'evento che scatena l'azione.
     * @throws IOException In caso di errore di I/O durante il caricamento della risorsa FXML.
     */
    public void InsertRimButton(ActionEvent actionEvent) throws IOException {
        rimBrand = TextFieldMarcaCerchio.getText().trim();
        rimDimension = TextFieldDimensioneCerchio.getText().trim();
        rimPrice = TextFieldPrezzoCerchio.getText().trim();
        rimDiscount = TextFieldScontoCerchio.getText().trim();
        nRim = spinnerRimQuan.getValue();

        if(rimBrand.equals("")|| rimPrice.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Dati Cerchio mancanti!");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        } else if (!rimPrice.matches("^\\d+([.,]\\d{1,2})?$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Formato prezzo cerchi non valido");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        } else if (!rimDiscount.matches("^\\d+([.,]\\d{1,2})?$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Formato prezzo cerchi non valido");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        } else{

            if(PrevTiresController.maxRims == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un Errore");
                alert.setContentText("Hai raggiunto il numero massimo di cerchi inseribili in questo preventivo!");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");
                alert.showAndWait();

                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                stage.hide();
            }else{
                //Creo il nome dello pneumatico e creo l'oggetto pneumatico
                String idCerchio = "cerchio" + PrevTiresController.iRim;
                Rim cerchio = new Rim(rimBrand, nRim, rimPrice, rimDiscount);

                //Recupero lo stage per chiudere la finestra
                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                stage.hide();

                //Inserisco un nuovo elemento contenete il nome dello pneunmatico all'interno della grid richiamando il metodo
                Boolean controle = PrevTiresController.inserisciCerchio(rimBrand, cerchio);
                if (controle){
                    PrevTiresController.maxRims--;
                }
                //Incremento la variabile per il nome degli pneumatici
                PrevTiresController.iRim++;
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
