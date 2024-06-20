package it.bicocca.laboratoriodiprogettazione_java.common;

import it.bicocca.laboratoriodiprogettazione_java.entity.News;

import java.io.*;

import static it.bicocca.laboratoriodiprogettazione_java.GroupeRMain.listNews;


/**
 * La classe OfflineNewsCreator è responsabile della creazione delle notizie da un file offline e della loro aggiunta
 * alla lista di notizie.
 */
public class OfflineNewsCreator {

    /**
     * Legge le notizie da un file offline e le aggiunge alla lista di notizie.
     */
    public void createNews(){
        try {
            int counter = 0;
            String line;

            InputStream inputStream = getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/News/news.txt").openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "ISO_8859_1"));

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("@");

                News notizia = new News();
                String titleNotizia = parts[0];
                String corpusNotizia = parts[1];
                String typeNotizia = parts[2];

                notizia.setTitleNews(titleNotizia);
                notizia.setCorpusNews(corpusNotizia);
                notizia.setTypeNews(typeNotizia);
                notizia.setIdNews(counter);

                listNews.add(notizia);
                counter ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
