package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.entity.Tire;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TireTest {

    @Test
    public void testCostruttore() {
        // Verifica che il costruttore crei correttamente un'istanza di Tire
        Tire tire = new Tire("001", "Michelin", "205/55 R16", "100,00", "10", 4, "2.50");

        assertEquals("001", tire.getIdTire());
        assertEquals("Michelin", tire.getBrandTire());
        assertEquals("205/55 R16", tire.getDimensionTire());
        assertEquals("100,00", tire.getPriceTireEasy());
        assertEquals("10", tire.getDiscountTireEasy());
        assertEquals("4", tire.getNumberTire());
        assertEquals("2.50", tire.getPricePfuEasy());
    }

    @Test
    public void testGetterSetter() {
        // Verifica che i metodi getter e setter funzionino correttamente
        Tire tire = new Tire("001", "Michelin", "205/55 R16", "100,00", "10", 4, "2.50");

        // Modifica e verifica i valori con i setter
        tire.setIdTire("002");
        tire.setBrandTire("Pirelli");
        tire.setDimensionTire("195/65 R15");
        tire.setPriceTire("120,00");
        tire.setDiscountTire("15");
        tire.setnTire(2);
        tire.setPricePfu("3.00");

        // Verifica i nuovi valori con i getter
        assertEquals("002", tire.getIdTire());
        assertEquals("Pirelli", tire.getBrandTire());
        assertEquals("195/65 R15", tire.getDimensionTire());
        assertEquals("120,00", tire.getPriceTireEasy());
        assertEquals("15", tire.getDiscountTireEasy());
        assertEquals("2", tire.getNumberTire());
        assertEquals("3.00", tire.getPricePfuEasy());
    }

    @Test
    public void testCalcoliPrezzoTotale() {
        Tire tire = new Tire("001", "Michelin", "205/55 R16", "100,00", "10", 4, "2.50");

        // Prezzo totale dei pneumatici, considerando lo sconto
        double pricePerTire = 100.00; // Prezzo di un singolo pneumatico
        double discount = 0.10; // Sconto del 10%
        double priceTotalNoDiscount = pricePerTire * 4; // Prezzo senza sconto
        double discountAmount = priceTotalNoDiscount * discount; // Valore dello sconto
        double expectedPriceTotal = priceTotalNoDiscount - discountAmount; // Prezzo finale con sconto
        assertEquals(expectedPriceTotal, tire.getPriceTotTireDouble(), 0.01);

        // Prezzo totale PFU
        double pfuPrice = 2.50; // Prezzo PFU per pneumatico
        double expectedPfuTotal = pfuPrice * 4; // Prezzo totale PFU
        assertEquals(expectedPfuTotal, tire.getPriceTotPfuDouble(), 0.01);
    }
}
