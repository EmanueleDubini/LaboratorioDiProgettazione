package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.entity.News;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NewsTest {
    @Test
    public void testConstructor() {
        News news1 = new News(1, "Title 1", "Corpus 1", "Type 1", "image1.png");
        News news2 = new News(2, "Title 2", "Corpus 2", "Type 2", "image2.png");

        assertEquals(1, news1.getIdNews());
        assertEquals("Title 1", news1.getTitleNews());
        assertEquals("Corpus 1", news1.getCorpusNews());
        assertEquals("Type 1", news1.getTypeNews());
        assertEquals("image1.png", news1.getImageSrc());

        assertEquals(2, news2.getIdNews());
        assertEquals("Title 2", news2.getTitleNews());
        assertEquals("Corpus 2", news2.getCorpusNews());
        assertEquals("Type 2", news2.getTypeNews());
        assertEquals("image2.png", news2.getImageSrc());
    }

    @Test
    public void testGetterSetter() {
        News news = new News(1, "Initial Title", "Initial Corpus", "Initial Type", "initialImage.png");

        news.setIdNews(2);
        news.setTitleNews("Updated Title");
        news.setCorpusNews("Updated Corpus");
        news.setTypeNews("Updated Type");
        news.setImageSrc("updatedImage.png");

        assertEquals(2, news.getIdNews());
        assertEquals("Updated Title", news.getTitleNews());
        assertEquals("Updated Corpus", news.getCorpusNews());
        assertEquals("Updated Type", news.getTypeNews());
        assertEquals("updatedImage.png", news.getImageSrc());
    }
}
