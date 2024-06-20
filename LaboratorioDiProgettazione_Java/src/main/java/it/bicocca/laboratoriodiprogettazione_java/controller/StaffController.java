package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.GroupeRMain;
import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.entity.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static it.bicocca.laboratoriodiprogettazione_java.controller.LoginController.ruolo;

/**
 * Classe controller per la gestione delle interazioni dell'interfaccia utente relative al personale all'interno dell'applicazione.
 * Questa classe è responsabile dell'inizializzazione dei componenti dell'interfaccia utente legati alla gestione del personale,
 * della gestione delle azioni dell'utente e dell'aggiornamento dinamico dell'interfaccia utente in base ai cambiamenti nei dati del personale.
 * Implementa l'interfaccia {@link Initializable} per eseguire compiti di inizializzazione quando la vista associata viene caricata.
 */
public class StaffController implements Initializable {
    /////////////Staff FXML References/////////////
    @FXML
    public AnchorPane apane;

    @FXML
    public BorderPane bpane;

    @FXML
    private TableView<Staff> tableView;

    @FXML
    private TableColumn<Staff, String> colonnaId;

    @FXML
    private TableColumn<Staff, String> colonnaNome;

    @FXML
    private TableColumn<Staff, String> colonnaCognome;

    @FXML
    private TableColumn<Staff, String> colonnaEmail;

    @FXML
    private TableColumn<Staff, String> colonnaRuolo;

    //Richiamiamo dal file FXML i bottoni laterali
    @FXML
    private Button buttonLeft_Home;

    @FXML
    private Button buttonLeft_PrevGomme;

    @FXML
    private Button buttonLeft_Officina;

    @FXML
    private Button buttonLeft_Impo;

    @FXML
    private Label labelTitle;


    ObservableList <Staff> staffList = FXCollections.observableArrayList();

    private static StaffController instance;

    /**
     * Inizializza i componenti dell'interfaccia utente per la gestione del personale, includendo
     * l'impostazione dei legami dati per la tabella del personale e preparando la tabella per
     * mostrare l'elenco dei membri del personale. Nasconde le sezioni non dedicate dell'interfaccia
     * in base al ruolo dell'utente autenticato.
     *
     * @param url l'URL della risorsa FXML associata a questo controller, può essere null.
     * @param resourceBundle il bundle di risorse che contiene oggetti specifici per la localizzazione, può essere null.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        instance = this;

        /////////Table StaffList FXML/////////
        colonnaId.setCellValueFactory(new PropertyValueFactory<>("id_dipendente"));
        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colonnaEmail.setCellValueFactory(new PropertyValueFactory<>("e_mail"));
        colonnaRuolo.setCellValueFactory(new PropertyValueFactory<>("ruolo"));

        reloadTable();

        //Implementiamo uno switch case, se necessario, per nascondere le sezioni non dedicate
        switch (LoginController.ruolo) {
            case "Capo officina":
                buttonLeft_PrevGomme.setDisable(true);
                labelTitle.setText("officina");
                labelTitle.setStyle("-fx-font-weight: bold;");
                break;
            case "Capo magazzino":
                buttonLeft_Officina.setDisable(true);
                labelTitle.setText("magazzino");
                labelTitle.setStyle("-fx-font-weight: bold;");
                break;
            default:
                labelTitle.setText("filiale");
                labelTitle.setStyle("-fx-font-weight: bold;");
                break;
        }
    }

    /**
     * Aggiorna la vista tabella con l'elenco più attuale dei membri del personale dal database.
     * Se l'utente autenticato è un Capo officina, la tabella mostrerà solo i dipendenti con ruolo "Officina".
     * Se l'utente autenticato è un Capo magazzino, la tabella mostrerà solo i dipendenti con ruolo "Magazzino".
     * Altrimenti, la tabella mostrerà tutti i dipendenti.
     *
     */
    public void reloadTable() {
        //Viene pulita la lista esistente
        staffList.clear();

        //Recuperiamo tutti i dipendenti dal DB:
        List<Staff> allStaff  = getDipendenti();

        //Creiamo una nuova lista per i dipendenti filtrati
        List<Staff> filteredStaff = new ArrayList<>();

        //Verifichiamo il ruolo dell'utente loggato
        switch (LoginController.ruolo) {
            case "Capo officina":
                // Filtra i dipendenti con ruolo "Officina"
                for (Staff staff : allStaff) {
                    if (staff.getRuolo().equals("Officina")) {
                        filteredStaff.add(staff);
                    }
                }
                break;
            case "Capo magazzino":
                // Filtra i dipendenti con ruolo "Officina"
                for (Staff staff : allStaff) {
                    if (staff.getRuolo().equals("Magazzino")) {
                        filteredStaff.add(staff);
                    }
                }
                break;
            default:
                //Se il ruolo non corrisponde a nessun caso, aggiungi tutti i dipendenti
                filteredStaff.addAll(allStaff);
                break;
        }

        staffList.addAll(filteredStaff);
        tableView.setItems(staffList);

        //Aggiungiamo un listener alla tabella per recuperare i dati sulla riga (quando viene cliccata 2 volte)
        tableView.setRowFactory(tv -> {
            TableRow<Staff> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Staff selectedStaff = row.getItem();
                    // Apri una nuova finestra con i dettagli del dipendente selezionato
                    openDetailsWindow(selectedStaff);
                }
            });
            return row;
        });
    }

    /**
     * Gestisce la navigazione di ritorno alla vista home all'interno dell'applicazione.
     */
    public void toHome() {
        bpane.setCenter(apane);
    }

    /**
     * Naviga alla vista di gestione del magazzino all'interno dell'applicazione.
     *
     * @throws IOException se si verifica un errore durante il caricamento della pagina.
     */
    public void toMagazzino() throws IOException {
        loadPage("01_Warehouse");
    }

    /**
     * Naviga alla vista di gestione dell'officina all'interno dell'applicazione.
     *
     * @throws IOException se si verifica un errore durante il caricamento della pagina.
     */
    public void toOfficina() throws IOException {
        loadPage("01_Garage");
    }

    /**
     * Naviga alla vista delle impostazioni all'interno dell'applicazione.
     *
     * @throws IOException se si verifica un errore durante il caricamento della pagina.
     */
    public void toInfo() throws IOException {
        loadPage("01_Impostazioni");
    }

    /**
     * Naviga alla vista della lista degli utenti all'interno dell'applicazione.
     *
     * @throws IOException se si verifica un errore durante il caricamento della pagina.
     */
    public void toClients() throws IOException {
        loadPage("01_Clients");
    }

    /**
     * Carica una specifica pagina all'interno della sezione di gestione del personale in base all'identificatore di pagina dato.
     *
     * @param page l'identificatore della pagina per la vista target da caricare.
     * @throws IOException se si verifica un errore durante il caricamento della pagina.
     */
    public void loadPage(String page) throws IOException {
        Parent root = null;
        try {
            root = FXMLLoader.load(StaffController.class.getResource(page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bpane.setCenter(root);
    }

    /**
     * Recupera una lista osservabile di membri del personale dal database.
     *
     * @return una lista osservabile di oggetti {@link Staff}.
     */
    private ObservableList<Staff> getDipendenti() {
        ObservableList<Staff> dipendenti = DbOperations.getAllStaff();
        return dipendenti;
    }

    /**
     * Gestisce l'evento di clic sul pulsante "Aggiungi Dipendente", che apre una nuova finestra per inserire un nuovo dipendente.
     *
     * @param actionEvent L'evento di azione che ha scatenato la chiamata a questo metodo.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML per la nuova finestra.
     */
    public void addStaff(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("02_NewStaff.fxml"));
        /*
         * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
         */
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
     * Apre una nuova finestra per visualizzare i dettagli del dipendente selezionato.
     *
     * @param selectedStaff Il dipendente di cui visualizzare i dettagli.
     */
    private void openDetailsWindow(Staff selectedStaff) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("02_EditStaff.fxml"));
            Parent root = fxmlLoader.load(); // Carica il file FXML e inizializza il controller

            // Passa il dipendente selezionato al controller della nuova finestra
            EditStaffController controller = fxmlLoader.getController();
            controller.staffDetails(selectedStaff);

            Scene scene = new Scene(root, 500, 550);
            scene.setFill(Color.TRANSPARENT);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Gestisce il logout dell'utente e la chiusura della finestra corrente.
     *
     * Questo metodo chiude la finestra corrente, resetta il ruolo dell'utente, e apre la finestra di login.
     *
     * @param actionEvent L'evento che ha scatenato il logout.
     * @throws IOException Se si verifica un errore durante il caricamento della finestra di login.
     */
    public void logout(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Confermi di volere effettuare il logout?");
        alert.setContentText("Verrai reindirizzato alla pagina di login.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");

        ButtonType clearButtonType = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(clearButtonType);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Stage currentStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

            ruolo = null;

            Stage stage = new Stage();

            GroupeRMain.setRoot("00_Login");

            stage.initStyle(StageStyle.TRANSPARENT);
            GroupeRMain.scene.setFill(Color.TRANSPARENT);
            stage.setScene(GroupeRMain.scene);
            GroupeRMain.scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.show();
        }
    }

    /**
     * Restituisce l'istanza singleton del controller della pagina principale.
     *
     * Questo metodo fornisce l'accesso all'istanza unica di `StaffController` utilizzata
     * per gestire la pagina principale dell'applicazione.
     * Assicura che vi sia una sola istanza di questo controller in tutta l'applicazione (pattern singleton).
     *
     * @return l'istanza singleton di `StaffController`
     */
    public static StaffController getInstance() {
        return instance;
    }


}
