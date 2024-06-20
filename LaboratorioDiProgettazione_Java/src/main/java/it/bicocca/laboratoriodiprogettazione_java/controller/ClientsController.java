package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.entity.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;



public class ClientsController implements Initializable {

    @FXML
    private TableView<Client> tableView;

    @FXML
    private TableColumn<Client, String> id;

    @FXML
    private TableColumn<Client, String> name;

    @FXML
    private TableColumn<Client, String> surname;

    @FXML
    private TableColumn<Client, String> email;

    @FXML
    private TableColumn<Client, String> telephone;

    /**
     * Metodo chiamato durante l'inizializzazione del controller.
     *
     * @param url            L'URL di inizializzazione.
     * @param resourceBundle Il ResourceBundle specifico della lingua.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        loadAllClients();
    }

    /**
     * Inizializza la struttura della tabella dei clienti.
     */
    private void initializeTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("nome"));
        surname.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    /**
     * Gestisce l'evento di clic sul pulsante "Aggiunta cliente", che apre il menu per l'aggiunta di un nuovo cliente.
     *
     * @param actionEvent L'evento di azione che ha scatenato la chiamata a questo metodo.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML per la nuova finestra.
     */
    public void addClient(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("02_NewClient.fxml"));

        //Aggiungiamo il root per le animazioni
        Parent root = null;
        Scene scene = new Scene(root = fxmlLoader.load(), 500, 550);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Carica tutti i clienti dal database e li visualizza nella tabella.
     */
    public void loadAllClients() {
        List<Client> allClient = DbOperations.getAllClients();

        // Rimuove i risultati precedenti e inserisce i clienti appena recuperati
        tableView.getItems().clear();
        tableView.getItems().addAll(allClient);
    }

    /**
     * Chiude l'applicazione. Gestisce l'evento scatenato da un click del mouse.
     *
     * @param mouseEvent L'evento del mouse che ha scatenato la chiamata a questo metodo.
     */
    public void CloseApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    /**
     * Minimizza l'applicazione. Gestisce l'evento scatenato da un click del mouse.
     *
     * @param mouseEvent L'evento del mouse che ha scatenato la chiamata a questo metodo.
     */
    public void MinimizeApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
