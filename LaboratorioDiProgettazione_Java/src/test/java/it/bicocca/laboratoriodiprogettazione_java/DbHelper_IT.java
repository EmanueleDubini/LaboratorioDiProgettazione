package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.db.DbHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DbHelper_IT {

    private Connection connection;
    private Statement statement;

    @BeforeEach
    public void setUp() {
        try {
            connection = DbHelper.getConnection();
            statement = DbHelper.getStatement();

            //todo fare come in dboperations
            String useDB = "use db1;";
            String createTableSql = "CREATE TABLE IF NOT EXISTS test_table (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))";

            statement.executeUpdate(useDB);
            statement.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante il setup iniziale");
        }
    }

    @Test
    public void testConnectionAndStatement() throws SQLException {
        // Verifica che la connessione non sia null
        assertNotNull(connection, "La connessione al database non deve essere null");
        assertNotNull(statement, "Lo Statement non deve essere null");

        // Verifica che la connessione sia aperta
        assertTrue(connection.isValid(1), "La connessione al database è valida");
    }

    @Test
    public void testCRUDOperations() {
        try {
            // Create
            String insertSql = "INSERT INTO test_table (name) VALUES ('TestName')";
            int affectedRows = statement.executeUpdate(insertSql);
            assertTrue(affectedRows > 0, "Inserimento fallito");

            // Read
            String selectSql = "SELECT * FROM test_table WHERE name='TestName'";
            ResultSet resultSet = statement.executeQuery(selectSql);
            assertTrue(resultSet.next(), "Lettura fallita");

            // Update
            String updateSql = "UPDATE test_table SET name='UpdatedName' WHERE name='TestName'";
            affectedRows = statement.executeUpdate(updateSql);
            assertTrue(affectedRows > 0, "Aggiornamento fallito");

            // Delete
            String deleteSql = "DELETE FROM test_table WHERE name='UpdatedName'";
            affectedRows = statement.executeUpdate(deleteSql);
            assertTrue(affectedRows > 0, "Cancellazione fallita");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore durante le operazioni CRUD");
        }
    }

    @Test
    public void testSQLExceptionHandling() {
        // Testa una query malformata per verificare la gestione delle SQLException
        assertThrows(SQLException.class, () -> {
            statement.execute("SELECT * FROM NonExistingTable");
        }, "La gestione delle SQLException non funziona come previsto");
    }

    @AfterEach
    public void tearDown() {
        try {
            if (statement != null) {
                statement.execute("DROP TABLE IF EXISTS test_table"); // Pulisce il DB per i test successivi
                DbHelper.closeStatement();
            }
            if (connection != null) {
                DbHelper.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

