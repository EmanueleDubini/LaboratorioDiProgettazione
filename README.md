## Laboratorio di Progettazione

Questo repository contiene il progetto di Laboratorio di Progettazione del corso di Informatica dell'Università degli Studi di Milano - Bicocca, anno accademico 2023-2024.

Il progetto si propone di sviluppare un sistema software per la gestione di concessionarie automobilistiche, denominato *GroupeR-Toolkit* incudendo un'applicazione desktop e una web per i clienti.

Il progetto originale è presente su GitLab all'indirizzo: https://gitlab.com/academicunimib/laboratoriodiprogettazione

---

**Università Degli Studi Di Milano - Bicocca @Unimib AA.2023-2024**

BANCORA Davide       | 905588

DONATO Benedetta     | 905338

DUBINI Emanuele      | 904078 

________________
Agile Board: https://trello.com/b/zpyNZdA6/laboratorio-di-progettazione

### Architettura del Progetto

Il sistema *GroupeR-Toolkit* è strutturato in due parti chiave, con l'obiettivo di ottimizzare la gestione interna delle concessionarie automobilistiche e, al tempo stesso, di potenziare l'interazione con i clienti attraverso una applicazione web.\
La soluzione software si articola in due componenti principali:
  
1. **Applicazione Desktop:** Ideata per essere utilizzata all'interno delle concessionarie, questa applicazione è il fulcro operativo per la gestione dell'officina e del magazzino. Offre un'interfaccia intuitiva che permette ai meccanici di inserire e gestire le automobili in officina e ai responsabili del magazzino di utilizzare strumenti avanzati per il monitoraggio delle scorte e la generazione di preventivi per i clienti.

2. **Applicazione Web per Clienti:** Questo portale online estende l'esperienza del cliente oltre i confini fisici della concessionaria. Attraverso questa piattaforma, i clienti possono consultare i preventivi e monitorare in tempo reale lo stato di riparazione dei propri veicoli, contribuendo a costruire un rapporto di fiducia e trasparenza con i clienti e garantendo un servizio assistenziale proattivo e personalizzato.

3. **Database:** Fondamentale per la raccolta, l'archiviazione e l'analisi dei dati necessari sia per l'applicazione desktop che per quella web.

<p align="center">
  <img src="Schema_ER/Architettura_Alto_Livello.jpeg" alt="Architettura Alto Livello">
</p>

### Descrizione delle tecnologie da utilizzare 

Per la realizzazione del progetto si prevede di implementare l'applicazione desktop utilizzando il linguaggio di programmazione Java integrando Maven per la gestione delle dipendenze e delle librerie esterne. Per quanto riguarda la GUI, si prevede di utilizzare il software JavaFX. Inoltre, é previsto l'utilizzo di un database SQL, accessibile via rete, che permette di eseguire operazioni di archiviazione e recupero dei dati in maniera centralizzata.\
Per quanto riguarda l'applicazione web, si prevede di utilizzare il linguaggio HTML, CSS e JavaScript per creare interfacce web responsive con l'ausilio del framework Bootstrap.\
Per il backend sarà impiegato il framework web Django in linguaggio Python.

### Schema ER

Per garantire una comprensione completa della struttura dei dati gestiti all'interno dell'applicazione, è essenziale consultare lo schema ER (Entity-Relationship) allegato.\
Questo schema fornisce una rappresentazione visiva delle entità del database e delle relazioni tra di esse, facilitando così l'identificazione delle dipendenze e delle interconnessioni chiave.\
https://gitlab.com/academicunimib/laboratoriodiprogettazione/-/blob/f0b8c52883d9d029b46d467a6aa6eaf667c788b8/LaboratorioDiProgettazione_Java/Schema_ER/SchemaER.jpg

### Test di accettazione

Questo paragrafo fornisce una panoramica sui test di accettazione del progetto e sul loro ruolo nella verifica della conformità del software ai requisiti definiti.
Per consultare i casi di test di accettazione completi, inclusi i passaggi di test, i dati di input previsti e i risultati attesi, fare riferimento al seguente foglio Excel:
https://unimibit-my.sharepoint.com/personal/d_bancora_campus_unimib_it/_layouts/15/guestaccess.aspx?share=Ed0HEUeWftBNtJExTMKLNp8BHPm50nVvgCmE_WhY6y02zQ&e=FuoGaX


