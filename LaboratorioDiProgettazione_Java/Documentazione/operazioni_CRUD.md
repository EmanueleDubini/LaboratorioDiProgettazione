## Operazioni CRUD per creazioni tabelle

La classe Query, presente nel package db, contiene una serie di stringhe che rappresentano query SQL predefinite per la creazione e l'eliminazione di tabelle nel database.\
Ogni stringa rappresenta una specifica query SQL per creare o eliminare una tabella nel database. I metodi principali includono la definizione di query SQL per la creazione e l'eliminazione di diverse tabelle nel database, tra cui *Clienti*, *Gestione_Personale*, *Magazzino_Ricambi*, *Magazzino_Pneumatici*, *Preventivi* e *Garage*.\
Il nome di ogni metodo è autoesplicativo, ad esempio createTableClienti per la creazione della tabella dei clienti e deleteTableMagazzinoRicambi per l'eliminazione della tabella del magazzino dei ricambi.
Le query sono definite come costanti statiche per consentirne un facile accesso e utilizzo in altre classi del codice.\
Inoltre, all’interno della classe, sono presenti commenti esplicativi che chiariscono il significato di ciascuna colonna delle tabelle e le relazioni tra di esse, come le chiavi esterne che fanno riferimento ad altre tabelle nel database.
