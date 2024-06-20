package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.common.OfflineNewsCreator;
import it.bicocca.laboratoriodiprogettazione_java.controller.LoginController;
import it.bicocca.laboratoriodiprogettazione_java.entity.News;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe che gestisce l'inizializzazione dell'applicazione
 */
public class GroupeRMain extends Application {

    /**
     * Parametro che contiene la scena dell'applicazione JavaFX
     */
    public static Scene scene;

    /**
     * Lista che contiene tutte le notizie che vengono create
     */
    public static List<News> listNews = new ArrayList<>();


    /**
     * Metodo chiamato automaticamente quando l'applicazione JavaFX viene avviata.
     * Questo metodo è utilizzato per inizializzare e mostrare la finestra principale dell'applicazione.
     *
     * @param stage Lo Stage principale dell'applicazione, che rappresenta la finestra dell'applicazione.
     * @throws Exception Se si verificano eccezioni durante l'avvio dell'applicazione.
     */
    @Override
    public void start(Stage stage) throws Exception {

        OfflineNewsCreator createOfflineNews = new OfflineNewsCreator();
        createOfflineNews.createNews();

        Parent pane = FXMLLoader.load(getClass().getResource("controller/00_Login.fxml"));
        scene = new Scene(pane);
        GroupeRMain.scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    /**
     * Metodo di ingresso principale per avviare l'applicazione Java.
     * Questo metodo viene chiamato automaticamente quando si avvia l'applicazione Java e accetta un array di argomenti da riga di comando.
     *
     * @param args Un array di stringhe che rappresenta gli argomenti passati all'applicazione Java da riga di comando.
     */
    public  static void main(String[] args) {
        launch(args);

    }

    /**
     * Imposta la radice della scena su un nuovo file FXML specificato.
     *
     * @param fxml Il percorso del file FXML che deve essere utilizzato come radice della scena.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.setFill(Color.TRANSPARENT);
    }

    /**
     * Carica e restituisce la radice del file FXML specificato.
     *
     * @param fxml Il percorso del file FXML da caricare, senza estensione ".fxml".
     * @return La radice del file FXML come oggetto Parent.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
