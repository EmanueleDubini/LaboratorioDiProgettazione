package it.bicocca.laboratoriodiprogettazione_java.entity;

import javafx.beans.property.SimpleStringProperty;

/**
 * Rappresenta un'entità di personale all'interno dell'organizzazione.
 * Ogni membro del personale è caratterizzato da un ID dipendente, nome, cognome, indirizzo email e ruolo.
 * Questa classe utilizza le proprietà di JavaFX per permettere il binding dei dati con l'interfaccia utente,
 * facilitando così l'aggiornamento automatico dell'UI quando i dati cambiano.
 */
public class Staff {

    private SimpleStringProperty id_dipendente;
    private SimpleStringProperty nome;
    private SimpleStringProperty cognome;
    private SimpleStringProperty e_mail;
    private SimpleStringProperty ruolo;

    /**
     * Costruttore per creare un'istanza di Staff.
     *
     * @param id_dipendente L'ID univoco del dipendente.
     * @param nome Il nome del dipendente.
     * @param cognome Il cognome del dipendente.
     * @param e_mail L'indirizzo email del dipendente.
     * @param ruolo Il ruolo del dipendente all'interno dell'organizzazione.
     */
    public Staff(String id_dipendente, String nome, String cognome, String e_mail, String ruolo) {
        this.id_dipendente = new SimpleStringProperty(id_dipendente);
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.e_mail = new SimpleStringProperty(e_mail);
        this.ruolo = new SimpleStringProperty(ruolo);
    }

    /**
     * Imposta l'ID del dipendente.
     *
     * @param id_dipendente id_dipendente Il nuovo ID del dipendente da impostare.
     */
    public void setId_dipendente(String id_dipendente) {
        this.id_dipendente.set(id_dipendente);
    }

    /**
     * Imposta il nome del dipendente.
     *
     * @param nome Il nuovo nome del dipendente da impostare.
     */
    public void setNome(String nome) {
        this.nome.set(nome);
    }

    /**
     * Imposta il cognome del dipendente.
     *
     * @param cognome cognome Il nuovo cognome del dipendente da impostare.
     */
    public void setCognome(String cognome) {
        this.cognome.set(cognome);
    }

    /**
     * Imposta l'indirizzo email del dipendente.
     *
     * @param e_mail e_mail Il nuovo indirizzo email del dipendente da impostare.
     */
    public void setE_mail(String e_mail) {
        this.e_mail.set(e_mail);
    }

    /**
     * Imposta il ruolo del dipendente.
     *
     * @param ruolo Il nuovo ruolo del dipendente da impostare.
     */
    public void setRuolo(String ruolo) {
        this.ruolo.set(ruolo);
    }

    /**
     * Restituisce l'ID del dipendente.
     *
     * @return L'ID del dipendente come stringa.
     */
    public String getId_dipendente() {
        return id_dipendente.get();
    }

    /**
     * Restituisce il nome del dipendente.
     *
     * @return Il nome del dipendente come stringa.
     */
    public String getNome() {
        return nome.get();
    }

    /**
     * Restituisce il cognome del dipendente.
     *
     * @return Il cognome del dipendente come stringa.
     */
    public String getCognome() {
        return cognome.get();
    }

    /**
     * Restituisce l'indirizzo email del dipendente.
     *
     * @return L'indirizzo email del dipendente come stringa.
     */
    public String getE_mail() {
        return e_mail.get();
    }

    /**
     * Restituisce il ruolo del dipendente all'interno dell'organizzazione.
     *
     * @return Il ruolo del dipendente come stringa.
     */
    public String getRuolo() {
        return ruolo.get();
    }

    /**
     * Fornisce una rappresentazione stringa dell'oggetto Staff, includendo l'ID del dipendente, nome, cognome, email e ruolo.
     *
     * @return Una stringa che rappresenta l'oggetto Staff.
     */
    @Override
    public String toString() {
        return "Staff{" +
                "id_dipendente=" + id_dipendente +
                ", nome=" + nome +
                ", cognome='" + cognome + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}

