## Pipeline
La pipeline configurata è stata progettata specificamente per migliorare il processo di sviluppo e verifica di applicazioni Java, avvalendosi di Maven come principale strumento di automazione.\
Essa gioca un ruolo cruciale nel ciclo di sviluppo del software, assicurando l'integrità del codice e prevenendo l'introduzione di errori o comportamenti non desiderati attraverso i test automatici.\
La pipeline è articolata in due fasi principali, denominate "build" e "verify".\
Durante la fase "build", viene compilata l'applicazione Java, un passaggio per preparare il software ai test di verifica. Questa fase si attiva automaticamente quando viene effettuato un push sul branch di sviluppo, integrando così il processo di build nel flusso di lavoro di sviluppo quotidiano.\
Per la verifica, la fase "verify" gestisce l'esecuzione dei test di unità e di integrazione.\
Utilizzando il plugin Maven Surefire, la pipeline è configurata per eseguire test di unità, mentre il plugin Maven Failsafe è riservato ai test di integrazione.\
I test di integrazione sono selezionati in base ai file che terminano con "IT" o "_IT.java", garantendo che ogni componente interagisca correttamente con gli altri.

L'ambiente di esecuzione è stabilizzato utilizzando una versione specifica di Maven, `maven:3.8.6`, per evitare discrepanze causate da aggiornamenti dell'ambiente.\
Inoltre, sono state definite variabili d'ambiente globali e configurazioni di cache per ottimizzare la performance e la consistenza delle compilazioni e dei test.

Questa strutturazione permette non solo di garantire un alto standard di qualità del software, ma anche di integrare la verifica direttamente nel ciclo di sviluppo, facilitando così il continuo miglioramento del prodotto senza interruzioni significative del workflow di sviluppo.\
Qui di seguito il link al codice della pipeline:\
https://gitlab.com/emanuele.dubini/laboratoriodiprogettazione/-/blob/8a6f89c7f428d3c44b68b876b8f9a15644cb99f4/.gitlab-ci.yml)
