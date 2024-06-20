package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.entity.Staff;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaffTest {
    @Test
    public void testCostruttore() {
        // Verifica che il costruttore crei correttamente un'istanza di Staff
        Staff staff = new Staff("123", "Mario", "Rossi", "mario@example.com", "Manager");

        assertEquals("123", staff.getId_dipendente());
        assertEquals("Mario", staff.getNome());
        assertEquals("Rossi", staff.getCognome());
        assertEquals("mario@example.com", staff.getE_mail());
        assertEquals("Manager", staff.getRuolo());
    }

    @Test
    public void testGetterSetter() {
        // Verifica che i metodi getter e setter funzionino correttamente
        Staff staff = new Staff("123", "Mario", "Rossi", "mario@example.com", "Manager");

        staff.setId_dipendente("456");
        staff.setNome("Luigi");
        staff.setCognome("Verdi");
        staff.setE_mail("luigi@example.com");
        staff.setRuolo("Assistant");

        assertEquals("456", staff.getId_dipendente());
        assertEquals("Luigi", staff.getNome());
        assertEquals("Verdi", staff.getCognome());
        assertEquals("luigi@example.com", staff.getE_mail());
        assertEquals("Assistant", staff.getRuolo());
    }

}
