package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.entity.Car;
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

/**
 * Controller per la finestra 01_Garage.fxml, che gestisce la visualizzazione dei veicoli presenti nel garage.
 */
public class GarageController implements Initializable {

    @FXML
    private TableView<Car> tableView;

    @FXML
    private TableColumn<Car, String> statoRiparazione;

    @FXML
    private TableColumn<Car, String> targa;

    @FXML
    private TableColumn<Car, String> NumTelaio;

    @FXML
    private TableColumn<Car, String> marcaVettura;

    @FXML
    private TableColumn<Car, String> modelloVettura;

    @FXML
    private TableColumn<Car, String> diagnosi;

    @FXML
    private TableColumn<Car, String> idDipendente;


    /**
     * Metodo chiamato durante l'inizializzazione del controller.
     *
     * @param url            L'URL di inizializzazione.
     * @param resourceBundle Il ResourceBundle specifico della lingua.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        loadAllCars();
    }

    /**
     * Inizializza la struttura della tabella dei veicoli.
     */
    private void initializeTable() {

        statoRiparazione.setCellValueFactory(new PropertyValueFactory<>("statoRiparazione"));
        targa.setCellValueFactory(new PropertyValueFactory<>("targa"));
        NumTelaio.setCellValueFactory(new PropertyValueFactory<>("telaio"));
        marcaVettura.setCellValueFactory(new PropertyValueFactory<>("marcaVettura"));
        modelloVettura.setCellValueFactory(new PropertyValueFactory<>("modello"));
        diagnosi.setCellValueFactory(new PropertyValueFactory<>("diagnosi"));
        idDipendente.setCellValueFactory(new PropertyValueFactory<>("idDipendente"));
    }

    /**
     * Carica tutti i veicoli dal database e li visualizza nella tabella.
     */
    public void loadAllCars() {
        List<Car> allCars = DbOperations.getAllCars();

        // Rimuove i risultati precedenti e inserisce le vetture appena recuperate
        tableView.getItems().clear();
        tableView.getItems().addAll(allCars);
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
     * Gestisce l'evento di clic sul pulsante "Aggiungi vettura", che apre il menu guidato (composto da due finestre) per l'aggiunta di una nuova vettura.
     *
     * @param actionEvent L'evento di azione che ha scatenato la chiamata a questo metodo.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML per la nuova finestra.
     */
    public void addCar(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("02_NewCar_1.fxml"));

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
}
