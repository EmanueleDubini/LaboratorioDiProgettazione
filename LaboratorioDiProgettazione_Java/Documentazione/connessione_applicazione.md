## Connessione dell’applicazione desktop al database
L’implementazione della connessione dell’applicazione desktop al database avviene tramite la classe DbHelper.\
Essa è un'utilità progettata per facilitare la gestione delle connessioni a un database MySQL.\
Si concentra sulla gestione delle risorse di connessione, incapsulando la logica necessaria per connettersi a un database MySQL ospitato su Azure e per chiudere tali connessioni in modo sicuro.\
La configurazione del database include l'URL del Database, che è l'indirizzo del server MySQL ospitato su Azure, incluso il numero di porta (3306) e il nome del database al quale connettersi.\
Viene anche specificato l'Utente del Database e la Password del Database per l'autenticazione.\
All’interno della classe DbHelper presenti i seguenti metodi pubblici:
* *getConnection()* stabilisce una connessione al database MySQL utilizzando le credenziali e l'URL forniti, ritornando l'oggetto Connection che rappresenta la connessione al database.\
Questo metodo cattura e stampa le eccezioni SQLException che possono verificarsi durante il tentativo di connessione.
* *closeConnection()* chiude la connessione al database se aperta, e resetta la variabile di connessione a null. Questo metodo può lanciare l'eccezione SQLException se si verifica un errore durante la chiusura della connessione.
* *getStatement()* crea e restituisce un oggetto Statement per inviare comandi SQL al database. Se l'oggetto Statement non esiste, stabilisce una nuova connessione prima di crearne uno. Questo metodo può lanciare l'eccezione SQLException se si verifica un errore durante la creazione dello Statement.
* *closeStatement()* chiude l'oggetto Statement se esistente, chiude la connessione al database e resetta la variabile statement a null. Questo metodo può lanciare l'eccezione SQLException se si verifica un errore durante la chiusura dello Statement.
  
Tramite l’utilizzo di questi metodi la classe DbHelper offre un'interfaccia semplice ed efficace per la gestione delle connessioni a un database MySQL, rendendo il codice che richiede l'accesso al database più pulito e più facile da mantenere. 
