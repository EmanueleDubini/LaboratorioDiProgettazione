package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.db.DbHelper;
import it.bicocca.laboratoriodiprogettazione_java.db.DbOperations;
import it.bicocca.laboratoriodiprogettazione_java.db.Query;
import it.bicocca.laboratoriodiprogettazione_java.entity.Car;
import it.bicocca.laboratoriodiprogettazione_java.entity.Client;
import it.bicocca.laboratoriodiprogettazione_java.entity.Part;
import it.bicocca.laboratoriodiprogettazione_java.entity.Staff;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DbOperations_IT {

    private Connection connection;
    private Statement statement;

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        try {
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            String testName = testInfo.getTestMethod().orElseThrow().getName();

            // Verifica e creazione delle tabelle necessarie per ogni test
            DatabaseMetaData dbm = connection.getMetaData();

            if (testName.equals("testInsertPart")) {
                ResultSet tables = dbm.getTables(null, null, "magazzino_ricambi", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableMagazzinoRicambi);
                }
            } else if (testName.equals("testDeletePart")) {
                ResultSet tables = dbm.getTables(null, null, "magazzino_ricambi", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableMagazzinoRicambi);
                }
                statement.executeUpdate("INSERT INTO Magazzino_Ricambi (id_ricambio, nome_ricambio, marca_ricambio, prezzo_ricambio, quantita) VALUES ('1001', 'Ricambio Test', 'Marca Ricambio', '28.50', '5')");
            }else if (testName.equals("testInsertStaff")) {
                ResultSet tables = dbm.getTables(null, null, "gestione_personale", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableGestionePersonale);
                }
            } else if (testName.equals("testInsertCapoOfficina")) {
                ResultSet tables = dbm.getTables(null, null, "Gestione_Personale", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableGestionePersonale);
                }
            }  else if (testName.equals("testInsertCapoMagazzino")) {
                ResultSet tables = dbm.getTables(null, null, "Gestione_Personale", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableGestionePersonale);
                }
            } else if (testName.equals("testDeleteStaff")) {
                ResultSet tables = dbm.getTables(null, null, "Gestione_Personale", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableGestionePersonale);
                }
                statement.executeUpdate("INSERT INTO Gestione_Personale (nome, cognome, e_mail, ruolo, password) VALUES ('Test', 'Dipendente', 'test.dipendente@example.com', 'RuoloTest', 'password123')");
            } else if (testName.equals("testUpdateStaffCapoMagazzino")) {
                ResultSet tables = dbm.getTables(null, null, "Gestione_Personale", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableGestionePersonale);
                }
                statement.executeUpdate("INSERT INTO Gestione_Personale (nome, cognome, e_mail, ruolo, password) VALUES ('Test', 'Dipendente', 'test.dipendente@example.com', 'Ruolo Test', 'password123')");
            } else if (testName.equals("testUpdateStaffCapoOfficina")) {
                ResultSet tables = dbm.getTables(null, null, "Gestione_Personale", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableGestionePersonale);
                }
                statement.executeUpdate("INSERT INTO Gestione_Personale (nome, cognome, e_mail, ruolo, password) VALUES ('Test', 'Dipendente', 'test.dipendente@example.com', 'Ruolo Test', 'password123')");
            } else if (testName.equals("testUpdateStaff")) {
                ResultSet tables = dbm.getTables(null, null, "Gestione_Personale", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableGestionePersonale);
                }
                statement.executeUpdate("INSERT INTO Gestione_Personale (nome, cognome, e_mail, ruolo, password) VALUES ('Test', 'Dipendente', 'test.dipendente@example.com', 'Ruolo Test', 'password123')");
            } else if (testName.equals("testInsertClient")) {
                ResultSet tables = dbm.getTables(null, null, "Gestione_Personale", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableClienti);
                }
            } else if (testName.equals("testInsertCar")) {
                ResultSet tables = dbm.getTables(null, null, "garage", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTableGarage);
                }
            } else if (testName.equals("testInsertPDF")) {
                ResultSet tables = dbm.getTables(null, null, "preventivi", null);
                if (!tables.next()) {
                    statement.executeUpdate(Query.createTablePreventivi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante il setup iniziale");
        }
    }


    /* todo
    @Test
    public void testAuthenticate() {
        try {
            String insertSql = "INSERT INTO Gestione_Personale (nome, cognome, e_mail, password, ruolo) VALUES ('Utente', 'Prova', 'Utente.Prova@example.com', 'password123', 'Direttore di filiale')";
            statement.executeUpdate(insertSql);

            String userRole = DbOperations.authenticate("Utente.Prova@example.com", "password123");
            assertEquals("Direttore di filiale", userRole, "Autenticazione fallita");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante il test di autenticazione");
        }
    }*/

    @Test
    public void testInsertStaff() {
        boolean isInserted = DbOperations.insertStaff("Staff", "Prova", "Staff.Prova@example.com", "password123", "Officina");
        assertTrue(isInserted, "Inserimento del dipendente fallito");

        try {
            //bisogna riaprire la connessione e lo statement perchè dopo che viene eseguito il metodo DbOperations.insertStaff, all'interno del metodo, vengono chiusi sia statement che connessione
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);


            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE e_mail = 'Staff.Prova@example.com'");
            assertTrue(resultSet.next(), "Dipendente non trovato nel database");

            assertEquals("Staff", resultSet.getString("nome"));
            assertEquals("Prova", resultSet.getString("cognome"));
            assertEquals("Officina", resultSet.getString("ruolo"));

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'inserimento del dipendente");
        }
    }

    @Test
    public void testInsertCapoOfficina() {
        try {
            // Ottieni una nuova connessione e statement
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE ruolo = 'Capo officina'");
            boolean capoOfficinaExists = resultSet.next();
            resultSet.close();

            if (capoOfficinaExists) {
                boolean isInserted = DbOperations.insertStaff("Capo", "Officina", "capo.officina@example.com", "password123", "Capo officina");
                assertFalse(isInserted, "Inserimento del secondo capo officina dovrebbe fallire");
            } else {
                boolean isInserted = DbOperations.insertStaff("Capo", "Officina", "capo.officina@example.com", "password123", "Capo officina");
                assertTrue(isInserted, "Inserimento del capo officina fallito");

                resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE e_mail = 'capo.officina@example.com'");
                assertTrue(resultSet.next(), "Capo officina non trovato nel database");

                assertEquals("Capo", resultSet.getString("nome"));
                assertEquals("Officina", resultSet.getString("cognome"));
                assertEquals("Capo officina", resultSet.getString("ruolo"));

                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'inserimento del capo officina");
        }
    }

    @Test
    public void testInsertCapoMagazzino() {
        try {
            // Ottieni una nuova connessione e statement
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE ruolo = 'Capo magazzino'");
            boolean capoMagazzinoExists = resultSet.next();
            resultSet.close();

            if (capoMagazzinoExists) {
                boolean isInserted = DbOperations.insertStaff("Capo", "Magazzino", "capo.magazzino@example.com", "password123", "Capo magazzino");
                assertFalse(isInserted, "Inserimento del secondo capo magazzino dovrebbe fallire");
            } else {
                boolean isInserted = DbOperations.insertStaff("Capo", "Magazzino", "capo.magazzino@example.com", "password123", "Capo magazzino");
                assertTrue(isInserted, "Inserimento del capo magazzino fallito");

                resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE e_mail = 'capo.magazzino@example.com'");
                assertTrue(resultSet.next(), "Capo magazzino non trovato nel database");

                assertEquals("Capo", resultSet.getString("nome"));
                assertEquals("Magazzino", resultSet.getString("cognome"));
                assertEquals("Capo magazzino", resultSet.getString("ruolo"));

                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'inserimento del capo magazzino");
        }
    }

    @Test
    public void testDeleteStaff() {
        // Verifica che il dipendente esista prima della cancellazione
        try {
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE e_mail = 'test.dipendente@example.com'");
            assertTrue(resultSet.next(), "Il dipendente da cancellare non esiste nel database");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'esistenza del dipendente");
        }

        boolean isDeleted = DbOperations.deleteStaff("test.dipendente@example.com");
        assertTrue(isDeleted, "La cancellazione del dipendente ha fallito");

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE e_mail = 'test.dipendente@example.com'");
            assertFalse(resultSet.next(), "Il dipendente non è stato cancellato correttamente");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica della cancellazione del dipendente");
        }
    }

    @Test
    public void testUpdateStaffCapoMagazzino() {
        try {
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE ruolo = 'Capo magazzino'");
            boolean capoMagazzinoExists = resultSet.next();
            resultSet.close();

            if (capoMagazzinoExists) {
                boolean isUpdated = DbOperations.updateStaff("test.dipendente@example.com", "NuovoNome", "NuovoCognome", "nuova.email@example.com", "nuovaPassword123", "Capo magazzino");
                assertFalse(isUpdated, "L'aggiornamento del membro del personale con ruolo 'Capo magazzino' non dovrebbe essere consentito se ce n'è già uno nel database");
            } else {
                boolean isUpdated = DbOperations.updateStaff("test.dipendente@example.com", "NuovoNome", "NuovoCognome", "nuova.email@example.com", "nuovaPassword123", "Capo magazzino");
                assertTrue(isUpdated, "L'aggiornamento del membro del personale con ruolo 'Capo magazzino' dovrebbe essere consentito se non ce n'è già uno nel database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'esistenza del Capo magazzino nel database");
        }
    }

    @Test
    public void testUpdateStaffCapoOfficina() {
        try {
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE ruolo = 'Capo officina'");
            boolean capoMagazzinoExists = resultSet.next();
            resultSet.close();

            if (capoMagazzinoExists) {
                boolean isUpdated = DbOperations.updateStaff("test.dipendente@example.com", "NuovoNome", "NuovoCognome", "nuova.email@example.com", "nuovaPassword123", "Capo officina");
                assertFalse(isUpdated, "L'aggiornamento del membro del personale con ruolo 'Capo magazzino' non dovrebbe essere consentito se ce n'è già uno nel database");
            } else {
                boolean isUpdated = DbOperations.updateStaff("test.dipendente@example.com", "NuovoNome", "NuovoCognome", "nuova.email@example.com", "nuovaPassword123", "Capo officina");
                assertTrue(isUpdated, "L'aggiornamento del membro del personale con ruolo 'Capo magazzino' dovrebbe essere consentito se non ce n'è già uno nel database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'esistenza del Capo magazzino nel database");
        }
    }

    @Test
    public void testUpdateStaff() {
        try {
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            // Aggiornamento di un membro del personale con ruolo "Magazzino" a "Test"
            boolean isUpdated = DbOperations.updateStaff("test.dipendente@example.com", "NuovoNome", "NuovoCognome", "nuova.email@example.com", "nuovaPassword123", "Test");
            assertTrue(isUpdated, "L'aggiornamento del ruolo del membro del personale dovrebbe essere avvenuta con successo");

            // Verifica che il ruolo sia stato effettivamente aggiornato a "Test"
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gestione_Personale WHERE e_mail = 'nuova.email@example.com'");
            assertTrue(resultSet.next(), "Membro del personale non trovato nel database dopo l'aggiornamento");
            assertEquals("Test", resultSet.getString("ruolo"), "Il ruolo del membro del personale non corrisponde al valore atteso dopo l'aggiornamento");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante l'aggiornamento del ruolo del membro del personale");
        }
    }



    @Test
    public void testInsertPart() {
        boolean isInserted = DbOperations.insertPart("####################", "ComponenteDiProva", "MarcaDiProva", "999.99", 999);
        assertTrue(isInserted, "Inserimento del ricambio fallito");

        try {
            //bisogna riaprire la connessione e lo statement perchè dopo che viene eseguito il metodo DbOperations.insertStaff, all'interno del metodo, vengono chiusi sia statement che connessione
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);


            ResultSet resultSet = statement.executeQuery("SELECT * FROM Magazzino_Ricambi WHERE id_ricambio = '####################'");
            assertTrue(resultSet.next(), "Ricambio non trovato nel database");

            assertEquals("ComponenteDiProva", resultSet.getString("nome_ricambio"));
            assertEquals("MarcaDiProva", resultSet.getString("marca_ricambio"));
            assertEquals(999, resultSet.getInt("quantita"));

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'inserimento del ricambio");
        }
    }

    @Test
    public void testDeletePart() {
        // Verifica che il dipendente esista prima della cancellazione
        try {
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Magazzino_Ricambi WHERE id_ricambio = '1001'");
            assertTrue(resultSet.next(), "Il ricambio da cancellare non esiste nel database");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'esistenza del ricambio");
        }

        boolean isDeleted = DbOperations.deletePart("1001");
        assertTrue(isDeleted, "La cancellazione del ricambio ha fallito");

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Magazzino_Ricambi WHERE id_ricambio = '1001'");
            assertFalse(resultSet.next(), "Il ricambio non è stato cancellato correttamente");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica della cancellazione del dipendente");
        }
    }

    @Test
    public void testGetAllParts() {
        try {
            statement.executeUpdate("INSERT INTO magazzino_ricambi (id_ricambio, nome_ricambio, marca_ricambio, prezzo_ricambio, quantita) VALUES ('99999R', 'Filtro', 'Bosch', '25.99', '10')");
            statement.executeUpdate("INSERT INTO magazzino_ricambi (id_ricambio, nome_ricambio, marca_ricambio, prezzo_ricambio, quantita) VALUES ('99990R', 'Cinghia', 'Continental', '45.50', '5')");

            ObservableList<Part> partList = DbOperations.getAllParts();

            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            boolean part1Found = false;
            boolean part2Found = false;

            for (Part part : partList) {
                if (part.getId().equals("99999R")) {
                    assertEquals("Filtro", part.getName());
                    part1Found = true;
                } else if (part.getId().equals("99990R")) {
                    assertEquals("Cinghia", part.getName());
                    part2Found = true;
                }
            }

            assertTrue(part1Found, "Part1 non trovato");
            assertTrue(part2Found, "Part2 non trovato");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante il test di recupero di tutti i ricambi");
        }
    }

    @Test
    public void testInsertClient() {
        boolean isInserted = DbOperations.insertClient("Cliente", "Prova", "Cliente.Prova@example.com", "1234567890", "password123");
        assertTrue(isInserted, "Inserimento del cliente fallito");

        try {
            //bisogna riaprire la connessione e lo statement perchè dopo che viene eseguito il metodo DbOperations.insertStaff, all'interno del metodo, vengono chiusi sia statement che connessione
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);


            ResultSet resultSet = statement.executeQuery("SELECT * FROM clienti WHERE email = 'Cliente.Prova@example.com'");
            assertTrue(resultSet.next(), "Cliente non trovato nel database");

            assertEquals("Cliente", resultSet.getString("nome"));
            assertEquals("Prova", resultSet.getString("cognome"));
            assertEquals("1234567890", resultSet.getString("telefono"));

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'inserimento del cliente");
        }
    }

    @Test
    public void testInsertCar() {
        try {
            // Inserimento del cliente
            String insertClientSql = "INSERT INTO clienti (id_cliente, nome, cognome, email, telefono) VALUES ('999', 'Cliente', 'Prova', 'Cliente.Prova@example.com', '1234567890')";
            statement.executeUpdate(insertClientSql);

            // Verifica l'inserimento del cliente
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clienti WHERE email = 'Cliente.Prova@example.com'");
            assertTrue(resultSet.next(), "Cliente non trovato nel database");
            assertEquals("Cliente", resultSet.getString("nome"));
            assertEquals("Prova", resultSet.getString("cognome"));
            assertEquals("1234567890", resultSet.getString("telefono"));
            resultSet.close();

            // Inserimento della vettura
            boolean isInserted = DbOperations.insertCar(new Client("999", "Cliente", "Prova", "Cliente.Prova@example.com", "1234567890"),
                    "MarcaProva", "ModelloProva", "ZZ999ZZ", "ZZZZZZZZZ", "DiagnosiProva", "Preso In Carica");
            assertTrue(isInserted, "Inserimento della vettura fallito");

            // Riapri la connessione e lo statement
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            // Verifica l'inserimento della vettura
            resultSet = statement.executeQuery("SELECT * FROM Garage WHERE targa = 'ZZ999ZZ'");
            assertTrue(resultSet.next(), "Vettura non trovata nel database");
            assertEquals("MarcaProva", resultSet.getString("marca_vettura"));
            assertEquals("ModelloProva", resultSet.getString("modello"));
            assertEquals("ZZZZZZZZZ", resultSet.getString("telaio"));
            assertEquals("DiagnosiProva", resultSet.getString("diagnosi"));
            assertEquals("Preso In Carica", resultSet.getString("stato_riparazione"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'inserimento della vettura");
        }
    }

    @Test
    public void testGetAllStaff() {
        try {
            // inserimento dei due staff specifici
            statement.executeUpdate("INSERT INTO Gestione_Personale (nome, cognome, e_mail, ruolo, password) VALUES ('Staff1', 'Prova1', 'Staff1.Prova1@example.com', 'Officina', 'password123')");
            statement.executeUpdate("INSERT INTO Gestione_Personale (nome, cognome, e_mail, ruolo, password) VALUES ('Staff2', 'Prova2', 'Staff2.Prova2@example.com', 'Magazzino', 'password123')");

            // lista completa dello staff
            ObservableList<Staff> staffList = DbOperations.getAllStaff();

            //bisogna riaprire la connessione e lo statement perchè dopo che viene eseguito il metodo DbOperations.insertStaff, all'interno del metodo, vengono chiusi sia statement che connessione
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            // Verifica che gli staff specifici esistano nella lista
            boolean staff1Found = false;
            boolean staff2Found = false;

            for (Staff staff : staffList) {
                if (staff.getE_mail().equals("Staff1.Prova1@example.com")) {
                    assertEquals("Staff1", staff.getNome());
                    staff1Found = true;
                } else if (staff.getE_mail().equals("Staff2.Prova2@example.com")) {
                    assertEquals("Staff2", staff.getNome());
                    staff2Found = true;
                }
            }

            // Verifica che entrambi gli staff siano stati trovati
            assertTrue(staff1Found, "Staff1 non trovato");
            assertTrue(staff2Found, "Staff2 non trovato");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante il test di recupero di tutti i dipendenti");
        }
    }

    @Test
    public void testGetAllClients() {
        try {
            // Inserimento dei clienti specifici
            statement.executeUpdate("INSERT INTO clienti (id_cliente, nome, cognome, email, telefono) VALUES ('999', 'Cliente1', 'Prova1', 'Cliente1.Prova1@example.com', '1234567890')");
            statement.executeUpdate("INSERT INTO clienti (id_cliente, nome, cognome, email, telefono) VALUES ('1000', 'Cliente2', 'Prova2', 'Cliente2.Prova2@example.com', '0987654321')");

            // Lista completa dei clienti
            List<Client> clientList = DbOperations.getAllClients();

            // Bisogna riaprire la connessione e lo statement perché dopo che viene eseguito il metodo DbOperations.getAllClients, all'interno del metodo, vengono chiusi sia statement che connessione
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            // Verifica che i clienti specifici esistano nella lista
            boolean client1Found = false;
            boolean client2Found = false;

            for (Client client : clientList) {
                if (client.getEmail().equals("Cliente1.Prova1@example.com")) {
                    assertEquals("Cliente1", client.getNome());
                    assertEquals("Prova1", client.getCognome());
                    assertEquals("1234567890", client.getTelefono());
                    client1Found = true;
                } else if (client.getEmail().equals("Cliente2.Prova2@example.com")) {
                    assertEquals("Cliente2", client.getNome());
                    assertEquals("Prova2", client.getCognome());
                    assertEquals("0987654321", client.getTelefono());
                    client2Found = true;
                }
            }

            // Verifica che entrambi i clienti siano stati trovati
            assertTrue(client1Found, "Cliente1 non trovato");
            assertTrue(client2Found, "Cliente2 non trovato");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante il test di recupero di tutti i clienti");
        }
    }

    @Test
    public void testGetAllCars() {
        try {
            // Inserimento del cliente necessario per la foreign key
            String insertClientSql = "INSERT INTO clienti (id_cliente, nome, cognome, email, telefono) VALUES ('999', 'Cliente', 'Prova', 'Cliente.Prova@example.com', '1234567890')";
            statement.executeUpdate(insertClientSql);

            // Verifica l'inserimento del cliente
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clienti WHERE email = 'Cliente.Prova@example.com'");
            assertTrue(resultSet.next(), "Cliente non trovato nel database");
            assertEquals("Cliente", resultSet.getString("nome"));
            assertEquals("Prova", resultSet.getString("cognome"));
            assertEquals("1234567890", resultSet.getString("telefono"));
            resultSet.close();

            // Inserimento delle vetture specifiche
            statement.executeUpdate("INSERT INTO garage (targa, telaio, marca_vettura, modello, id_cliente, id_dipendente, diagnosi, stato_riparazione) VALUES ('ZZ999ZZ', 'ZZZZZZZZZ', 'MarcaProva1', 'ModelloProva1', '999', '1', 'DiagnosiProva1', 'Preso In Carica')");
            statement.executeUpdate("INSERT INTO garage (targa, telaio, marca_vettura, modello, id_cliente, id_dipendente, diagnosi, stato_riparazione) VALUES ('ZZ888ZZ', 'YYYYYYYYY', 'MarcaProva2', 'ModelloProva2', '999', '1', 'DiagnosiProva2', 'In Riparazione')");

            // Lista completa delle vetture
            List<Car> carList = DbOperations.getAllCars();

            // Riapri la connessione e lo statement
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            // Verifica che le vetture specifiche esistano nella lista
            boolean car1Found = false;
            boolean car2Found = false;

            for (Car car : carList) {
                if (car.getTarga().equals("ZZ999ZZ")) {
                    assertEquals("MarcaProva1", car.getMarcaVettura());
                    assertEquals("ModelloProva1", car.getModello());
                    assertEquals("ZZZZZZZZZ", car.getTelaio());
                    assertEquals("DiagnosiProva1", car.getDiagnosi());
                    assertEquals("Preso In Carica", car.getStatoRiparazione());
                    car1Found = true;
                } else if (car.getTarga().equals("ZZ888ZZ")) {
                    assertEquals("MarcaProva2", car.getMarcaVettura());
                    assertEquals("ModelloProva2", car.getModello());
                    assertEquals("YYYYYYYYY", car.getTelaio());
                    assertEquals("DiagnosiProva2", car.getDiagnosi());
                    assertEquals("In Riparazione", car.getStatoRiparazione());
                    car2Found = true;
                }
            }

            // Verifica che entrambe le vetture siano state trovate
            assertTrue(car1Found, "Vettura ZZ999ZZ non trovata");
            assertTrue(car2Found, "Vettura ZZ888ZZ non trovata");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante il test di recupero di tutte le vetture");
        }
    }


    @Test
    public void testInsertPDF() {
        try {
            // Inserimento del cliente necessario
            String insertClientSql = "INSERT INTO clienti (id_cliente, nome, cognome, email, telefono) VALUES ('999', 'Cliente', 'Prova', 'Cliente.Prova@example.com', '1234567890')";
            statement.executeUpdate(insertClientSql);

            // Verifica l'inserimento del cliente
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clienti WHERE email = 'Cliente.Prova@example.com'");
            assertTrue(resultSet.next(), "Cliente non trovato nel database");
            assertEquals("Cliente", resultSet.getString("nome"));
            assertEquals("Prova", resultSet.getString("cognome"));
            assertEquals("1234567890", resultSet.getString("telefono"));
            resultSet.close();


            // Inserimento del preventivo
            java.sql.Date creationDate = java.sql.Date.valueOf("1999-01-01");
            boolean isInserted = DbOperations.insertPDF(999, null, creationDate, "Officina", 150.75);
            assertTrue(isInserted, "Inserimento del PDF fallito");

            // Riapri la connessione e lo statement
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            // Verifica l'inserimento del PDF
            resultSet = statement.executeQuery("SELECT * FROM preventivi WHERE id_cliente = 999 AND reparto_di_creazione = 'Officina'");
            assertTrue(resultSet.next(), "Preventivo non trovato nel database");
            assertEquals(creationDate, resultSet.getDate("data_creazione"));
            assertEquals(150.75, resultSet.getDouble("prezzo_totale"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante la verifica dell'inserimento del PDF");
        }
    }






    @AfterEach
    public void tearDown(TestInfo testInfo) {
        try {
            String testName = testInfo.getTestMethod().orElseThrow().getName();

            if (statement != null) {
                // Rimuovi i dati inseriti specifici per ogni test
                if (testName.equals("testAuthenticate")) {
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'Utente.Prova@example.com'");
                } else if (testName.equals("testInsertStaff")) {
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'Staff.Prova@example.com'");
                } else if (testName.equals("testInsertCapoOfficina")) {
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'capo.officina@example.com'");
                } else if (testName.equals("testInsertCapoMagazzino")) {
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'capo.magazzino@example.com'");
                } else if (testName.equals("testUpdateStaffCapoMagazzino")) {
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'nuova.email@example.com'");
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'test.dipendente@example.com'");
                } else if (testName.equals("testUpdateStaffCapoOfficina")) {
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'nuova.email@example.com'");
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'test.dipendente@example.com'");
                } else if (testName.equals("testUpdateStaff")) {
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'nuova.email@example.com'");
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail = 'test.dipendente@example.com'");
                } else if (testName.equals("testInsertPart")) {
                    statement.executeUpdate("DELETE FROM Magazzino_Ricambi WHERE id_ricambio = '####################'");
                } else if (testName.equals("testGetAllParts")) {
                    statement.executeUpdate("DELETE FROM Magazzino_Ricambi WHERE id_ricambio IN ('99999R', '99990R')");
                } else if (testName.equals("testInsertClient")) {
                     statement.executeUpdate("DELETE FROM clienti WHERE email = 'Cliente.Prova@example.com'");
                } else if (testName.equals("testGetAllStaff")) {
                    statement.executeUpdate("DELETE FROM Gestione_Personale WHERE e_mail IN ('Staff1.Prova1@example.com', 'Staff2.Prova2@example.com')");
                } else if (testName.equals("testInsertCar")) {
                    //eliminare prima la riga dalla tabella garage in quanto è presente una foreign key con la tabella clienti
                    statement.executeUpdate("DELETE FROM Garage WHERE targa = 'ZZ999ZZ'");
                    statement.executeUpdate("DELETE FROM clienti WHERE email = 'Cliente.Prova@example.com'");
                } else if (testName.equals("testGetAllClients")) {
                    statement.executeUpdate("DELETE FROM clienti WHERE email IN ('Cliente1.Prova1@example.com', 'Cliente2.Prova2@example.com')");
                } else if (testName.equals("testGetAllCars")) {
                    statement.executeUpdate("DELETE FROM garage WHERE targa IN ('ZZ999ZZ', 'ZZ888ZZ')");
                    statement.executeUpdate("DELETE FROM clienti WHERE email = 'Cliente.Prova@example.com'");
                } else if (testName.equals("testInsertPDF")) {
                    statement.executeUpdate("DELETE FROM preventivi WHERE id_cliente = 999");
                    statement.executeUpdate("DELETE FROM clienti WHERE email = 'Cliente.Prova@example.com'");
                }

                // Chiude lo statement
                DbHelper.closeStatement();
            } else {
                DbHelper.closeConnection();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante il teardown");
        }
    }
}

