package it.bicocca.laboratoriodiprogettazione_java.entity;

/**
 * La classe News rappresenta una singola notizia con un identificatore, un titolo, un corpo, un tipo e un'immagine associata.
 * Fornisce metodi per accedere e modificare le informazioni della notizia.
 */
public class News {
    private int idNews;
    private String titleNews;
    private String corpusNews;
    private String typeNews;

    private String imageSrc;

    /**
     * Costruisce un oggetto News con i valori forniti per l'ID della notizia, il titolo, il corpo, il tipo e il percorso dell'immagine.
     *
     * @param idNews l'ID della notizia
     * @param titleNews il titolo della notizia
     * @param corpusNews il corpo della notizia
     * @param typeNews il tipo della notizia
     * @param imageSrc il percorso dell'immagine associata alla notizia
     */
    public News(int idNews, String titleNews, String corpusNews, String typeNews, String imageSrc) {
        this.idNews = idNews;
        this.titleNews = titleNews;
        this.corpusNews = corpusNews;
        this.typeNews = typeNews;
        this.imageSrc = imageSrc;
    }

    /**
     * Costruttore della notizia (vuoto)
     */
    public News() {

    }

    /**
     * Restituisce l'ID della notizia.
     *
     * @return l'ID della notizia
     */
    public int getIdNews() {
        return idNews;
    }

    /**
     * Restituisce il titolo della notizia.
     *
     * @return il titolo della notizia
     */
    public String getTitleNews() {
        return titleNews;
    }

    /**
     * Restituisce il corpo della notizia.
     *
     * @return il corpo della notizia
     */
    public String getCorpusNews() {
        return corpusNews;
    }

    /**
     * Restituisce il tipo della notizia.
     *
     * @return il tipo della notizia
     */
    public String getTypeNews() {
        return typeNews;
    }

    /**
     * Restituisce il percorso dell'immagine associata alla notizia.
     *
     * @return il percorso dell'immagine associata alla notizia
     */
    public String getImageSrc() {
        return imageSrc;
    }

    /**
     * Imposta l'ID della notizia.
     *
     * @param idNews il nuovo ID della notizia
     */
    public void setIdNews(int idNews) {
        this.idNews = idNews;
    }

    /**
     * Imposta il titolo della notizia.
     *
     * @param titleNews il nuovo titolo della notizia
     */
    public void setTitleNews(String titleNews) {
        this.titleNews = titleNews;
    }

    /**
     * Imposta il corpo della notizia.
     *
     * @param corpusNews il nuovo corpo della notizia
     */
    public void setCorpusNews(String corpusNews) {
        this.corpusNews = corpusNews;
    }

    /**
     * Imposta il tipo della notizia.
     *
     * @param typeNews il nuovo tipo della notizia
     */
    public void setTypeNews(String typeNews) {
        this.typeNews = typeNews;
    }

    /**
     * Imposta il percorso dell'immagine associata alla notizia.
     *
     * @param imageSrc il nuovo percorso dell'immagine associata alla notizia
     */
    public void setImageSrc(String imageSrc){
        this.imageSrc = imageSrc;
    }

    /**
     * Restituisce una rappresentazione in forma di stringa dell'oggetto News, che include l'ID della notizia, il titolo,
     * il corpo e il tipo della notizia.
     *
     * @return una stringa che rappresenta l'oggetto News
     */
    @Override
    public String toString() {
        return "News{" +
                "idNews=" + idNews +
                ", titleNews='" + titleNews + '\'' +
                ", corpusNews='" + corpusNews + '\'' +
                ", typeNews='" + typeNews + '\'' +
                '}';
    }
}
