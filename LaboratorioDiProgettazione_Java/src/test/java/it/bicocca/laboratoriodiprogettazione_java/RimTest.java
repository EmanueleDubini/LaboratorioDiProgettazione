package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.entity.Rim;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RimTest {

    @Test
    public void testCostruttore() {
        // Verifica che il costruttore crei correttamente un'istanza di Rim
        Rim rim = new Rim("Sportivo", 4, "150.00", "5");

        assertEquals("Sportivo", rim.getTypeRim());
        assertEquals("4", rim.getNumberRim());
        assertEquals("150.00", rim.getPriceRimEasy());
        assertEquals("5", rim.getDiscountRimEasy());
    }

    @Test
    public void testCalcoli() {
        // Verifica i calcoli effettuati sui valori di Rim
        Rim rim = new Rim("Sportivo", 4, "150,00", "5");

        // Prezzo singolo cerchio formattato come atteso
        String expectedFormattedPrice = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(150.00);
        assertEquals(expectedFormattedPrice, rim.getPriceRim(), "Il prezzo formattato del cerchio non corrisponde al valore atteso.");

        // Sconto singolo cerchio con simbolo percentuale
        assertEquals("5%", rim.getDiscountRim(), "Lo sconto applicato non corrisponde al valore atteso.");

        // Prezzo totale dei cerchi considerando lo sconto
        double priceTotal = 4 * 150.00; // Prezzo totale senza sconto
        double discountAmount = priceTotal * 5 / 100; // Calcolo dello sconto
        double expectedTotalPrice = priceTotal - discountAmount; // Prezzo totale con sconto

        // Poiché getPriceTotRimDouble() restituisce un Double, si confronta direttamente il valore atteso con quello ottenuto
        assertEquals(expectedTotalPrice, rim.getPriceTotRimDouble(), "Il prezzo totale dei cerchi non corrisponde al valore atteso.");
    }

    @Test
    public void testGetterSetter() {
        // Verifica che i metodi getter e setter funzionino correttamente
        Rim rim = new Rim("Sportivo", 4, "150.00", "5");

        rim.setTypeRim("Stradale");
        rim.setnRim(2);
        rim.setPriceRim("200.00");
        rim.setDiscountRim("10");

        assertEquals("Stradale", rim.getTypeRim());
        assertEquals("2", rim.getNumberRim());
        assertEquals("200.00", rim.getPriceRimEasy());
        assertEquals("10", rim.getDiscountRimEasy());
    }
}
