package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.entity.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller per le due finestre, che compongono il menù guidato, per l'aggiunta di un nuovo veicolo, 02_NewCars.fxml.
 */
public class NewCarController implements Initializable {

    /* elementi finestra 02_NewCar_1*/
    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Client> searchResultsTable;

    private List<Client> allClients;

    private static Client selectedClient;

    private static boolean isFirstWindowVisible = true; // Variabile di stato per indicare se la finestra 02_NewCar_1.fxml è attualmente visualizzata


    /* elementi finestra 02_NewCar_2*/
    @FXML
    public TextField marcaTextField;

    @FXML
    public TextField modelloTextField;

    @FXML
    public TextField targaTextField;

    @FXML
    public TextField telaioTextField;

    @FXML
    public TextArea diagnosiTextArea;

    @FXML
    public TextField statoRiparazioneTextField;

    @FXML
    public Label labelNomeCognome;


    /**
     * Metodo chiamato durante l'inizializzazione del controller.
     *
     * @param url            L'URL di inizializzazione.
     * @param resourceBundle Il ResourceBundle specifico della lingua.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (isFirstWindowVisible) {
            initializeTable();
            loadAllClients();
        } else{
            String nomeCognome = selectedClient.getNome() + " " + selectedClient.getCognome();
            labelNomeCognome.setText(nomeCognome);
        }
    }

    /**
     * Inizializza la struttura della tabella dei risultati di ricerca.
     */
    private void initializeTable() {
        TableColumn<Client, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Client, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Client, String> cognomeColumn = new TableColumn<>("Cognome");
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));

        TableColumn<Client, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));

        TableColumn<Client, String> telephoneColumn = new TableColumn<>("Telefono");
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telefono"));

        idColumn.setPrefWidth(30); // Larghezza della colonna ID
        nomeColumn.setPrefWidth(80); // Larghezza della colonna Nome
        cognomeColumn.setPrefWidth(80); // Larghezza della colonna Cognome
        emailColumn.setPrefWidth(120); // Larghezza della colonna Email
        telephoneColumn.setPrefWidth(100); // Larghezza della colonna Telefono

        searchResultsTable.getColumns().addAll(idColumn,nomeColumn, cognomeColumn, emailColumn, telephoneColumn);
    }

    /**
     * Carica tutti i clienti dal database e li visualizza nella tabella.
     */
    private void loadAllClients() {

        this.allClients = DbOperations.getAllClients();
        searchResultsTable.getItems().addAll(allClients);
    }

    /**
     * Gestisce l'azione di ricerca quando viene digitato un testo nella casella di ricerca.
     *
     * @param event L'evento KeyEvent associato all'azione di ricerca.
     */
    @FXML
    private void handleSearchAction(KeyEvent event) {
        String searchTerm = searchTextField.getText().trim();
        searchResultsTable.getItems().clear(); // Rimuove i risultati precedenti

        if (!searchTerm.isEmpty()) {

            for (Client client : allClients) {
                if (client.getNome().toLowerCase().contains(searchTerm.toLowerCase())) {
                    // Se il nome del cliente contiene il termine di ricerca, aggiungi il cliente alla tabella dei risultati
                    searchResultsTable.getItems().add(client);
                }
            }
        } else {
            searchResultsTable.getItems().addAll(allClients);
        }
    }

    /**
     * Gestisce l'azione del pulsante per la selezione di un cliente.
     *
     * @param actionEvent L'evento di azione associato alla pressione del pulsante.
     */
    @FXML
    public void SelectClientButton(ActionEvent actionEvent) throws IOException {
        // Ottieni il cliente selezionato dalla tabella
        Client selectedClient = searchResultsTable.getSelectionModel().getSelectedItem();

        // Verifica se un cliente è stato effettivamente selezionato
        if (selectedClient != null) {
            // Memorizza il cliente selezionato per l'utilizzo nella finestra successiva (02_NewCar_2)
            NewCarController.selectedClient = selectedClient;


            // Chiude la finestra attuale (02_NewCar_1)
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            //imposta la finestra (02_NewCar_2) come visualizzata
            setFirstWindowVisible(false);

            // Apre la finestra successiva (02_NewCar_2)
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("02_NewCar_2.fxml"));

            //Aggiungiamo il root per le animazioni
            Parent root = null;
            Scene scene = new Scene(root = fxmlLoader.load(), 500, 550);
            scene.setFill(Color.TRANSPARENT);
            stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } else {
            // Se nessun cliente è stato selezionato
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Nessun Cliente selezionato");
            alert.setContentText("Per selezionare cliccare sulla riga della tabella corrispondente al cliente");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }

    }

    /**
     * Gestisce l'azione del pulsante per la chiusura della finestra.
     *
     * @param actionEvent L'evento di azione associato alla pressione del pulsante.
     */
    @FXML
    public void DeleteButton(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    /**
     * Gestisce l'azione del pulsante per l'aggiunta di una vettura.
     *
     * @param actionEvent L'evento di azione associato alla pressione del pulsante.
     */
    @FXML
    public void AddCarButton(ActionEvent actionEvent) {
        String marcaVeicolo = marcaTextField.getText().strip();
        String modelloVeicolo = modelloTextField.getText().strip();
        String targaVeicolo = targaTextField.getText().strip();
        String codTelaioVeicolo = telaioTextField.getText().strip();
        String diagnosiVeicolo = diagnosiTextArea.getText().strip();
        String statoVeicolo = statoRiparazioneTextField.getText().strip();

        //controllo campi vuoti
        if (marcaVeicolo.isEmpty() | modelloVeicolo.isEmpty() | targaVeicolo.isEmpty() | codTelaioVeicolo.isEmpty() | diagnosiVeicolo.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Compilare tutti i campi");
            alert.setContentText("Sono presenti alcuni campi non compilati");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }

        else {

            boolean isInserted = DbOperations.insertCar(selectedClient, marcaVeicolo, modelloVeicolo, targaVeicolo, codTelaioVeicolo, diagnosiVeicolo, statoVeicolo);

            if(isInserted){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText("Veicolo inserito correttamente");
                alert.setContentText("Il veicolo associato al cliente: " + selectedClient.getNome() + " " + selectedClient.getCognome() + ", è stato inserito");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();

                // Dopo aver aggiunto il veicolo, chiude la finestra corrente
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

                setFirstWindowVisible(true);

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRORE");
                alert.setHeaderText("Errore");
                alert.setContentText("C'è stato un errore nell'inserimento del Veicolo. Verifica che non sia già stato inserito.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();
            }
        }
    }

    /**
     * Gestisce l'azione del pulsante per visualizzare la finestra precedente.
     *
     * @param actionEvent L'evento di azione associato alla pressione del pulsante.
     */
    @FXML
    public void goBackButton(ActionEvent actionEvent) throws IOException {
        // Chiude la finestra attuale (02_NewCar_2)
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        //imposta la finestra (02_NewCar_1) come visualizzata
        setFirstWindowVisible(true);

        // Apre la finestra precedente (02_NewCar_1)
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("02_NewCar_1.fxml"));

        //Aggiungiamo il root per le animazioni
        Parent root = null;
        Scene scene = new Scene(root = fxmlLoader.load(), 500, 550);
        scene.setFill(Color.TRANSPARENT);
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    /**
     * Imposta lo stato della finestra 02_NewCar_1.fxml. Sè è visibile il suo valore è true
     *
     * @param visible true se la finestra 02_NewCar_1.fxml è visibile, false altrimenti
     */
    public void setFirstWindowVisible(boolean visible) {
        isFirstWindowVisible = visible;
    }
}
