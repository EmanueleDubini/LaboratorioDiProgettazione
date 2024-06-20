package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.entity.Part;
import it.bicocca.laboratoriodiprogettazione_java.entity.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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

import static it.bicocca.laboratoriodiprogettazione_java.controller.LoginController.ruolo;

public class PartsListController implements Initializable {

    @FXML
    private TableView<Part> tableView;

    @FXML
    private TableColumn<Part, String> id;

    @FXML
    private TableColumn<Part, String> name;

    @FXML
    private TableColumn<Part, String> brand;

    @FXML
    private TableColumn<Part, String> price;

    @FXML
    private TableColumn<Part, Integer> quantity;

    static PartsListController istance;


    /**
     * Metodo chiamato durante l'inizializzazione del controller.
     *
     * @param url            L'URL di inizializzazione.
     * @param resourceBundle Il ResourceBundle specifico della lingua.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        istance = this;

        initializeTable();
        loadAllParts();
    }

    /**
     * Inizializza la struttura della tabella dei ricambi.
     */
    public void initializeTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    /**
     * Carica tutti i ricambi dal database e li visualizza nella tabella.
     */
    public void loadAllParts() {
        List<Part> allParts = DbOperations.getAllParts();

        // Rimuove i risultati precedenti e inserisce i ricambi appena recuperati
        tableView.getItems().clear();
        tableView.getItems().addAll(allParts);

        //Aggiungiamo un listener alla tabella per recuperare i dati sulla riga (quando viene cliccata 2 volte)
        tableView.setRowFactory(tv -> {
            TableRow<Part> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Part selectedPart = row.getItem();
                    // Apri una nuova finestra con i dettagli del dipendente selezionato
                    openDetailsWindow(selectedPart);
                }
            });
            return row;
        });

    }

    /**
     * Apre una nuova finestra per visualizzare i dettagli del ricambio selezionato.
     *
     * @param selectedPart Il ricambio di cui visualizzare i dettagli.
     */
    private void openDetailsWindow(Part selectedPart) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("02_DeletePart.fxml"));
            Parent root = fxmlLoader.load(); // Carica il file FXML e inizializza il controller

            // Passa il dipendente selezionato al controller della nuova finestra
            DeletePartController controller = fxmlLoader.getController();
            controller.partDetails(selectedPart);

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
     * Gestisce l'evento di clic sul pulsante "Aggiunta ricambio", che apre il menu per l'aggiunta di un nuovo ricambio.
     *
     * @param actionEvent L'evento di azione che ha scatenato la chiamata a questo metodo.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML per la nuova finestra.
     */
    public void addPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("02_NewPart.fxml"));

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

    public static PartsListController getIstance() {
        return istance;
    }

    /**
     * Chiude l'applicazione.
     *
     * @param mouseEvent L'evento di click che ha innescato il metodo.
     */
    public void CloseApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    /**
     * Minimizza l'applicazione nella barra delle applicazioni.
     *
     * @param mouseEvent L'evento di click che ha innescato il metodo.
     */
    public void MinimizeApplication(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
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
}
