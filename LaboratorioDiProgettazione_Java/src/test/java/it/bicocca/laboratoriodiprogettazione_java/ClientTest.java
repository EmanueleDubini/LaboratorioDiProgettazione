package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.entity.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {
    @Test
    public void testCostruttore() {
        // Verifica che il costruttore crei correttamente un'istanza di Client
        Client client1 = new Client("Mario", "Rossi", "mario.rossi@example.com", "123456789");
        Client client2 = new Client("1","Mario", "Rossi", "mario.rossi@example.com", "123456789");

        assertEquals("Mario", client1.getNome());
        assertEquals("Rossi", client1.getCognome());
        assertEquals("mario.rossi@example.com", client1.getEmail());
        assertEquals("123456789", client1.getTelefono());

        assertEquals("1", client2.getId());
        assertEquals("Mario", client2.getNome());
        assertEquals("Rossi", client2.getCognome());
        assertEquals("mario.rossi@example.com", client2.getEmail());
        assertEquals("123456789", client2.getTelefono());
    }

    @Test
    public void testGetterSetter() {
        // Verifica che i metodi getter e setter funzionino correttamente
        Client client = new Client("1", "Mario", "Rossi", "mario.rossi@example.com", "123456789");

        client.setId("2");
        client.setNome("Luigi");
        client.setCognome("Verdi");
        client.setEmail("luigi.verdi@example.com");
        client.setTelefono("987654321");

        assertEquals("2", client.getId());
        assertEquals("Luigi", client.getNome());
        assertEquals("Verdi", client.getCognome());
        assertEquals("luigi.verdi@example.com", client.getEmail());
        assertEquals("987654321", client.getTelefono());
    }
}
