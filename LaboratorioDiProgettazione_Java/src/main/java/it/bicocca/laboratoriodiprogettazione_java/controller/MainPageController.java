package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.GroupeRMain;
import it.bicocca.laboratoriodiprogettazione_java.entity.News;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static it.bicocca.laboratoriodiprogettazione_java.GroupeRMain.listNews;
import static it.bicocca.laboratoriodiprogettazione_java.controller.LoginController.ruolo;

/**
 * La classe {@code MainPageController} gestisce la navigazione principale all'interno dell'applicazione.
 * Consente agli utenti di spostarsi tra le diverse sezioni dell'applicazione come la home, il preventivatore,
 * i ricambi e le informazioni. La classe utilizza un {@link BorderPane} come layout principale per
 * sostituire il contenuto centrale in base alla sezione selezionata.
 * Le transizioni tra le varie parti dell'applicazione sono gestite attraverso i metodi specifici
 * che caricano i rispettivi file FXML. Questi metodi sono invocati da eventi generati dall'interfaccia utente,
 * come click su pulsanti o elementi di menu.
 */
public class MainPageController implements Initializable {

    @FXML
    public AnchorPane apane;
    @FXML
    public BorderPane bpane;

    @FXML
    public Button buttonLeft_PrevGomme;

    @FXML
    public Button buttonLeft_Officina;

    @FXML
    public TextArea textAreaInfo;

    @FXML
    Pane paneNews;

    @FXML
    HBox newsLayout;

    @FXML
    private Label userName;

    public static List<HBox> listNewsHbox = new ArrayList<>();

    static int countNewstimeline = 0;

    private static MainPageController instance;


    /**
     * Metodo initialize per gestire gli accessi alle sezioni.
     * Permette di personalizzare anche il titolo della mainpage in base al ruolo del personale che ha effettuato
     * il login.
     * Questo metodo viene chiamato automaticamente dopo che il file FXML è stato caricato.
     * Gestisce anche lo scorrimento delle notizie nella home page
     *
     * @param url il percorso utilizzato per risolvere i percorsi relativi per l'oggetto radice, o null se il percorso non è noto.
     * @param resourceBundle il ResourceBundle da utilizzare per localizzare l'oggetto radice, o null se il bundle non è noto.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Recuperiamo l'istanza
        instance = this;

        userName.setText(ruolo.toLowerCase());

        textAreaInfo.setText("Hai effettuato il login come " + ruolo.toLowerCase() + ".\nUtilizza il menù laterale per effettuare le operazioni.");

        switch (ruolo) {
            case "Officina":
                buttonLeft_PrevGomme.setDisable(true);
                break;
            case "Magazzino":
                buttonLeft_Officina.setDisable(true);
                break;
            default:
                break;
        }

        //Codice per gestione timeline notizie

        if (listNews.isEmpty()){
            News notiziaNoInt = new News();
            notiziaNoInt.setCorpusNews("C'è stato un errore nella creazione delle notizie!;Probabilmente la connessione internet non è disponibile.");
            notiziaNoInt.setTypeNews("REN");
            listNews.add(notiziaNoInt);
        }
        //Scarica le notizie dallo spreadsheet di Google
        try {
            for (int i = 0; i<listNews.size(); i++){

                //Carichiamo l'fxml della notizia
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("itemNews.fxml"));

                //Carichiamo l'HBox in cui dovremmo inserire la notizia
                HBox cardBox = fxmlLoader.load();
                ItemNewsController itemNewsController = fxmlLoader.getController();
                itemNewsController.setData(listNews.get(i));

                //Aggiungiamo in una lista tutti gli HBox con le notizie
                listNewsHbox.add(cardBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Creiamo una timeline infinita per fare scorrere le notizie

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        //Inseriamo la prima notizia
        newsLayout.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), newsLayout);

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        newsLayout.getChildren().add(listNewsHbox.get(countNewstimeline));
        newsLayout.setAlignment(Pos.CENTER);
        countNewstimeline = countNewstimeline + 1;
        // crea una key frame che verrà eseguita ogni 10 secondi
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(10), event -> {
            // questo codice viene eseguito ogni 10 secondi
            //Animazione di FadeOut
            newsLayout.setOpacity(1);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), newsLayout);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.play();

            //Puliamo il layout e inseriamo la prossima notizia
            newsLayout.getChildren().clear();

            FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(2), newsLayout);
            newsLayout.setOpacity(0);

            fadeIn2.setFromValue(0);
            fadeIn2.setToValue(1);
            fadeIn2.play();

            if (countNewstimeline==listNewsHbox.size()) {
                countNewstimeline = 0;
            }

            newsLayout.getChildren().add(listNewsHbox.get(countNewstimeline));
            newsLayout.setAlignment(Pos.CENTER);
            countNewstimeline = countNewstimeline + 1;

        });
        timeline.getKeyFrames().add(keyFrame);
        // avvia la timeline
        timeline.play();
    }

    /**
     * Metodo per tornare alla vista home dell'applicazione.
     * Imposta il pannello {@code apane} come contenuto centrale del {@code bpane}.
     */
    public void toHome() {
        bpane.setCenter(apane);
    }

    /**
     * Carica la vista del preventivatore.
     *
     * @throws IOException Se il file FXML non può essere caricato.
     */
    public void toPreventivatore() throws IOException {
        loadPage("01_Warehouse");
    }

    /**
     * Carica la vista dei ricambi.
     *
     * @throws IOException Se il file FXML non può essere caricato.
     */
    public void toRicambi() throws IOException {
        loadPage("01_Garage");
    }

    /**
     * Carica la vista delle informazioni.
     *
     * @throws IOException Se il file FXML non può essere caricato.
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
     * Metodo ausiliario per caricare le pagine dell'applicazione.
     * Sostituisce il contenuto centrale del {@code bpane} con la vista specificata.
     *
     * @param page Il nome della pagina (senza estensione .fxml) da caricare.
     * @throws IOException Se il file FXML non può essere caricato.
     */
    public void loadPage(String page) throws IOException {
        Parent root = null;
        try {

            root = FXMLLoader.load(MainPageController.class.getResource(page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bpane.setCenter(root);
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
     * Restituisce l'istanza singleton del controller della pagina principale.
     *
     * Questo metodo fornisce l'accesso all'istanza unica di `MainPageController` utilizzata
     * per gestire la pagina principale dell'applicazione. Assicura che vi sia una sola istanza
     * di questo controller in tutta l'applicazione (pattern singleton).
     *
     * @return l'istanza singleton di `MainPageController`
     */
    public static MainPageController getInstance() {
        return instance;
    }


}
