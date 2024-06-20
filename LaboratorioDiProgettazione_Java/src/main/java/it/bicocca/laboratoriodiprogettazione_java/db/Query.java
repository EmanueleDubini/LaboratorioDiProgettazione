package it.bicocca.laboratoriodiprogettazione_java.db;

/**
 *
 * Classe che definisce le stringhe (statiche) per la formulazione delle query di interazione con il database.
 **/
public class Query {

    /**
     * Crea il databsase se non esiste
     */
    public static String createDB = "CREATE DATABASE IF NOT EXISTS db1;";

    /**
     * Elimina il databsase se non esiste
     */
    public static String deleteDB = "DROP DATABASE IF EXISTS db1;";

    /**
     * Seleziona il database da utilizzare
     */
    public static String useDB = "use db1;";

    /**
     * Creazione table Clienti
     */
    public static String createTableClienti = "CREATE TABLE IF NOT EXISTS Clienti (" +
            "    id_cliente INT AUTO_INCREMENT NOT NULL PRIMARY KEY," +
            "    nome CHAR(255)," +
            "    cognome CHAR(255)," +
            "    email VARCHAR(255)," +
            "    telefono VARCHAR(255)," +
            "    password VARCHAR(255)," +
            "    last_login DATETIME NULL," +
            "    PRIMARY KEY (id_cliente)" +
            ");";
    /**
     * Eliminazione table Clienti
     */
    public static String dropTableClienti = "DROP TABLE IF EXISTS Clienti;";

    /**
     * Creazione table Gestione Personale
     */
    public static String createTableGestionePersonale = "CREATE TABLE IF NOT EXISTS Gestione_Personale (" +
            "    id_dipendente INT AUTO_INCREMENT NOT NULL PRIMARY KEY," +
            "    nome CHAR(20) NOT NULL," +
            "    cognome CHAR(20) NOT NULL," +
            "    email VARCHAR(30) NOT NULL," +
            "    ruolo CHAR(20) NOT NULL," +
            "    password VARCHAR(30) NOT NULL" +
            ");";

    /**
     * Eliminazione table Gestione Personale
     */
    public static String dropTableGestionePersonale = "DROP TABLE IF EXISTS Gestione_Personale;";

    /**
     * Creazione table Magazzino Ricambi
     */
    public static String createTableMagazzinoRicambi = "CREATE TABLE IF NOT EXISTS Magazzino_Ricambi (" +
            "    id_ricambio CHAR(20) NOT NULL PRIMARY KEY," +
            "    nome_ricambio CHAR(255) NOT NULL," +
            "    marca_ricambio CHAR(200) NOT NULL," +
            "    prezzo_ricambio DECIMAL(10,2) NOT NULL," +
            "    quantita INT NOT NULL" +
            ");";

    /**
     * Eliminazione table Magazzino Ricambi
     */
    public static String deleteTableMagazzinoRicambi = "DROP TABLE IF EXISTS Magazzino_Ricambi;";

    /**
     * Creazione table Magazzino Pneumatici
     */
    public static String createTableMagazzinoPneumatici = "CREATE TABLE IF NOT EXISTS Magazzino_Pneumatici (" +
            "    id_pneumatico INT NOT NULL PRIMARY KEY," +
            "    marca_pneumatico VARCHAR(20) NOT NULL," +
            "    modello_pneumatico VARCHAR(20) NOT NULL," +
            "    tipo VARCHAR(20) NOT NULL," +
            "    larghezza INT NOT NULL," +
            "    altezza INT NOT NULL," +
            "    diametro INT NOT NULL," +
            "    carico INT NOT NULL," +
            "    velocità CHAR(2) NOT NULL," +
            "    prezzo_cad_pneumatico DECIMAL(10,2) NOT NULL" +
            ");";

    /**
     * Eliminazione table Magazzino Pneumatici
     */
    public static String deleteTableMagazzinoPneumatici = "DROP TABLE IF EXISTS Magazzino_Pneumatici;";

    /**
     * Creazione table Preventivi
     */
    public static String createTablePreventivi = "CREATE TABLE IF NOT EXISTS Preventivi (" +
            "    id_preventivo INT AUTO_INCREMENT NOT NULL PRIMARY KEY," +
            "    id_cliente INT NOT NULL," +
            "    file_PDF BLOB NOT NULL," +
            "    data_creazione DATE NOT NULL," +
            "    reparto_di_creazione CHAR(20) NOT NULL," +
            "    prezzo_totale DECIMAL(10,2) NOT NULL," +
            "    FOREIGN KEY (id_cliente) REFERENCES Clienti(id_cliente)" +
            ");";

    /**
     * Eliminazione table Magazzino Pneumatici
     */
    public static String deleteTablePreventivi = "DROP TABLE IF EXISTS Preventivi;";

    /**
     * Creazione table Garage
     */
    public static String createTableGarage = "CREATE TABLE IF NOT EXISTS Garage (" +
            "    targa VARCHAR(20) NOT NULL PRIMARY KEY," +
            "    telaio CHAR(200) NOT NULL," +
            "    marca_vettura CHAR(200) NOT NULL," +
            "    modello CHAR(200) NOT NULL," +
            "    id_cliente INT NOT NULL," +
            "    id_dipendente INT NOT NULL," +
            "    diagnosi CHAR(200) NOT NULL," +
            "    stato_riparazione CHAR(30) NOT NULL," +
            "    id_preventivo INT," +
            "    " +
            "    FOREIGN KEY (id_cliente)" +
            "        REFERENCES Clienti(id_cliente), -- Presumendo che esista una tabella Clienti con una colonna ID\n" +
            "       " +
            "    FOREIGN KEY (id_dipendente)" +
            "        REFERENCES Gestione_Personale(id_dipendente), -- Presumendo che esista una tabella Dipendenti con una colonna ID\n" +
            ");";

    /**
     * Eliminazione table Magazzino Pneumatici
     */
    public static String deleteTableGarage = "DROP TABLE IF EXISTS Garage;";
}
