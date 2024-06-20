package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.entity.Tire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * La classe {@code EditTireController} gestisce le interazioni dell'utente nell'interfaccia di modifica degli pneumatici.
 * Questo include l'inizializzazione dell'interfaccia grafica con i dati degli pneumatici da modificare e la gestione
 * delle operazioni di modifica, annullamento ed eliminazione di uno pneumatico. Implementa l'interfaccia {@link Initializable}
 * per eseguire l'inizializzazione necessaria sui componenti dell'interfaccia grafica.
 * Il controller consente la modifica degli attributi di uno pneumatico, inclusi marca, dimensione, prezzo, sconto e prezzo del PFU.
 * Queste modifiche sono riflettute nelle strutture dati dell'applicazione e l'interfaccia grafica viene aggiornata di conseguenza.
 */

public class EditTireController implements Initializable {

        @FXML
        private Spinner<Integer> spinnerPneumQuan = new Spinner<>();

        @FXML
        private TextField TextFieldMarcaPneum;

        @FXML
        private TextField TextFieldDimensionePneum;

        @FXML
        private TextField TextFieldPrezzoPneum;

        @FXML
        private TextField TextFieldScontoPneum;

        @FXML
        private TextField TextFieldPfu;

        String brandTire, dimensionTire, priceTire, discountTire, pricePfu;
        int nTire;

        AnchorPane anchorPane = ItemTireController.anchorPaneCliccato;
        Tire tireToModify = PrevTiresController.mapTire.get(anchorPane);

    /**
     * Inizializza la classe controller. Questo metodo viene chiamato automaticamente
     * dopo il caricamento del file FXML. Inizializza i componenti dell'interfaccia grafica
     * con i dati correnti dello pneumatico per la modifica.
     *
     * @param location La posizione utilizzata per risolvere i percorsi relativi all'oggetto radice, o null se sconosciuta.
     * @param resources Le risorse utilizzate per localizzare l'oggetto radice, o null se non localizzato.
     */
        @Override
        public void initialize(URL location, ResourceBundle resources) {

            int nTire;

            TextFieldMarcaPneum.setText(tireToModify.getBrandTire());
            TextFieldDimensionePneum.setText(tireToModify.getDimensionTire());

            //Creiamo lo spinner per la modifica degli pneumatici
            nTire =  Integer.parseInt(tireToModify.getNumberTire());
            SpinnerValueFactory<Integer> spinnerValueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, nTire);
            spinnerPneumQuan.setValueFactory(spinnerValueFactory2);
            spinnerPneumQuan.editorProperty().get().setAlignment(Pos.CENTER);

            TextFieldPrezzoPneum.setText(tireToModify.getPriceTireEasy());
            TextFieldPfu.setText(tireToModify.getPricePfuEasy());
            TextFieldScontoPneum.setText(tireToModify.getDiscountTireEasy());
        }

    /**
     * Gestisce l'azione del pulsante "Modifica Pneumatico". Aggiorna i dati dello pneumatico con l'input dell'utente e chiude la finestra di modifica.
     *
     * @param actionEvent L'evento che ha innescato la chiamata al metodo.
     */
        public void EditTireButton(ActionEvent actionEvent)  {
            brandTire = TextFieldMarcaPneum.getText().trim();
            dimensionTire = TextFieldDimensionePneum.getText().trim();
            priceTire = TextFieldPrezzoPneum.getText().trim();
            discountTire = TextFieldScontoPneum.getText().trim();
            nTire = spinnerPneumQuan.getValue();
            pricePfu = TextFieldPfu.getText().trim();

            if(brandTire.equals("")|| priceTire.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Si è verificato un Errore");
                alert.setContentText("Dati Pneumatico mancanti!");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");

                alert.showAndWait();
            }else{
                //Creiamo il nuovo tire da inserire all'interno della mappa
                Tire tire  = new Tire(tireToModify.getIdTire(), brandTire, dimensionTire, priceTire, discountTire, nTire, pricePfu);

                //Recuperiamo l'ItemController dell'anchorPane creato per poterne cambiare il nome
                ItemTireController itemToModify = PrevTiresController.mapTireItem.get(anchorPane);
                itemToModify.setData(brandTire);

                //Facciamo un replace dell' ItemController all'interno dell'HashMap con quello appena modificato
                PrevTiresController.mapTireItem.replace(anchorPane, itemToModify);

                //Facciamo un replace dello tire all'interno dell'HashMap con lo tire modificato
                PrevTiresController.mapTire.replace(anchorPane, tire);

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
        public void CancelTireButton(ActionEvent actionEvent) throws IOException {
            //Chiudiamo la finestra
            //todo non funziona l'animazione di chiusura
            Parent root = null;
            root = ((Node) actionEvent.getSource()).getParent();

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();
        }

    /**
     * Gestisce l'azione del pulsante "Elimina Pneumatico". Rimuove lo pneumatico dalle strutture dati dell'applicazione
     * e aggiorna l'interfaccia grafica di conseguenza.
     *
     * @param actionEvent L'evento che ha innescato la chiamata al metodo.
     */
        public void DeleteTireButton(ActionEvent actionEvent) {

            //Creiamo una finestra di dialogo per avvisare l'utente è convinto di volere rimuovere lo pneumatico dal preventivo
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Sei sicuro?");
            alert.setContentText("Questa operazione eliminerà lo pneumatico in modo definitivo");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/org/example/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            ButtonType clearButtonType = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getDialogPane().getButtonTypes().add(clearButtonType);

            Optional<ButtonType> result =  alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                //Rimuoviamo l'anchorPane relativa allo pneumatico che stiamo inserendo
                PrevTiresController.gridTire.getChildren().remove(anchorPane);


                //Rimuoviamo all'interno dell'hashMap lo pneumatico corrispondente
                PrevTiresController.mapTire.remove(anchorPane, tireToModify);

                //Rimuoviamo all'interno dell'hashmap, l'anchorPane corrispondente e l'itemController
                ItemTireController itemDaModificare = PrevTiresController.mapTireItem.get(anchorPane);
                PrevTiresController.mapTireItem.remove(anchorPane, itemDaModificare);

                //Una volta eliminato lo pneumatico, si libera sostanzialmente un posto. Diamo così la possibilità di inserire altri pneumatici
                PrevTiresController.maxTires++;

                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                stage.hide();
            }
        }
    }

