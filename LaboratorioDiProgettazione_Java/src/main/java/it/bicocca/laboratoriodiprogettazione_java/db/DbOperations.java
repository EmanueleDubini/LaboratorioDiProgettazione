package it.bicocca.laboratoriodiprogettazione_java.db;

import it.bicocca.laboratoriodiprogettazione_java.db.DbHelper;
import it.bicocca.laboratoriodiprogettazione_java.entity.Car;
import it.bicocca.laboratoriodiprogettazione_java.entity.Client;
import it.bicocca.laboratoriodiprogettazione_java.entity.Part;
import it.bicocca.laboratoriodiprogettazione_java.entity.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Classe che contiene i metodi per interrogare il DataBase (DB)
 */
public class DbOperations {

    //UTENTI//

    /**
     * Il metodo autentica un utente verificando se le credenziali fornite corrispondono a quelle registrate nella tabella Gestione_Personale del database.
     * Il metodo accetta l'email e la password dell'utente come parametri ed esegue una query SQL per determinare se esiste un record corrispondente.
     * Se le credenziali sono verificate correttamente, restituisce il ruolo dell'utente autenticato. Se le credenziali non sono corrette o l'utente non viene trovato, restituisce null.
     *
     * Innanzitutto, il metodo stabilisce una connessione al database e inizializza il database eseguendo le istruzioni SQL per creare e utilizzare il database specificato.
     * Successivamente, esegue la query per ottenere il ruolo dell'utente basato sulle credenziali fornite. Il risultato della query viene memorizzato in un oggetto ResultSet.
     * Se le credenziali sono errate o l'utente non è presente nel database, il valore di ritorno rimane null.
     * Al termine delle operazioni, le risorse utilizzate (Statement e connessione al database) vengono chiuse per evitare perdite di memoria.
     * In caso di errori durante l'esecuzione della query o la chiusura delle risorse, viene generata e stampata una SQLException.
     *
     * @param userEmail l'email dell'utente, utilizzata per verificare l'identità dell'utente.
     * @param userPsw la password dell'utente, utilizzata insieme all'email per l'autenticazione.
     * @return il ruolo dell'utente se le credenziali sono corrette, altrimenti null.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    public static String authenticate(String userEmail, String userPsw) {
        String userRole = null;
        String query = "SELECT ruolo FROM Gestione_Personale WHERE  e_mail = '" + userEmail + "' AND password = '" + userPsw + "'";
        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(query);

            // Controlliamo se il risultato ha almeno una riga
            if (rs.next()) {
                // Otteniamo il ruolo dell'utente
                userRole = rs.getString("ruolo");
            }

            // Chiudiamo le risorse
            rs.close();
            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userRole;
    }

     /**
     * Il metodo è progettato per aggiungere un nuovo dipendente alla tabella `Gestione_Personale` del database.
     * Inizialmente, il metodo esegue una query SQL per verificare se l'email fornita è già associata a un dipendente esistente.
     * Stabilisce una connessione con il database e inizializza il database eseguendo le istruzioni SQL specificate nei pacchetti `Query.createDB` e `Query.useDB`.
     * Successivamente, esegue la query di selezione per confermare la presenza o l'assenza del dipendente nel database.
     * Controlla inoltre se esiste già un dipendente con i ruoli di "Capo officina" o "Capo magazzino". Se tali ruoli sono già occupati, il metodo termina restituendo `false` e non registra il nuovo dipendente.
     * Se nessun record corrispondente viene trovato, il metodo procede con la creazione di una query d'inserimento utilizzando i dati forniti in input e inserisce il nuovo dipendente nel database.
     * Al termine dell'operazione, il metodo chiude le risorse `Statement` e la connessione al database utilizzando `DbHelper`.
     * Durante l'esecuzione, eventuali eccezioni di tipo `SQLException` vengono gestite stampando il tracciato dello stack dell'eccezione attraverso `e.printStackTrace()`.
     * Il metodo restituisce un valore booleano: `true` se l'inserimento nel database è riuscito, altrimenti `false`.
     *
     * @param name il nome del dipendente da inserire.
     * @param surname il cognome del dipendente da inserire.
     * @param userEmail l'email del dipendente, utilizzata per verificare l'unicità dell'utente nel database.
     * @param userPsw la password del dipendente, usata per configurare l'accesso all'account.
     * @param role il ruolo del dipendente, con particolare attenzione ai ruoli di "Capo officina" e "Capo magazzino".
     * @return true se il dipendente è stato inserito con successo nel database, false altrimenti.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    public static boolean insertStaff(String name, String surname, String userEmail, String userPsw, String role) {
        boolean isInserted = false;
        String selectQuery = "SELECT * FROM Gestione_Personale WHERE e_mail = '" + userEmail + "'";
        String checkRoleOfficinaQuery = "SELECT * FROM Gestione_Personale WHERE ruolo = 'Capo officina'";
        String checkRoleMagazzinoQuery = "SELECT * FROM Gestione_Personale WHERE ruolo = 'Capo magazzino'";

        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(selectQuery);

            // Controllo se l'email esiste già
            if (!rs.next()) {
                boolean canInsert = true;

                // Se il ruolo è "Capo officina", controlla se esiste già un capo officina
                if ("Capo officina".equals(role)) {
                    ResultSet rsRoleOfficina = statement.executeQuery(checkRoleOfficinaQuery);
                    if (rsRoleOfficina.next()) {
                        // Esiste già un Capo officina, non inserire
                        canInsert = false;
                    }
                    rsRoleOfficina.close();
                }

                // Se il ruolo è "Capo magazzino", controlla se esiste già un capo magazzino
                if ("Capo magazzino".equals(role)) {
                    ResultSet rsRoleMagazzino = statement.executeQuery(checkRoleMagazzinoQuery);
                    if (rsRoleMagazzino.next()) {
                        // Esiste già un Capo magazzino, non inserire
                        canInsert = false;
                    }
                    rsRoleMagazzino.close();
                }

                if (canInsert) {
                    // Inserisci il nuovo dipendente
                    String insertQuery = "INSERT INTO Gestione_Personale (nome, cognome, e_mail, ruolo, password) VALUES ('" + name + "', '" + surname + "', '" + userEmail + "', '" + role + "', '" + userPsw + "')";
                    statement.executeUpdate(insertQuery);
                    isInserted = true;
                }
            }

            rs.close();
            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isInserted;
    }

    /**
     * Il metodo aggiorna i dati di un dipendente esistente nella tabella Gestione_Personale del database.
     * Il processo di aggiornamento segue questi passi:
     * 1. Verifica se l'email aggiornata è già in uso da un altro dipendente per prevenire duplicazioni, escludendo l'email originale del dipendente corrente.
     * 2. Controlla l'unicità dei ruoli di "Capo officina" e "Capo magazzino", impedendo l'assegnazione di questi ruoli se già occupati.
     * 3. Se le verifiche sono superate, procede con l'aggiornamento dei dati del dipendente nel database.
     * 4. Chiude tutte le risorse utilizzate e gestisce le eventuali SQLException visualizzando lo stack trace.
     *
     * Questo metodo stabilisce una connessione al database e inizializza il database eseguendo le istruzioni SQL necessarie prima di procedere con le query.
     * Restituisce true se l'aggiornamento è stato completato con successo, altrimenti false se l'email è già in uso o se il ruolo specifico non può essere assegnato.
     *
     * @param userEmail l'email originale del dipendente, usata per identificare il record da aggiornare.
     * @param newName il nuovo nome del dipendente.
     * @param newSurname il nuovo cognome del dipendente.
     * @param newEmail la nuova email del dipendente, che non deve essere in conflitto con altre esistenti.
     * @param newPassword la nuova password del dipendente.
     * @param newRole il nuovo ruolo del dipendente, con controlli di unicità per i ruoli sensibili.
     * @return boolean true se l'aggiornamento è riuscito, false in caso contrario.
     * @throws SQLException se si verificano errori durante l'interazione con il database.
     */
    public static boolean updateStaff(String userEmail, String newName, String newSurname, String newEmail, String newPassword, String newRole) {
        boolean isUpdated = false;
        String selectQuery = "SELECT * FROM Gestione_Personale WHERE e_mail = '" + userEmail + "'";
        String checkRoleOfficinaQuery = "SELECT * FROM Gestione_Personale WHERE ruolo = 'Capo officina'";
        String checkRoleMagazzinoQuery = "SELECT * FROM Gestione_Personale WHERE ruolo = 'Capo magazzino'";

        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(selectQuery);

            if (rs.next()) {
                String emailCheckQuery = "SELECT * FROM Gestione_Personale WHERE e_mail = '" + newEmail + "' AND e_mail != '" + userEmail + "'";
                ResultSet emailCheckRs = statement.executeQuery(emailCheckQuery);

                if (!emailCheckRs.next()) {
                    if ("Capo officina".equals(newRole)) {
                        ResultSet rsRoleOfficina = statement.executeQuery(checkRoleOfficinaQuery);
                        if (rsRoleOfficina.next()) {
                            System.out.println("Esiste già un Capo officina nel database.");
                            return false;
                        }
                        rsRoleOfficina.close();
                    }

                    if ("Capo magazzino".equals(newRole)) {
                        ResultSet rsRoleMagazzino = statement.executeQuery(checkRoleMagazzinoQuery);
                        if (rsRoleMagazzino.next()) {
                            // Esiste già un Capo magazzino, quindi non eseguire l'aggiornamento
                            System.out.println("Esiste già un Capo magazzino nel database.");
                            return false;
                        }
                        rsRoleMagazzino.close();
                    }

                    String updateQuery = "UPDATE Gestione_Personale SET nome = '" + newName + "', cognome = '" + newSurname + "', e_mail = '" + newEmail + "', password = '" + newPassword + "', ruolo = '" + newRole + "' WHERE e_mail = '" + userEmail + "'";
                    statement.executeUpdate(updateQuery);
                    isUpdated = true;
                } else {
                    System.out.println("L'email " + newEmail + " è già associata a un altro membro del personale.");
                }

                emailCheckRs.close();
            } else {
                System.out.println("Il membro del personale con email " + userEmail + " non esiste nel database.");
            }

            rs.close();
            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }


    /**
     * Il metodo è progettato per rimuovere un dipendente dalla tabella `Gestione_Personale` del database, utilizzando l'email del dipendente come chiave di ricerca.
     * Inizia verificando l'esistenza del dipendente con una query SQL di selezione basata sull'email fornita.
     * Stabilisce una connessione al database e inizializza il database eseguendo le istruzioni SQL specificate nei pacchetti `Query.createDB` e `Query.useDB`.
     * Dopo aver confermato la presenza del record del dipendente, il metodo procede con la cancellazione.
     * Se il record esiste, il dipendente viene eliminato e il metodo restituisce `true`. Se il dipendente non è presente, viene visualizzato un messaggio di errore e il metodo restituisce `false`.
     * Alla conclusione delle operazioni, il metodo chiude tutte le risorse utilizzate, inclusi `ResultSet`, `Statement` e la connessione al database, attraverso l'oggetto `DbHelper`.
     * Durante l'esecuzione, le eccezioni di tipo `SQLException` vengono gestite stampando il tracciato dello stack dell'eccezione tramite `e.printStackTrace()`.
     * Il metodo restituisce un valore booleano che indica se il dipendente è stato effettivamente eliminato dal database.
     *
     * @param userEmail l'email del dipendente da eliminare, usata per identificare il record specifico nel database.
     * @return boolean true se il dipendente è stato eliminato con successo, altrimenti false.
     * @throws SQLException se si verificano errori durante l'interazione con il database.
     */
    public static boolean deleteStaff(String userEmail) {
        boolean isDeleted = false;
        String selectQuery = "SELECT * FROM Gestione_Personale WHERE e_mail = '" + userEmail + "'";

        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(selectQuery);

            // Controlla se il dipendente esiste nel database
            if (rs.next()) {
                // Esegue la cancellazione del dipendente
                String deleteQuery = "DELETE FROM Gestione_Personale WHERE e_mail = '" + userEmail + "'";
                statement.executeUpdate(deleteQuery);
                isDeleted = true;
            } else {
                // Se il dipendente non esiste nel database, visualizza un messaggio di errore
                System.out.println("Il dipendente con email " + userEmail + " non esiste nel database.");
            }

            rs.close();
            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDeleted;
    }


    /**
     * Il metodo è responsabile dell'inserimento di nuovi ricambi nel magazzino del database nella tabella `Magazzino_Ricambi`.
     *
     * Il processo segue questi passaggi:
     * 1. Verifica l'esistenza della tabella `Magazzino_Ricambi` nel database. Se non esiste, la crea utilizzando una query definita in `Query.createTableMagazzinoRicambi`.
     * 2. Controlla se esiste già un ricambio con lo stesso ID. Se l'ID è unico, procede al passo successivo.
     * 3. Verifica se esiste un ricambio con lo stesso nome e marca. Questo passaggio serve a evitare duplicazioni di ricambi con identiche caratteristiche ma ID diverso.
     * 4. Se i controlli sopra indicati non trovano duplicati, il ricambio viene inserito nella tabella utilizzando una query d'inserimento SQL.
     *
     * Alla conclusione delle operazioni, le risorse come `Statement` e la connessione al database vengono chiuse. In caso di eccezioni SQL, queste vengono gestite e tracciate tramite `e.printStackTrace()`.
     * Il metodo restituisce un valore booleano che indica se l'inserimento è avvenuto con successo (`true`) o meno (`false`).
     *
     * @param partId ID del ricambio da inserire.
     * @param partName Nome del ricambio.
     * @param partBrand Marca del ricambio.
     * @param partPrice Prezzo del ricambio espresso come stringa.
     * @param nPart Quantità del ricambio da inserire.
     * @return boolean true se il ricambio è stato inserito con successo, false altrimenti.
     * @throws SQLException se si verificano errori durante l'interazione con il database.
     */
    public static boolean insertPart(String partId, String partName, String partBrand, String partPrice, int nPart) {
        boolean isInserted = false;
        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            // Verifica se la tabella esiste
            DatabaseMetaData dbm = DbHelper.getConnection().getMetaData();
            ResultSet tables = dbm.getTables(null, null, "Magazzino_Ricambi", null);
            if (!tables.next()) {
                // La tabella non esiste, crea la tabella
                statement.executeUpdate(Query.createTableMagazzinoRicambi);
            }

            // Ora la tabella esiste, esegui il controllo dell'ID
            String selectQueryId = "SELECT * FROM Magazzino_Ricambi WHERE id_ricambio = '" + partId + "'";
            ResultSet rsId = statement.executeQuery(selectQueryId);

            if (!rsId.next()) {
                // L'ID non esiste, esegui il controllo del nome e della marca
                String selectQueryNameBrand = "SELECT * FROM Magazzino_Ricambi WHERE nome_ricambio = '" + partName + "' AND marca_ricambio = '" + partBrand + "'";
                ResultSet rsNameBrand = statement.executeQuery(selectQueryNameBrand);

                if (!rsNameBrand.next()) {
                    // Il ricambio con lo stesso nome e marca non esiste, inserisci
                    String insertQuery = "INSERT INTO Magazzino_Ricambi (id_ricambio, nome_ricambio, marca_ricambio, prezzo_ricambio, quantita) VALUES ('" + partId + "', '" + partName + "', '" + partBrand + "', '" + partPrice + "', " + nPart + ")";
                    statement.executeUpdate(insertQuery);
                    isInserted = true;
                }
            }

            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isInserted;
    }

    /**
     * Il metodo è progettato per rimuovere un ricambio dalla tabella `Magazzino_ricambi` del database, utilizzando l'id del ricambio come chiave di ricerca.
     * Inizia verificando l'esistenza del ricambio con una query SQL di selezione basata sull'id fornito.
     * Stabilisce una connessione al database e inizializza il database eseguendo le istruzioni SQL specificate nei pacchetti `Query.createDB` e `Query.useDB`.
     * Dopo aver confermato la presenza del record del ricambio, il metodo procede con la cancellazione.
     * Se il record esiste, il ricambio viene eliminato e il metodo restituisce `true`. Se il ricambio non è presente, viene visualizzato un messaggio di errore e il metodo restituisce `false`.
     * Alla conclusione delle operazioni, il metodo chiude tutte le risorse utilizzate, inclusi `ResultSet`, `Statement` e la connessione al database, attraverso l'oggetto `DbHelper`.
     * Durante l'esecuzione, le eccezioni di tipo `SQLException` vengono gestite stampando il tracciato dello stack dell'eccezione tramite `e.printStackTrace()`.
     * Il metodo restituisce un valore booleano che indica se il ricambio è stato effettivamente eliminato dal database.
     *
     * @param partId l'id del ricambio da eliminare, usato per identificare il record specifico nel database.
     * @return boolean true se il ricambio è stato eliminato con successo, altrimenti false.
     * @throws SQLException se si verificano errori durante l'interazione con il database.
     */
    public static boolean deletePart(String partId) {
        boolean isDeleted = false;
        String selectQuery = "SELECT * FROM magazzino_ricambi WHERE id_ricambio = '" + partId + "'";

        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(selectQuery);

            // Controlla se il dipendente esiste nel database
            if (rs.next()) {
                // Esegue la cancellazione del dipendente
                String deleteQuery = "DELETE FROM magazzino_ricambi WHERE id_ricambio = '" + partId + "'";
                statement.executeUpdate(deleteQuery);
                isDeleted = true;
            } else {
                // Se il dipendente non esiste nel database, visualizza un messaggio di errore
                System.out.println("Il ricambio con id: " + partId + " non esiste nel database.");
            }

            rs.close();
            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDeleted;
    }

    /**
     * Il metodo è progettato per recuperare tutti i dati dei ricambi dalla tabella `magazzino_ricambi` del database e popolarli in un `ObservableList<Part>`.
     * Inizialmente, crea un nuovo oggetto `ObservableList<Part>` per contenere gli oggetti Part.
     * Esegue una query SQL per selezionare gli attributi id_ricambio, nome_ricambio, marca_ricambio, prezzo_ricambio e quantita dalla tabella `magazzino_ricambi`.
     * I risultati della query sono memorizzati in un `ResultSet`. Tramite un ciclo while, i dati estratti dal `ResultSet` sono utilizzati per istanziare oggetti `part`,
     * che sono poi aggiunti all'`ObservableList` `partList`.
     * Infine, la connessione al database e lo statement sono chiusi utilizzando l'oggetto `DbHelper`, e le eventuali eccezioni SQL sono gestite stampando il tracciato dello stack dell'eccezione.
     *
     * @return `ObservableList<Part>` contenente tutti i dati dei ricambi recuperati, che possono essere utilizzati direttamente nell'interfaccia utente dell'applicazione.
     */
    public static ObservableList<Part> getAllParts() {
        ObservableList<Part> partList = FXCollections.observableArrayList();

        String query = "SELECT id_ricambio, nome_ricambio, marca_ricambio, prezzo_ricambio, quantita FROM magazzino_ricambi";

        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                // Assumendo che Part abbia un costruttore che accetta id, name, brand, price, quantity.
                Part part = new Part(
                        rs.getString("id_ricambio"),
                        rs.getString("nome_ricambio"),
                        rs.getString("marca_ricambio"),
                        rs.getString("prezzo_ricambio"),
                        rs.getInt("quantita")
                );
                partList.add(part);
            }

            DbHelper.closeStatement(); // Considera la gestione delle risorse try-with-resources.
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partList;
    }

    /**
     * Il metodo gestisce l'inserimento di nuovi clienti nella tabella `clienti` del database.
     *
     * Il metodo esegue questi passaggi:
     * 1. Verifica l'esistenza di un cliente con la stessa email e numero di telefono per prevenire duplicazioni, utilizzando una query SQL di tipo SELECT.
     * 2. Se non esiste alcun record corrispondente, procede con l'inserimento del nuovo cliente mediante una query SQL di tipo INSERT.
     * 3. Inserisce i dati forniti nelle colonne appropriate della tabella `clienti`.
     *
     * Se l'inserimento è completato con successo, il metodo restituisce `true`, indicando che il cliente è stato aggiunto al database.
     * Se il cliente esiste già o se si verifica un'eccezione SQL durante l'esecuzione delle query, il metodo restituisce `false`.
     * Le eccezioni vengono gestite stampando il tracciato dello stack dell'eccezione tramite `e.printStackTrace()`.
     *
     * Prima di concludere, il metodo chiude la connessione al database e il `Statement` utilizzato, assicurando la liberazione delle risorse e la pulizia delle connessioni.
     *
     * @param clientName Nome del cliente.
     * @param clientSurname Cognome del cliente.
     * @param clientEmail Email del cliente, utilizzata come parte del controllo per evitare duplicazioni.
     * @param clientNumber Numero di telefono del cliente, utilizzato insieme all'email per il controllo delle duplicazioni.
     * @param clientPsw Password del cliente.
     * @return boolean true se il cliente è stato inserito correttamente, false in caso contrario.
     * @throws SQLException se si verificano errori durante l'interazione con il database.
     */
    public static boolean insertClient(String clientName, String clientSurname, String clientEmail, String clientNumber, String clientPsw) {
        boolean isInserted = false;
        String selectQuery = "SELECT * FROM clienti WHERE email = '" + clientEmail + "' AND telefono = '" + clientNumber + "'";


        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(selectQuery);

            if (!rs.next()) {
                String insertQuery = "INSERT INTO clienti (nome, cognome, email, telefono, password) VALUES ('" + clientName + "', '" + clientSurname + "', '" + clientEmail + "', '" + clientNumber + "', '" + clientPsw + "')";
                statement.executeUpdate(insertQuery);
                isInserted = true;
            }

            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isInserted;
    }

    /**
     * Il metodo è progettato per recuperare tutti i dati dei dipendenti dalla tabella `gestione_personale` del database e popolarli in un `ObservableList<Staff>`.
     * Inizialmente, crea un nuovo oggetto `ObservableList<Staff>` per contenere gli oggetti Staff.
     * Esegue una query SQL per selezionare gli attributi id_dipendente, nome, cognome, e_mail e ruolo dalla tabella `gestione_personale`.
     * I risultati della query sono memorizzati in un `ResultSet`. Tramite un ciclo while, i dati estratti dal `ResultSet` sono utilizzati per istanziare oggetti `Staff`,
     * che sono poi aggiunti all'`ObservableList` `staffList`.
     * Infine, la connessione al database e lo statement sono chiusi utilizzando l'oggetto `DbHelper`, e le eventuali eccezioni SQL sono gestite stampando il tracciato dello stack dell'eccezione.
     *
     * @return `ObservableList<Staff>` contenente tutti i dati dei dipendenti recuperati, che possono essere utilizzati direttamente nell'interfaccia utente dell'applicazione.
     */
    public static ObservableList<Staff> getAllStaff() {
        ObservableList<Staff> staffList = FXCollections.observableArrayList();

        String query = "SELECT id_dipendente, nome, cognome, e_mail, ruolo FROM gestione_personale";

        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                // Assumendo che Staff abbia un costruttore che accetta id, nome, cognome, email, ruolo.
                Staff staff = new Staff(
                        rs.getString("id_dipendente"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("e_mail"),
                        rs.getString("ruolo")
                );
                staffList.add(staff);
            }

            DbHelper.closeStatement(); // Considera la gestione delle risorse try-with-resources.
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    /**
     * Il metodo recupera tutti i dati dei clienti dalla tabella `clienti` nel database e li popola in un `ObservableList<Client>`.
     * Non richiede parametri in input, indicando che la funzione è quella di recuperare i dati di ogni cliente senza filtri.
     *
     * Il metodo segue questi passi:
     * 1. Definisce una query SQL che seleziona gli attributi id_cliente, nome, cognome, email e telefono dalla tabella `clienti`.
     * 2. Esegue la query per ottenere i dettagli di ogni cliente memorizzato nel database.
     * 3. Itera sui risultati del `ResultSet`, e per ogni record, crea un nuovo oggetto `Client` utilizzando i dati recuperati.
     * 4. Ogni nuovo oggetto `Client` viene aggiunto all'`ObservableList<Client>`.
     * 5. Una volta completata l'iterazione dei risultati e la popolazione della lista, il metodo chiude lo statement SQL e la connessione al database per evitare perdite di risorse e mantenere la stabilità dell'applicazione.
     * 6. Gestisce le eccezioni SQL catturandole e stampando lo stack trace, il che fornisce dettagli utili per il debugging.
     *
     * Il metodo restituisce un `ObservableList<Client>`, che contiene gli oggetti `Client` rappresentanti tutti i clienti nel database, anche se potenzialmente vuota in caso di eccezioni verificatesi prima di qualsiasi inserimento nella lista.
     *
     * @return `ObservableList<Client>` contenente tutti i clienti nel database.
     */
    public static List<Client> getAllClients() {
        ObservableList<Client> clientList = FXCollections.observableArrayList();

        String query = "SELECT id_cliente, nome, cognome, email, telefono FROM clienti";

        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Client client = new Client(
                        rs.getString("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
                clientList.add(client);
            }

            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientList;
    }

    /**
     * Il metodo recupera i clienti dalla tabella `clienti` del database basandosi sul nome fornito.
     * Inizia creando un `ObservableList<Client>` per raccogliere i clienti che corrispondono al nome specificato.
     * Utilizza una query SQL per filtrare i clienti per nome, sensibile alle maiuscole e minuscole.
     * Dopo aver eseguito la query, il metodo itera sui risultati ottenuti.
     * Per ogni cliente trovato, crea un nuovo oggetto `Client` utilizzando i dati estratti (nome, cognome, email e telefono) e lo aggiunge alla lista.
     * Al termine dell'iterazione, chiude lo statement SQL e la connessione al database, liberando le risorse e garantendo la stabilità del sistema.
     * In caso di eccezioni SQL, il metodo le gestisce stampando il tracciato dello stack dell'eccezione, fornendo dettagli utili per il debugging.
     * Restituisce la lista dei clienti trovati, che può essere utilizzata direttamente nelle interfacce utente dell'applicazione per visualizzare i risultati della ricerca.

     * @param clientName Il nome del cliente da cercare nel database.
     * @return `ObservableList<Client>` contenente i clienti che corrispondono al nome cercato.
     */
    public static List<Client> searchClientsByName(String clientName) {
        ObservableList<Client> clientList = FXCollections.observableArrayList();

        String query = "SELECT nome, cognome, email, telefono FROM clienti WHERE nome = '" + clientName + "'"; /*todo rendere case insensity*/

        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Client client = new Client(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
                clientList.add(client);
            }

            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientList;
    }

    /**
     * Il metodo è progettato per aggiungere i dettagli di un veicolo al database, associando il veicolo a un cliente esistente.
     *
     * Il processo inizia con la verifica dell'esistenza delle tabelle 'Preventivi' e 'Garage' nel database.
     * Se non presenti, queste tabelle vengono create per garantire che il database possa supportare le operazioni richieste senza errori legati alle foreign key.
     * Una volta assicurata l'esistenza delle tabelle, il metodo controlla l'unicità della targa del veicolo nella tabella 'Garage'.
     * Se la targa non è già presente, il veicolo viene inserito nel database utilizzando i dettagli forniti.
     * Questo inserimento include collegamenti all'ID del cliente e agli ID predefiniti per preventivo e dipendente, che sono attualmente impostati staticamente nel metodo.
     * L'inserimento è considerato riuscito se viene completato senza errori, e il metodo restituisce 'true'.
     * In caso contrario, restituisce 'false', ad esempio, se la targa è già presente nel database. Tutte le eccezioni SQL vengono catturate e il loro stack trace viene stampato.
     * Infine, il metodo chiude lo statement e la connessione al database per assicurare la pulizia delle risorse e la stabilità dell'applicazione.
     *
     * @param selectedClient Il cliente a cui il veicolo sarà associato.
     * @param marcaVeicolo La marca del veicolo.
     * @param modelloVeicolo Il modello del veicolo.
     * @param targaVeicolo La targa del veicolo, usata come identificativo unico.
     * @param codTelaioVeicolo Il codice telaio del veicolo.
     * @param diagnosiVeicolo Una descrizione del problema o della condizione del veicolo.
     * @param statoVeicolo Lo stato attuale del veicolo.
     * @return boolean true se il veicolo è stato inserito correttamente, false altrimenti.
     */
    public static boolean insertCar(Client selectedClient, String marcaVeicolo, String modelloVeicolo, String targaVeicolo, String codTelaioVeicolo, String diagnosiVeicolo, String statoVeicolo) {
        boolean isInserted = false;
        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            // Verifica se la tabella 'Preventivi' esiste
            DatabaseMetaData dbm = DbHelper.getConnection().getMetaData();
            ResultSet tables = dbm.getTables(null, null, "Preventivi", null);
            if (!tables.next()) {
                // La tabella non esiste, crea la tabella
                statement.executeUpdate(Query.createTablePreventivi);
            }

            // Verifica se la tabella 'Garage' esiste
            dbm = DbHelper.getConnection().getMetaData();
            tables = dbm.getTables(null, null, "Garage", null);
            if (!tables.next()) {
                // La tabella non esiste, crea la tabella
                statement.executeUpdate(Query.createTableGarage);
            }

            // Ora la tabella esiste, esegui il controllo della targa
            String selectQueryPlate = "SELECT * FROM Garage WHERE targa = '" + targaVeicolo + "'";
            ResultSet rsId = statement.executeQuery(selectQueryPlate);

            if (!rsId.next()) {
                // La targa non esiste, inserisci veicolo
                /* todo sistemare nel nuovo sprint*/
                    String idPreventivo = "1";
                    String idDipendente = "1";

                    String insertQuery = "INSERT INTO Garage (targa, telaio, marca_vettura, modello, id_cliente, id_dipendente, diagnosi, stato_riparazione, id_preventivo) VALUES ('" + targaVeicolo + "', '" + codTelaioVeicolo + "', '" + marcaVeicolo + "', '" + modelloVeicolo + "', '" + selectedClient.getId() + "', '" + idDipendente + "', '" + diagnosiVeicolo + "', '" + statoVeicolo + "', '" + idPreventivo + "')";
                    statement.executeUpdate(insertQuery);
                    isInserted = true;

            }

            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isInserted;
    }

    /**
     * Il metodo è progettato per recuperare e restituire una lista di tutti i veicoli registrati nel database, senza applicare filtri.
     * Questo metodo non richiede parametri, riflettendo la sua capacità di ritrovare i dati di ogni veicolo nella tabella `garage`.
     * La procedura inizia con la formulazione di una query SQL che mira a selezionare campi cruciali come targa, telaio, marca del veicolo, modello, id_cliente, id_dipendente, diagnosi, e stato di riparazione.
     * L'obiettivo è raccogliere le informazioni essenziali per ogni veicolo immagazzinato nel sistema.
     * Dopo aver preparato la query, il metodo procede con l'esecuzione attraverso un oggetto `Statement`, iniziando con le operazioni necessarie per stabilire e configurare la connessione al database.
     * Segue poi l'iterazione sui risultati ottenuti dal `ResultSet`: per ogni record trovato, viene creato un oggetto `Car` utilizzando i dati estratti, che vengono poi aggregati in una `ObservableList<Car>`.
     * Tale lista rappresenta la collezione di veicoli che sarà restituita al termine del metodo.
     * Alla fine, il metodo si occupa della chiusura dello statement e della connessione al database. La chiusura delle risorse è per evitare perdite di memoria e assicurare la chiusura di tutte le connessioni aperte durante l'esecuzione del metodo.
     * Qualora si verifichino eccezioni il metodo le gestisce stampando il loro stack trace.
     * Infine, il metodo restituisce la `ObservableList<Car>`, contenente tutti i veicoli.
     *
     * @return `ObservableList<Car>` che contiene i dettagli di tutti i veicoli recuperati dal database.
     */
    public static List<Car> getAllCars() {
        ObservableList<Car> carlist = FXCollections.observableArrayList();

        String query = "SELECT targa, telaio, marca_vettura, modello, id_cliente, id_dipendente, diagnosi, stato_riparazione FROM garage";

        try {
            Statement statement = DbHelper.getStatement();
            statement.executeUpdate(Query.createDB);
            statement.executeUpdate(Query.useDB);

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Car car = new Car(
                        rs.getString("targa"),
                        rs.getString("telaio"),
                        rs.getString("marca_vettura"),
                        rs.getString("modello"),
                        rs.getString("id_cliente"),
                        rs.getString("id_dipendente"),
                        rs.getString("diagnosi"),
                        rs.getString("stato_riparazione")
                );
                carlist.add(car);
            }

            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carlist;
    }

    /**
     * Questo metodo è utilizzato per inserire un file PDF nella tabella Preventivi del database.
     *
     * Il metodo prende come input i dettagli del preventivo, incluso l'ID del cliente, il percorso del file PDF,
     * la data di creazione, il reparto di creazione e il prezzo totale. Crea una connessione al database, prepara
     * una dichiarazione SQL per l'inserimento e imposta i parametri della query con i valori forniti. Infine,
     * esegue l'inserimento del record nel database.
     *
     *
     * @param clientId L'ID del cliente associato al preventivo.
     *                 Deve essere un valore intero corrispondente a un cliente esistente nel sistema.
     * @param pdfFilePath Il percorso del file PDF da inserire.
     *                    Deve essere un percorso valido a un file PDF esistente sul filesystem.
     * @param creationDate La data di creazione del preventivo.
     *                     Deve essere una data valida e formattata correttamente come java.sql.Date.
     * @param department Il reparto di creazione del preventivo.
     *                   Deve essere una stringa non nulla e non vuota che rappresenta il nome del reparto.
     * @param totalPrice Il prezzo totale del preventivo.
     *                   Deve essere un valore double rappresentante il costo totale del preventivo.
     *
     *
     * @return true se il file PDF è stato inserito con successo nel database, false altrimenti.
     *         Il metodo restituisce true se una o più righe sono state modificate nel database, false se nessuna riga è stata modificata.
     */
    public static boolean insertPDF(int clientId, String pdfFilePath, java.sql.Date creationDate, String department, double totalPrice) {
        boolean isInserted = false;

        String insertQuery = "INSERT INTO preventivi (id_cliente, file_PDF, data_creazione, reparto_di_creazione, prezzo_totale) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = DbHelper.getConnection();
            
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Imposta i valori per la query
            preparedStatement.setInt(1, clientId);
            preparedStatement.setDate(3, creationDate);
            preparedStatement.setString(4, department);
            preparedStatement.setDouble(5, totalPrice);

            // nel caso in cui si specifica il percorso del PDF come "null" allora non viene scritto nulla nella colonna "file_PDF" della tabella "preventivi"


            if(pdfFilePath != null ) {
                File pdfFile = new File(pdfFilePath);
                FileInputStream inputStream = new FileInputStream(pdfFile);

                System.out.println("!!!!!!!!! Contenuto dell'inputstream che carica nella query:\n");
                System.out.println(inputStream);
                preparedStatement.setBinaryStream(2, inputStream, (int) pdfFile.length());

                // Esegui l'inserimento
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    isInserted = true;
                }

                inputStream.close();


            } else {
                preparedStatement.setBinaryStream(2, null, 0);

                // Esegui l'inserimento
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    isInserted = true;
                }
            }

            // Chiudi le risorse
            preparedStatement.close();

            DbHelper.closeStatement();
            DbHelper.closeConnection();

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isInserted;
    }

}



