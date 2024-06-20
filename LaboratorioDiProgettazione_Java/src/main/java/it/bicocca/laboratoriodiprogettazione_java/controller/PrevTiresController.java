package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.entity.Client;
import it.bicocca.laboratoriodiprogettazione_java.common.PdfCreator;
import it.bicocca.laboratoriodiprogettazione_java.entity.Rim;
import it.bicocca.laboratoriodiprogettazione_java.entity.Tire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.bicocca.laboratoriodiprogettazione_java.controller.LoginController.ruolo;

/**
 * Il controller {@code PrevTiresController} gestisce la logica dietro la vista del preventivatore di pneumatici e cerchi.
 * Questa classe implementa {@link Initializable} per inizializzare l'interfaccia e gestire le interazioni dell'utente
 * come l'aggiunta di pneumatici e cerchi, la selezione dei clienti e la generazione di preventivi.
 * La classe gestisce sia la sezione pneumatici che cerchi, consentendo all'utente di inserire dettagli specifici
 * per ciascun articolo, oltre a gestire i dati del cliente e la creazione del preventivo finale. Gli elementi dell'interfaccia
 * sono dinamicamente aggiornati in base alle interazioni dell'utente.
 * È responsabile della gestione dei dati inseriti dall'utente, come la marca, la dimensione e il prezzo degli articoli,
 * oltre a mantenere un elenco di questi per la generazione del preventivo. Supporta anche la funzionalità di ricerca e selezione
 * del cliente dal database.
 */
public class PrevTiresController implements Initializable {

    ///////////////////////////////////////// --> PNEUMATICI <-- ////////////////////////////////////////////////////

    @FXML
    ScrollPane scrollPneum = new ScrollPane(gridTire);

    @FXML
    private TextField TextFieldMontaggioEBilanciatura;

    String clientId, clientName, clientSurname, clientTel, clientEmail;


    @FXML
    private CheckBox checkBoxContiSeparati;

    static int iTire = 0;

    //Inizializziamo una variabile che consente di tenere traccia del numero massimo di pneumatici che possono essere inseriti
    //Deve essere una variabile statica perchè si dovrà aggiornare nel momento in cui verranno rimossi e/o aggionti nuovi pneumatici
    //Modificando il parametro è inoltre possibile cambiare il numero massimo di pneumatici che possono essere all'interno del preventivo

    static int maxTires = 4;
    static int columnTire = 0;
    static int rowTire = 0;
    static GridPane gridTire = new GridPane();


    //Creo un hashMap di pneumatici legati alla propria anchorPane
    static HashMap<AnchorPane, Tire> mapTire = new HashMap<>();
    //Creo un hashMap di ItemController legati alla propria anchorPane
    static HashMap<AnchorPane, ItemTireController> mapTireItem = new HashMap<>();

    ArrayList<Tire> listTire = new ArrayList<>();

    String assemblyAndBalancing;

    Boolean separateCount = false;

    ///////////////////////////////////////// --> CERCHI <-- ////////////////////////////////////////////////////
    static GridPane gridRim = new GridPane();

    @FXML
    private Label label21;

    @FXML
    private Button buttonAddCerchi;

    static int iRim = 0;
    static int maxRims = 4;
    static int columnRims = 0;
    static int rowRims = 0;

    //Creo un hashMap di cerchi legati alla propria anchorPane
    static HashMap<AnchorPane, Rim> mapRims = new HashMap<>();
    //Creo un hashMap di ItemController legati alla propria anchorPane
    static HashMap<AnchorPane, ItemRimController> mapRimsItem = new HashMap<>();

    ArrayList<Rim> listRim = new ArrayList<>();

    @FXML
    ScrollPane scrollCerchi = new ScrollPane(gridRim);

    private static PrevTiresController instance;

    @FXML
    public Label clientNameLabel;

    @FXML
    public Label clientSurnameLabel;

    /**
     * Inizializza i componenti dell'interfaccia utente e configura lo stato iniziale della vista.
     * Questo metodo viene invocato automaticamente dopo che il file FXML è stato caricato.
     * @param location L'URL di localizzazione utilizzato per risolvere i percorsi relativi per l'oggetto radice, o null se la localizzazione non è nota.
     * @param resources Le risorse utilizzate per localizzare l'oggetto radice, o null se l'oggetto radice non è stato localizzato.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        instance = this;

        scrollPneum.setContent(gridTire);


        scrollCerchi.setContent(gridRim);
    }

    /**
     * Gestisce l'azione del pulsante per l'aggiunta di un nuovo cliente.
     * Apre una nuova finestra per l'inserimento dei dati del cliente. Questa finestra è definita nel file FXML "02_AddClient.fxml".
     * Il metodo tenta di caricare il layout della nuova finestra dal file FXML specificato.
     * In caso di successo, viene visualizzata la finestra "Aggiungi Cliente" dove l'utente può inserire
     * i dettagli del nuovo cliente. È possibile passare dati al controller della nuova finestra, se necessario,
     * per pre-popolare alcuni campi o passare informazioni specifiche.
     *
     * @param event L'evento di azione che ha scatenato questo metodo, tipicamente il click su un pulsante.
     */
    public void handleAddClientButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("02_AddClient.fxml"));

            //Aggiungiamo il root per le animazioni
            Parent root = null;
            Scene scene = new Scene(root = fxmlLoader.load(), 500, 550);
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
     * Gestisce l'azione del pulsante per salvare il preventivo in formato PDF.
     * Verifica se è stato selezionato un cliente; in caso contrario, mostra un messaggio di errore.
     * Se un cliente è stato selezionato, procede alla creazione del preventivo includendo i dati dei pneumatici
     * e dei cerchi selezionati, nonché le informazioni del cliente.
     *
     * Genera un file PDF contenente il preventivo e lo apre automaticamente dopo la creazione.
     * Mostra anche un messaggio di conferma dell'avvenuta creazione del file PDF.
     *
     * Il metodo assicura che tutti i dati necessari siano presenti e correttamente formati prima
     * di procedere con la creazione del preventivo. Gestisce anche la selezione dell'opzione per i conti separati,
     * influenzando così il formato del preventivo generato.
     *
     * @param actionEvent L'evento di azione che ha scatenato questo metodo, tipicamente il click su un pulsante.
     * @throws IOException Se si verifica un errore durante la generazione o l'apertura del file PDF.
     */
    public void SaveFileButton(ActionEvent actionEvent) throws IOException {

        if (AddNewClientController.selectedClient == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Dati Cliente mancanti!");
            alert.setContentText("Per procedere selezionare prima un cliente.");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }else{
            clientId = AddNewClientController.selectedClient.getId();
            clientName = AddNewClientController.selectedClient.getNome();
            clientSurname = AddNewClientController.selectedClient.getCognome();
            clientEmail = AddNewClientController.selectedClient.getEmail();
            clientTel = AddNewClientController.selectedClient.getTelefono();


            //il metodo va bene ma a volte li inserisce in ordine sparso
            for(Tire tire: mapTire.values()){
                System.out.println(tire);
                listTire.add(tire);
            }

            //il metodo va bene ma a volte li inserisce in ordine sparso
            for(Rim rim : mapRims.values()){
                System.out.println(rim);
                listRim.add(rim);
            }

            //Creiamo l'oggetto client
            Client client = new Client(clientId, clientName, clientSurname, clientEmail, clientTel);

            assemblyAndBalancing = TextFieldMontaggioEBilanciatura.getText();
            assemblyAndBalancing = assemblyAndBalancing.replaceAll(",", "\\.");

            if(checkBoxContiSeparati.isSelected()){
                separateCount = true;
                System.out.println("Conti separati: " + separateCount);
            }else{
                separateCount = false;
            }

            PdfCreator pdfCreator = new PdfCreator(client, listTire, listRim, assemblyAndBalancing, separateCount);

            // percorso del preventivo in pdf
            String namePdfCreated = null;
            try {
                namePdfCreated = pdfCreator.writeOnPdf();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Operazione eseguita correttamente");
            alert.setContentText("Il file " + clientName + "_" + clientSurname + ".pdf" + " è stato creato");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();

            Desktop.getDesktop().open(new File(namePdfCreated));

            // creazione della data attuale
            LocalDate creationDate = LocalDate.now();
            Date date = Date.valueOf(creationDate);

            String department = LoginController.ruolo;


             String totalPriceS = pdfCreator.prezzoTotale;

            //DEBUG: System.out.println("!!!!!!!!!! Valore totale prezzo pre modifche: " + totalPriceS);

            totalPriceS = totalPriceS.replaceAll("€", "");
            totalPriceS = totalPriceS.replaceAll(",", ".");
            totalPriceS = totalPriceS.replaceAll(" ","");
            totalPriceS = totalPriceS.substring(0, totalPriceS.length() -1);

             double totalPrice = Double.parseDouble(totalPriceS);

             //DEBUG: System.out.println("!!!!!!!!!! Valore totale prezzo da scrivere su db: " + totalPriceS);

            boolean success = DbOperations.insertPDF(Integer.parseInt(client.getId()), namePdfCreated, date, department, totalPrice);

            if (success) {
                System.out.println("File PDF inserito con successo nel database.");
            } else {
                System.out.println("Errore nell'inserimento del file PDF.");
            }
        }
    }

    /**
     * Metodo per aggiungere un nuovo pneumatico al preventivo.
     * @param actionEvent L'evento che scatena l'azione.
     */
    public void AddTireButton(ActionEvent actionEvent)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("02_Pneumatici.fxml"));

            //Aggiungiamo il root per le animazioni
            Parent root = null;
            Scene scene = new Scene(root = fxmlLoader.load(), 500, 550);
            scene.setFill(Color.TRANSPARENT);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Inserisci nuovo pneumatico");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    /**
     * Aggiunge un nuovo pneumatico alla griglia di visualizzazione all'interno dell'interfaccia utente.
     * Carica l'elemento dell'interfaccia utente per il pneumatico da un file FXML e lo configura con i dati specificati.
     * Associa ciascun pneumatico a un {@link AnchorPane} univoco per facilitare la gestione futura,
     * come la rimozione o la modifica delle informazioni del pneumatico.
     *Il metodo aggiorna dinamicamente la griglia di visualizzazione aggiungendo l'{@link AnchorPane} configurato
     * e assicurando che sia correttamente visualizzato all'interno dell'interfaccia utente.
     *
     * @param tireName Il nome del pneumatico da visualizzare.
     * @param tire L'oggetto {@link Tire} contenente i dati del pneumatico da aggiungere.
     * @return {@code true}, indicando che l'aggiunta è stata completata.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML per l'elemento dell'interfaccia utente.
     */
    public static boolean AddTire(String tireName, Tire tire) throws IOException {

        //Carichaimo il file FXML per la creazione dell'anchorPane
        FXMLLoader fxmlLoader = new FXMLLoader(PrevTiresController.class.getResource("itemPneum.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();

        //Creiamo un nome univoco per le Varie AnchorPane che vengono inserite nella griglia. Questo ci consente di identificarle successivamente per la rimozione/modifica
        String nomeAnchorpane = "anchorpane" + iTire;
        //Settiamo dinamicamente il nome dell'anchorPane
        anchorPane.setId(nomeAnchorpane);

        ItemTireController itemTireController = fxmlLoader.getController();
        itemTireController.setData(tireName);

        //Colleghiamo l'anchorPane appena creata allo tire che essa rappresenta
        mapTire.put(anchorPane, tire);
        //Colleghiamo l'anchorPane appena creata con l'ItemController per poter poi modificare il nome
        mapTireItem.put(anchorPane, itemTireController);

        //Settiamo il nome da visualizzare nell'AnchorPane all'interno della griglia
        itemTireController.setName(tireName);

        //Aggiunge l'anchor pane alla griglia di visualizzazione sulla schermata
        gridTire.add(anchorPane, columnTire, rowTire);
        //Aggiungo una colonna per inserire il prossimo tire
        columnTire++;

        return true;
    }

    /**
     * Metodo per avviare una nuova sessione di preventivo, resettando tutti i campi e le selezioni.
     * @param actionEvent L'evento che scatena l'azione.
     */
    public void NewPrevButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Sei sicuro?");
        alert.setContentText("Questa operazione resetta tutti i campi");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");

        ButtonType clearButtonType = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(clearButtonType);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {

            //Reset pneumatici
            gridTire.getChildren().clear();
            columnTire = 0;
            rowTire = 0;

            mapTire.clear();
            mapTireItem.clear();
            listTire.clear();

            maxTires = 4;
            iTire = 0;

            //Reset cerchi
            gridRim.getChildren().clear();
            columnRims = 0;
            rowRims = 0;

            mapRims.clear();
            mapRimsItem.clear();
            listRim.clear();

            maxRims = 4;
            iRim = 0;

            AddNewClientController.selectedClient = null;

            clientNameLabel.setText("Nessun cliente");
            clientSurnameLabel.setText("selezionato");
        }
    }

    /**
     * Metodo per aggiungere un nuovo cerchio al preventivo.
     *
     * @param actionEvent L'evento che scatena l'azione.
     */
    public void AddRimButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("02_Cerchi.fxml"));

            Parent root = null;
            Scene scene = new Scene(root = fxmlLoader.load(), 500, 550);
            scene.setFill(Color.TRANSPARENT);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Inserisci nuovo cerchio");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);

        }
    }

    /**
     * Inserisce un nuovo cerchio nella griglia di visualizzazione all'interno dell'interfaccia utente.
     * Carica l'elemento dell'interfaccia utente per il cerchio da un file FXML e lo configura con i dati specificati.
     * Associa ciascun cerchio a un {@link AnchorPane} univoco per facilitare la gestione futura,
     * come la rimozione o la modifica delle informazioni del cerchio.
     *
     * @param nomeCerchio Il nome del cerchio da visualizzare.
     * @param cerchio L'oggetto {@link Rim} contenente i dati del cerchio da aggiungere.
     * @return Sempre {@code true}, indicando che l'aggiunta è stata completata.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML per l'elemento dell'interfaccia utente.
     */
    public static Boolean inserisciCerchio(String nomeCerchio, Rim cerchio) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(PrevTiresController.class.getResource("itemCerchio.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();

        //Creiamo un nome univoco per le Varie AnchorPane che vengono inserite nella griglia. Questo ci consente di identificarle successivamente per la rimozione/modifica
        String nomeAnchorpane = "anchorpane" + iRim;
        //Settiamo dinamicamente il nome dell'anchorPane
        anchorPane.setId(nomeAnchorpane);

        ItemRimController itemCerchioController = fxmlLoader.getController();
        itemCerchioController.setData(nomeCerchio);

        //Colleghiamo l'anchorPane appena creata allo pneumatico che essa rappresenta
        mapRims.put(anchorPane, cerchio);
        //Colleghiamo l'anchorPane appena creata con l'ItemController per poter poi modificare il nome
        mapRimsItem.put(anchorPane, itemCerchioController);

        //Settiamo il nome da visualizzare nell'AnchorPane all'interno della griglia
        itemCerchioController.setName(nomeCerchio);

        //Aggiunge l'anchor pane alla griglia di visualizzazione sulla schermata
        gridRim.add(anchorPane, columnRims, rowRims);
        //Aggiungo una colonna per inserire il prossimo pneumatico
        columnRims++;

        return true;
    }

    /**
     * Gestisce l'evento di selezione della casella di controllo relativa all'opzione di conti separati.
     * Questa funzionalità permette di nascondere o visualizzare gli elementi dell'interfaccia utente associati
     * all'aggiunta di cerchi, in base alla selezione dell'utente.
     *
     * @param actionEvent L'evento che scatena la chiamata di questo metodo, tipicamente il cambio di stato
     *                    della casella di controllo.
     */
    public void CheckBoxSelezionata(ActionEvent actionEvent) {
        if (checkBoxContiSeparati.isSelected()){
            scrollCerchi.setVisible(false);
            label21.setVisible(false);
            buttonAddCerchi.setVisible(false);
        }else {
            scrollCerchi.setVisible(true);
            label21.setVisible(true);
            buttonAddCerchi.setVisible(true);
        }
    }

    /**
     * Recuperiamo l'istanza del controller per aggiornare alcuni dati
     *
     * @return l'istanza del controller PrevTiersController
     */
    public static PrevTiresController getInstance() {
        return instance;
    }

    /**
     * Metodo per gestire l'azione di cancellazione di un elemento (pneumatico o cerchio) dal preventivo.
     *
     * @param actionEvent L'evento che scatena l'azione.
     */
    public void DeleteButton(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    /**
     * Gestisce l'azione di tornare all'applicazione in base al ruolo dell'utente.
     * A seconda del ruolo, caricherà la pagina appropriata nell'applicazione.
     *
     * @param mouseEvent l'evento del mouse che ha attivato questa azione
     * @throws IOException se si verifica un'eccezione di input o output durante il caricamento della pagina
     */
    public void BackApplication(MouseEvent mouseEvent) throws IOException {
        switch (ruolo) {
            case "Capo magazzino":
            case "Capo officina":
            case "Direttore di filiale":
            case "root":
                StaffController controllerStaff = StaffController.getInstance();
                controllerStaff.loadPage("01_Warehouse");
                break;
            case "Officina":
            case "Magazzino":
                MainPageController controllerMainPage = MainPageController.getInstance();
                controllerMainPage.loadPage("01_Warehouse");
                break;
            default:
                //Aggiunta la logica nel caso in cui il ruolo recuperato non sia valido.
                System.out.println("Accesso negato. Ruolo non autorizzato.");
                break;
        }
    }

    /**
     * Metodo per chiudere l'applicazione.
     * @param mouseEvent L'evento del mouse che scatena l'azione.
     */
    public void CloseApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    /**
     * Metodo per minimizzare l'applicazione.
     * @param mouseEvent L'evento del mouse che scatena l'azione.
     */
    public void MinimizeApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}