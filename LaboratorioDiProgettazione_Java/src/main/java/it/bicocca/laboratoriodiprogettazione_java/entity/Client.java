package it.bicocca.laboratoriodiprogettazione_java.entity;

import java.util.Objects;

/**
 * La classe Client rappresenta un cliente con informazioni di base come nome, cognome, email e numero di telefono.
 */
public class Client {

    /**
     * L'id del cliente.
     */
    private String id;

    /**
     * Il nome del cliente.
     */
    private String nome;

    /**
     * Il cognome del cliente.
     */
    private String cognome;

    /**
     * L'indirizzo email del cliente.
     */
    private String email;


    /**
     * Il numero di telefono del cliente.
     */
    private String telefono;

    /**
     * Costruisce un nuovo oggetto Client.
     */
    public Client() {
    }

    /**
     * Costruisce un nuovo oggetto Client.
     *
     * @param id     L'id del cliente.
     * @param nome     Il nome del cliente.
     * @param cognome  Il cognome del cliente.
     * @param email    L'indirizzo email del cliente.
     * @param telefono Il numero di telefono del cliente.
     */
    public Client(String id, String nome, String cognome, String email, String telefono) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Costruisce un nuovo oggetto Client.
     *
     * @param nome     Il nome del cliente.
     * @param cognome  Il cognome del cliente.
     * @param email    L'indirizzo email del cliente.
     * @param telefono Il numero di telefono del cliente.
     */
    public Client(String nome, String cognome, String email, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Verifica se questo cliente è uguale a un altro oggetto.
     *
     * @param o L'oggetto da confrontare con questo cliente.
     * @return true se gli oggetti sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(nome, client.nome) && Objects.equals(cognome, client.cognome) && Objects.equals(email, client.email) && Objects.equals(telefono, client.telefono);
    }

    /**
     * Ottiene l'id del cliente.
     *
     * @return L'id del cliente.
     */
    public String getId() {
        return id;
    }

    /**
     * Ottiene il nome del cliente.
     *
     * @return Il nome del cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Ottiene il cognome del cliente.
     *
     * @return Il cognome del cliente.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Ottiene l'indirizzo email del cliente.
     *
     * @return L'indirizzo email del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ottiene il numero di telefono del cliente.
     *
     * @return Il numero di telefono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta l'id del cliente.
     *
     * @param id L'id del cliente.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Imposta il nome del cliente.
     *
     * @param nome Il nome del cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *  Imposta il cognome del cliente.
     *
     * @param cognome Il cognome del cliente.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Imposta l'indirizzo email del cliente.
     *
     * @param email L'indirizzo email del cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Imposta il numero di telefono del cliente.
     *
     * @param telefono Il numero di telefono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Restituisce una rappresentazione in formato stringa del cliente.
     *
     * @return Una rappresentazione in formato stringa del cliente.
     */
    @Override
    public String toString() {
        return "Client{" +
                "ID='" + id + '\'' +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
