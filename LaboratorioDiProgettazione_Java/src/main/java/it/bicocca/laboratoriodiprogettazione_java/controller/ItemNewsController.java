package it.bicocca.laboratoriodiprogettazione_java.controller;

import it.bicocca.laboratoriodiprogettazione_java.entity.News;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

/**
 * Controller per la visualizzazione e gestione delle notizie.
 * Questa classe controlla gli elementi dell'interfaccia utente e popola i dati di una singola notizia.
 */
public class ItemNewsController {
    @FXML
    private Label titleNews;

    @FXML
    private TextArea corpusNews;

    @FXML
    private ImageView imageNews;

    /**
     * Imposta i dati della notizia nell'interfaccia utente.
     *
     * @param news l'oggetto News contenente i dati della notizia da visualizzare
     */
    public void setData(News news){

        titleNews.setText(news.getTitleNews());
        String corpusNotiziaComma = news.getCorpusNews();
        corpusNotiziaComma = corpusNotiziaComma.replaceAll(";", "\n");
        corpusNews.setText(corpusNotiziaComma);

        String typeNews = news.getTypeNews();
        String srcNews = findImage(typeNews);

        imageNews.setImage(new Image(srcNews));

    }

    /**
     * Trova il percorso dell'immagine in base al tipo di notizia.
     *
     * @param typeNews il tipo di notizia
     * @return il percorso dell'immagine corrispondente al tipo di notizia
     */
    public String findImage(String typeNews) {
        String imagePath;
        switch (typeNews) {
            case "REN":
                imagePath = "/it/bicocca/laboratoriodiprogettazione_java/images/renaultLogo_news.png";
                break;
            case "ALP":
                imagePath = "/it/bicocca/laboratoriodiprogettazione_java/images/alpineLogo_news.png";
                break;
            case "DAC":
                imagePath = "/it/bicocca/laboratoriodiprogettazione_java/images/daciaLogo_news.png";
                break;
            default:
                imagePath = "/it/bicocca/laboratoriodiprogettazione_java/images/renaultLogo_news.png";
                break;
        }

        URL resource = getClass().getResource(imagePath);
        if (resource == null) {
            System.err.println("Resource not found: " + imagePath);
            return null;
        }
        return resource.toExternalForm();
    }
}
