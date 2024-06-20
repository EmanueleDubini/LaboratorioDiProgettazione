package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.entity.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AddNewClientController implements Initializable {

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Client> searchResultsTable;

    private List<Client> allClients;

    public static Client selectedClient;

    /**
     * Inizializza i componenti dell'interfaccia utente e carica tutti i clienti nella tabella.
     * Questo metodo viene invocato automaticamente dopo che il file FXML è stato caricato e l'inizializzazione dei componenti è stata completata.
     *
     * @param url l'URL di localizzazione utilizzato per risolvere i percorsi relativi per l'oggetto radice, o null se la localizzazione non è nota.
     * @param resourceBundle le risorse utilizzate per localizzare l'oggetto radice, o null se l'oggetto radice non è stato localizzato.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        loadAllClients();
    }

    /**
     * Metodo per gestire l'azione di cancellazione di un elemento (pneumatico o cerchio) dal preventivo.
     * @param actionEvent L'evento che scatena l'azione.
     */
    public void DeleteButton(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    /**
     * Inizializza la struttura della tabella dei risultati di ricerca.
     */
    private void initializeTable() {
        TableColumn<Client, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Client, String> cognomeColumn = new TableColumn<>("Cognome");
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));

        TableColumn<Client, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));

        TableColumn<Client, String> telephoneColumn = new TableColumn<>("Telefono");
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telefono"));

        searchResultsTable.getColumns().addAll(nomeColumn, cognomeColumn, emailColumn, telephoneColumn);
    }

    /**
     * Carica tutti i clienti dal database e li visualizza nella tabella.
     */
    private void loadAllClients() {
        this.allClients = DbOperations.getAllClients();
        searchResultsTable.getItems().addAll(allClients);
    }

    /**
     * Metodo per gestire la selezione del cliente dal database.
     * @param actionEvent L'evento che scatena l'azione.
     */
    @FXML
    public void SelectClientButton(ActionEvent actionEvent) {
        selectedClient = searchResultsTable.getSelectionModel().getSelectedItem();

        // Verifica se un cliente è stato effettivamente selezionato
        if (selectedClient != null) {

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            updateNameSurnameLabel();

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
     * Gestisce l'evento di ricerca dei clienti all'interno della tabella.
     * Quando viene premuto un tasto nella casella di ricerca, questo metodo viene chiamato per filtrare
     * i clienti in base al termine di ricerca inserito dall'utente.
     *
     * @param keyEvent L'evento che ha scatenato la ricerca, tipicamente il premere di un tasto nella casella di ricerca.
     */
    public void handleSearchAction(KeyEvent keyEvent) {
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
     * Aggiorna i valori delle label del nome e del cognome del cliente nella prima finestra.
     * Questo metodo recupera l'istanza del controller della prima finestra e aggiorna le label
     * del nome e del cognome del cliente con i valori del cliente selezionato.
     * Se una o entrambe le label sono null o se il cliente selezionato è null, le label non vengono aggiornate.
     */
    public void updateNameSurnameLabel() {

        PrevTiresController prevTiresController = PrevTiresController.getInstance();

        if (prevTiresController.clientNameLabel != null && prevTiresController.clientSurnameLabel != null && selectedClient!= null){
            prevTiresController.clientNameLabel.setText(selectedClient.getNome());
            prevTiresController.clientSurnameLabel.setText(selectedClient.getCognome());
        } else {
            System.out.println(prevTiresController.clientNameLabel);
        }
    }


}
