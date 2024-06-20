package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.entity.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller per la finestra di modifica del personale.
 * Questa classe gestisce l'interfaccia utente per la modifica delle informazioni del personale,
 * inclusi campi come nome, cognome, email, ruolo, ecc.
 */
public class EditStaffController implements Initializable {

    @FXML
    private TextField TextFieldUserName;

    @FXML
    private TextField TextFieldUserSurname;

    @FXML
    private TextField TextFieldUserEmailIn;

    @FXML
    private TextField TextFieldUserPswIn;

    @FXML
    public ComboBox<String> comboBoxRole = new ComboBox<>();

    String oldemail = "";

    /**
     * Inizializza il controller dopo il caricamento dell'interfaccia utente.
     * Imposta i valori predefiniti per il combobox ruolo in base al ruolo dell'utente autenticato.
     *
     * @param url            l'URL della risorsa FXML associata a questo controller, può essere null.
     * @param resourceBundle il bundle di risorse che contiene oggetti specifici per la localizzazione, può essere null.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /////////COMBOBOX RUOLO/////////
        switch (LoginController.ruolo) {
            case "Capo officina":
                comboBoxRole.setValue("");
                comboBoxRole.getItems().clear();
                comboBoxRole.getItems().add("Officina");
                break;
            case "Capo magazzino":
                comboBoxRole.setValue("");
                comboBoxRole.getItems().clear();
                comboBoxRole.getItems().add("Magazzino");
                break;
            default:
                comboBoxRole.setValue("");
                comboBoxRole.getItems().clear();
                comboBoxRole.getItems().addAll("Officina", "Magazzino", "Direttore di filiale", "Capo officina", "Capo magazzino");
                break;
        }
    }

    /**
     * Popola i campi dell'interfaccia utente con le informazioni del dipendente selezionato.
     *
     * @param selectedStaff Il dipendente selezionato di cui visualizzare le informazioni.
     */
    public void staffDetails (Staff selectedStaff){
        String name = selectedStaff.getNome();
        String surname = selectedStaff.getCognome();
        oldemail = selectedStaff.getE_mail();
        String role = selectedStaff.getRuolo();

        TextFieldUserName.setText(name);
        TextFieldUserSurname.setText(surname);
        TextFieldUserEmailIn.setText(oldemail);
        comboBoxRole.setValue(role);
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

    /**
     * Gestisce l'evento di clic sul pulsante "Aggiorna" per inserire le modifiche al personale.
     *
     * @param actionEvent L'evento di azione che ha scatenato la chiamata a questo metodo.
     */
    public void updateStaffButton(ActionEvent actionEvent) {
        String userName = TextFieldUserName.getText().strip();
        String userSurname = TextFieldUserSurname.getText().strip();
        String userEmail = TextFieldUserEmailIn.getText().strip();
        String userPsw = TextFieldUserPswIn.getText().strip();
        String role = comboBoxRole.getValue();

        if (!userName.isEmpty() && !userSurname.isEmpty() && !userEmail.isEmpty() && !userPsw.isEmpty() && role != null) {
            boolean isInserted = DbOperations.updateStaff(oldemail, userName, userSurname, userEmail, userPsw, role);

            if(isInserted){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText("Ok");
                alert.setContentText("Il dipendente è stato aggiornato correttamente.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();

                Stage currentStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRORE");
                alert.setHeaderText("Errore");
                alert.setContentText("C'è stato un errore nell'aggiornamento del dipendente. Verifica che non sia già stato inserito.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Errore");
            alert.setContentText("Non hai compilato tutti i campi");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }

    }

    /**
     * Gestisce l'evento di clic sul pulsante "Elimina" per cancellare un dipendente.
     *
     * @param actionEvent L'evento di azione che ha scatenato la chiamata a questo metodo.
     */
    public void deleteStaffButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma cancellazione");
        alert.setHeaderText("Conferma cancellazione");
        alert.setContentText("Sei sicuro di voler cancellare questo dipendente?");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = DbOperations.deleteStaff(oldemail);
            if (isDeleted) {
                Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmationAlert.setTitle("Cancellazione avvenuta");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Il dipendente è stato cancellato con successo.");

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
                errorAlert.setContentText("Si è verificato un errore durante la cancellazione del dipendente.");

                DialogPane errorDialogPane = errorAlert.getDialogPane();
                errorDialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                errorDialogPane.getStyleClass().add("alert");

                errorAlert.showAndWait();
            }
        }
    }
}
