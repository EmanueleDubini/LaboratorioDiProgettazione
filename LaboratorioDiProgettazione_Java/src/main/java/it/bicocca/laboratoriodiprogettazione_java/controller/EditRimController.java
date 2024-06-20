package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.entity.Rim;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
* La classe {@code EditRimController} è responsabile della gestione delle interazioni dell'utente
* nell'interfaccia grafica di modifica dei cerchi. Questo include l'inizializzazione dell'interfaccia
* grafica con i dati del cerchio da modificare e la gestione delle operazioni di modifica, annullamento,
* ed eliminazione di un cerchio. Implementa l'interfaccia {@link Initializable} per eseguire le necessarie
* inizializzazioni sui componenti dell'interfaccia grafica.
 * Il controller permette la modifica degli attributi di un cerchio includendo marca, dimensione, prezzo e sconto.
 * Queste modifiche sono riflesse nelle strutture dati dell'applicazione e l'interfaccia grafica viene aggiornata di conseguenza.
*/

public class EditRimController implements Initializable {

    @FXML
    private Spinner<Integer> spinnerCerchioQuan = new Spinner<>();

    @FXML
    private TextField TextFieldMarcaCerchio;

    @FXML
    private TextField TextFieldDimensioneCerchio;

    @FXML
    private TextField TextFieldPrezzoCerchio;

    @FXML
    private TextField TextFieldScontoCerchio;

    String brandRim, dimensionRim, priceRim, discountRim;
    int nRim;

    AnchorPane anchorPane = ItemRimController.anchorPaneCliccato;
    Rim rimToModify = PrevTiresController.mapRims.get(anchorPane);

    /**
     * Inizializza la classe controller. Questo metodo viene chiamato automaticamente
     * dopo che il file FXML è stato caricato. Inizializza i componenti dell'interfaccia grafica
     * con i dati correnti del cerchio per la modifica.
     *
     * @param location La posizione usata per risolvere i percorsi relativi per l'oggetto radice, o null se sconosciuta.
     * @param resources Le risorse usate per localizzare l'oggetto radice, o null se non localizzato.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int nRim;

        TextFieldMarcaCerchio.setText(rimToModify.getTypeRim());

        //Creiamo lo spinner per la modifica degli pneumatici
        nRim =  Integer.parseInt(rimToModify.getNumberRim());
        SpinnerValueFactory<Integer> spinnerValueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, nRim);
        spinnerCerchioQuan.setValueFactory(spinnerValueFactory2);
        spinnerCerchioQuan.editorProperty().get().setAlignment(Pos.CENTER);

        TextFieldPrezzoCerchio.setText(rimToModify.getPriceRimEasy());
        TextFieldScontoCerchio.setText(rimToModify.getDiscountRimEasy());
    }


    /**
     * Gestisce l'azione del pulsante "Modifica Cerchio". Aggiorna i dati del cerchio con l'input dell'utente e chiude la finestra di modifica.
     *
     * @param actionEvent L'evento che ha innescato la chiamata al metodo.
     */
    public void EditRimButton(ActionEvent actionEvent)  {
        brandRim = TextFieldMarcaCerchio.getText().trim();
        dimensionRim = TextFieldDimensioneCerchio.getText().trim();
        priceRim = TextFieldPrezzoCerchio.getText().trim();
        discountRim = TextFieldScontoCerchio.getText().trim();
        nRim = spinnerCerchioQuan.getValue();

        if(brandRim.equals("")|| priceRim.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Dati Cerchio mancanti!");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }else{
            //Creiamo il nuovo pneumatico da inserire all'interno della mappa
            Rim rim  = new Rim(brandRim, nRim, priceRim, discountRim);

            //Recuperiamo l'ItemController dell'anchorPane creato per poterne cambiare il nome
            ItemRimController itemToModify = PrevTiresController.mapRimsItem.get(anchorPane);
            itemToModify.setData(brandRim);

            //Facciamo un replace dell' ItemController all'interno dell'HashMap con quello appena modificato
            PrevTiresController.mapRimsItem.replace(anchorPane, itemToModify);

            //Facciamo un replace dello pneumatico all'interno dell'HashMap con lo pneumatico modificato
            PrevTiresController.mapRims.replace(anchorPane, rim);

            //Chiudiamo la finestra
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();
        }
    }

    /**
     * Gestisce l'azione del pulsante "Annulla". Chiude semplicemente la finestra di modifica senza salvare alcuna modifica.
     *
     * @param actionEvent L'evento che ha innescato la chiamata al metodo.
     */
    public void CancelRimButton(ActionEvent actionEvent) {
        //Chiudiamo la finestra
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
    }

    /**
     * Gestisce l'azione del pulsante "Elimina Cerchio". Rimuove il cerchio dalle strutture dati dell'applicazione
     * e aggiorna l'interfaccia grafica di conseguenza.
     *
     * @param actionEvent L'evento che ha innescato la chiamata al metodo.
     */
    public void DeleteRimButton(ActionEvent actionEvent) {

        //Creiamo una finestra di dialogo per avvisare l'utente è convinto di volere rimuovere lo pneumatico dal preventivo
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Sei sicuro?");
        alert.setContentText("Questa operazione eliminerà il cerchio in modo definitivo");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/org/example/css/style.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");

        ButtonType clearButtonType = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(clearButtonType);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            //Rimuoviamo l'anchorPane relativa allo pneumatico che stiamo inserendo
            PrevTiresController.gridRim.getChildren().remove(anchorPane);

            //Rimuoviamo all'interno dell'hashMap lo pneumatico corrispondente
            PrevTiresController.mapRims.remove(anchorPane, rimToModify);

            //Rimuoviamo all'interno dell'hashmap, l'anchorPane corrispondente e l'itemController
            ItemRimController itemToModify = PrevTiresController.mapRimsItem.get(anchorPane);
            PrevTiresController.mapRimsItem.remove(anchorPane, itemToModify);

            //Una volta eliminato lo pneumatico, si libera sostanzialmente un posto. Diamo così la possibilità di inserirne altri pneumatici
            PrevTiresController.maxRims++;

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();
        }
    }
}

