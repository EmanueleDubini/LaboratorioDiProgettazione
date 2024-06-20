package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.common.PdfCreatorUserPassword;
import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.entity.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * Controller per la finestra di aggiunta di un nuovo cliente 02_NewClient.fxml.
 */
public class NewClientController {
    @FXML
    public TextField TextFieldClientName;
    
    @FXML
    public TextField TextFieldClientSurname;
    
    @FXML
    public TextField TextFieldClientNumber;
    
    @FXML
    public TextField TextFieldClientEmail;

    /**
     * Metodo collegato al bottone per inserire un nuovo cliente all'interno del database
     * @param actionEvent actionEvent l'evento associato al clic del pulsante (ActionEvent)
     */
    public void InsertClientButton(ActionEvent actionEvent) throws IOException {
        String clientName = TextFieldClientName.getText().strip();
        String clientSurname = TextFieldClientSurname.getText().strip();
        String clientNumber = TextFieldClientNumber.getText().strip();
        String clientEmail = TextFieldClientEmail.getText().strip();

        //controllo campi vuoti
        if (clientName.isEmpty() | clientSurname.isEmpty() | clientNumber.isEmpty() | clientEmail.isEmpty()){
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

        //Controllo validità email
        else if (!(Pattern.matches("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", clientEmail))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Email cliente non valida.\nRiprovare");

            alert.showAndWait();
        }

        // Controllo validità numero di telefono con spazi
        else if (!clientNumber.matches("\\d{3}(\\s)?\\d{3}(\\s)?\\d{3}(\\d{1})?")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Numero di telefono non valido.\nRiprovare ");

            alert.showAndWait();
        }

        else {

            // Genera una password sicura
            String clientPsw = generateSecurePassword();

            boolean isInserted = DbOperations.insertClient(clientName, clientSurname, clientEmail, clientNumber, clientPsw);

            if(isInserted){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText("Cliente inserito correttamente");

                // Creare un TextFlow con due Text: uno per il testo introduttivo e uno per la password
                Text introText = new Text("La password provvisoria del cliente è: ");
                introText.setStyle("-fx-fill: white");
                Text passwordText = new Text(clientPsw);
                passwordText.setStyle("-fx-fill: white; -fx-underline: true;"); // Aggiungere stile solo alla password

                // Aggiungere un event handler per copiare la password nella clipboard quando il Text viene cliccato
                passwordText.setOnMouseClicked(event -> {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(clientPsw);
                    clipboard.setContent(content);

                    Alert copyAlert = new Alert(Alert.AlertType.INFORMATION);
                    copyAlert.setTitle("INFO");
                    copyAlert.setHeaderText(null);
                    copyAlert.setContentText("La password è stata copiata nella clipboard.");
                    copyAlert.showAndWait();
                });

                TextFlow textFlow = new TextFlow(introText, passwordText);

                // Aggiungere il TextFlow nel contenuto del dialogo
                alert.getDialogPane().setContent(textFlow);

                // Aggiungere il foglio di stile
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();

                Client client = new Client(clientName, clientSurname, clientEmail, clientNumber);

                PdfCreatorUserPassword pdfCreatorUserPassword = new PdfCreatorUserPassword(client, clientPsw);
                String namePdfCreated = pdfCreatorUserPassword.writeOnPdf();
                Desktop.getDesktop().open(new File(namePdfCreated));

                Stage currentStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();


            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRORE");
                alert.setHeaderText("Errore");
                alert.setContentText("C'è stato un errore nell'inserimento del cliente. Verifica che non sia già stato inserito.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();
            }
        }

    }

    /**
     * Genera una password casuale e sicura.
     * La password generata contiene caratteri casuali tratti dalla stringa di caratteri consentiti.
     * La lunghezza della password generata è fissata a 15 caratteri.
     *
     * @return una stringa rappresentante la password casuale generata.
     */
    private String generateSecurePassword() {
        // Caratteri utilizzabili nella password
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*-=+";

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder(15); // Password di lunghezza fissa di 15 caratteri
        for (int i = 0; i < 15; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(randomIndex));
        }

        return password.toString();
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
