package it.bicocca.laboratoriodiprogettazione_java.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Classe di utilità per gestire la connessione al database MySQL.
 */
public class DbHelper {

    private static Connection connection = null;
    private static Statement statement = null;

    /**
     * Stabilisce una connessione al database MySQL con SSL/TLS.
     *
     * @return la connessione al database.
     */
    public static Connection getConnection() {
        try {
            final String DATABASE_URL = System.getenv("DB_URL");
            final String DATABASE_USER = System.getenv("DB_USER");
            final String DATABASE_PASSWORD = System.getenv("DB_PASSWORD");

            Properties properties = new Properties();
            properties.setProperty("user", DATABASE_USER);
            properties.setProperty("password", DATABASE_PASSWORD);
            properties.setProperty("useSSL", "true");
            properties.setProperty("requireSSL", "true");
            properties.setProperty("verifyServerCertificate", "true");


            connection = DriverManager.getConnection(DATABASE_URL, properties);
        } catch (SQLException e) {
            System.err.println("Si è verificata un'eccezione: " + e.getClass().getSimpleName());
            System.err.println("Errore durante la connessione al database. Controlla le tue credenziali e la disponibilità del database.");
        }
        return connection;
    }

    /**
     * Chiude la connessione al database MySQL.
     *
     * @throws SQLException se si verifica un errore durante la chiusura della connessione.
     */
    public static void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }

    /**
     * Crea un oggetto Statement per inviare query SQL al database.
     *
     * @return un oggetto Statement per inviare query SQL al database.
     * @throws SQLException se si verifica un errore durante la creazione dello Statement.
     */
    public static Statement getStatement() throws SQLException {
        if (statement == null) {

            Connection connection = getConnection();
            statement = connection.createStatement();
        }

        return statement;
    }

    /**
     * Chiude la connessione e resetta lo statement per future interrogazioni al DB
     *
     * @throws SQLException Se si verifica un errore durante la chiusura dello Statement.
     */
    public static void closeStatement() throws SQLException{
        if (statement != null) {

            connection.close();
            statement = null;
        }
    }
}
