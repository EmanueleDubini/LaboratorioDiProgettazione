package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.entity.Part;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartTest {
    @Test
    public void testCostruttore() {
        // Verifica che il costruttore crei correttamente un'istanza di Part
        Part part = new Part("82006571079R", "Filtro Aria", "Bosch", "25.99", 10);

        assertEquals("82006571079R", part.getId());
        assertEquals("Filtro Aria", part.getName());
        assertEquals("Bosch", part.getBrand());
        assertEquals("25.99", part.getPrice());
        assertEquals(10, part.getQuantity());
    }

    @Test
    public void testGetterSetter() {
        // Verifica che i metodi getter e setter funzionino correttamente
        Part part = new Part();

        part.setId("82006571080R");
        part.setName("Cinghia di distribuzione");
        part.setBrand("Continental");
        part.setPrice("45.50");
        part.setQuantity(5);

        assertEquals("82006571080R", part.getId());
        assertEquals("Cinghia di distribuzione", part.getName());
        assertEquals("Continental", part.getBrand());
        assertEquals("45.50", part.getPrice());
        assertEquals(5, part.getQuantity());
    }
}
