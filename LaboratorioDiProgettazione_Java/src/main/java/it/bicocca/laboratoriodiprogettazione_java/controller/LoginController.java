package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.GroupeRMain;
import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Classe controller per 00_Login.fxml
 */
public class LoginController implements Initializable {

    /**
     * Riferimento TextField Email di 00_Login.fxml
     */
    @FXML
    public TextField TextFieldUserEmail;

    /**
     * Riferimento TextField Psw di 00_Login.fxml
     */
    @FXML
    public TextField TextFieldUserPsw;

    /**
     * Stringa che contiene Email di 00_Login.fxml
     */
    String userEmail;

    /**
     * Stringa che contiene Psw di 00_Login.fxml
     */
    String userPsw;

    public static String ruolo = "";


    /**
     * Metodo chiamato automaticamente dopo che il file FXML è stato caricato e tutti i suoi elementi sono stati inizializzati.
     * È un punto opportuno per eseguire l'inizializzazione aggiuntiva di variabili o risorse necessarie per il controller.
     *
     * @param url Il percorso URL relativo al file FXML.
     * @param resourceBundle Il ResourceBundle che contiene le risorse localizzate per questo file FXML, o null se nessuna risorsa è stata specificata.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Metodo per gestire la chiusura dell'applicazione collegata al bottone close
     * @param mouseEvent L'evento del mouse che ha scatenato la chiamata al metodo.
     */

    public void CloseApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    /**
     * Metodo che serve per eseguire il login al sistema e verificare il ruolo dell'utente che ha eseguito il login.
     * In base al ruolo recuperato, viene caricata la pagina FXML corretta.
     * @param actionEvent L'evento del mouse che ha scatenato la chiamata al metodo.
     */
    public void LoginAction(ActionEvent actionEvent) throws IOException {

        userEmail = TextFieldUserEmail.getText().strip();
        userPsw = TextFieldUserPsw.getText().strip();

        String isAuthenticate = DbOperations.authenticate(userEmail, userPsw);

        if (isAuthenticate!=null){

            //Se il login va a buon fine, chiudiamo la finestra di login
            Stage currentStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

            //Salviamo la variabile statica del ruolo del dipendente che ha effettuato il login
            ruolo = isAuthenticate;

            //Apriamo lo stage "MainPage"
            Stage stage = new Stage();


            //Eseguiamo uno switch case in base al ruolo appena recuperato e carichiamo la finestra corretta.
            switch (ruolo) {
                case "Capo magazzino":
                case "Capo officina":
                case "Direttore di filiale":
                case "root":
                    try {
                        GroupeRMain.setRoot("01_Staff");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Officina":
                case "Magazzino":
                    try {
                        GroupeRMain.setRoot("01_MainPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    //Aggiunta la logica nel caso in cui il ruolo recuperato non sia valido.
                    System.out.println("Accesso negato. Ruolo non autorizzato.");
                    break;
            }
            stage.initStyle(StageStyle.TRANSPARENT);
            GroupeRMain.scene.setFill(Color.TRANSPARENT);
            stage.setScene(GroupeRMain.scene);
            GroupeRMain.scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setMinHeight(720);
            stage.setMinWidth(1280);
            stage.show();

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Errore");
            alert.setContentText("Email o Password errati");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }
    }
}
