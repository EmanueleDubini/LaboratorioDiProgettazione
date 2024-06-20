package it.bicocca.laboratoriodiprogettazione_java.controller;


import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe controller per 02_NewStaff.fxml
 */
public class NewStaffController implements Initializable {
    @FXML
    public TextField TextFieldUserName;

    @FXML
    public TextField TextFieldUserSurname;

    @FXML
    public TextField TextFieldUserEmailIn;

    @FXML
    public TextField TextFieldUserPswIn;

    @FXML
    public ComboBox<String> comboBoxRole = new ComboBox<>();

    @FXML
    private AnchorPane window;

    /**
     * Metodo per inizializzare la ComboBox del ruolo del personale, in base al login effettuato.
     * Il Capo officina può aggiungere solo i dipendenti con ruolo "Officina".
     * Il Capo magazzino può aggiungere solo i dipendenti con ruolo "Magazzino".
     * @param url l'URL della risorsa FXML associata a questa ComboBox (può essere null se non applicabile)
     * @param resourceBundle resourceBundle il bundle di risorse contenente le localizzazioni specifiche (può essere null se non applicabile)
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
     * Metodo collegato al bottone per inserire il dipendente all'interno del database
     * @param actionEvent actionEvent l'evento associato al clic del pulsante (ActionEvent)
     */
    public void InsertStaffButton(ActionEvent actionEvent) throws IOException {
        String userName = TextFieldUserName.getText().strip();
        String userSurname = TextFieldUserSurname.getText().strip();
        String userEmail = TextFieldUserEmailIn.getText().strip();
        String userPsw = TextFieldUserPswIn.getText().strip();
        String role = comboBoxRole.getValue();



        if (!userName.isEmpty() && !userSurname.isEmpty() && !userEmail.isEmpty() && !userPsw.isEmpty() && role != null) {
            boolean isInserted = DbOperations.insertStaff(userName, userSurname, userEmail, userPsw, role);

            if(isInserted){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText("Ok");
                alert.setContentText("Il dipendente è stato inserito correttamente.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();

                //Se l'inserimento va a buon fine, chiudiamo la finestra di login
                Stage currentStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRORE");
                alert.setHeaderText("Errore");
                alert.setContentText("C'è stato un errore nell'inserimento del dipendente. Verifica che non sia già stato inserito.");

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
     * Questo metodo è utile per chiudere la finestra.
     * @param actionEvent actionEvent l'evento associato alla chiusura della finestra
     */
    public void DeleteButton(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }
}
