package it.bicocca.laboratoriodiprogettazione_java.entity;

import java.util.Objects;

/**
 * Rappresenta un'entità di veicolo all'interno del sistema.
 * Ogni veicolo è caratterizzato da una targa, un codice telaio, marca e modello del veicolo,
 * un ID cliente, un ID dipendente, una diagnosi e uno stato di riparazione.
 */
public class Car {

    /** La targa del veicolo */
    private String targa;

    /** Il codice telaio del veicolo */
    private String telaio;

    /** La marca del veicolo */
    private String marcaVettura;

    /** Il modello del veicolo */
    private String modello;

    /** L'ID del cliente associato al veicolo */
    private String idCliente;

    /** L'ID del dipendente associato al veicolo */
    private String idDipendente;

    /** La diagnosi del veicolo */
    private String diagnosi;

    /** Lo stato di riparazione del veicolo */
    private String statoRiparazione;

    /**
     * Costruttore vuoto per la classe Car.
     */
    public Car() {
    }

    /**
     * Costruttore per creare un'istanza di Car con i parametri principali.
     *
     * @param targa            La targa del veicolo
     * @param telaio           Il codice telaio del veicolo
     * @param marcaVettura     La marca del veicolo
     * @param modello          Il modello del veicolo
     * @param idDipendente     L'ID del dipendente associato al veicolo
     * @param diagnosi         La diagnosi del veicolo
     * @param statoRiparazione Lo stato di riparazione del veicolo
     */
    public Car(String targa, String telaio, String marcaVettura, String modello, String idDipendente, String diagnosi, String statoRiparazione) {
        this.targa = targa;
        this.telaio = telaio;
        this.marcaVettura = marcaVettura;
        this.modello = modello;
        this.idDipendente = idDipendente;
        this.diagnosi = diagnosi;
        this.statoRiparazione = statoRiparazione;
    }

    /**
     * Costruttore per creare un'istanza di Car con tutti i parametri.
     *
     * @param targa            La targa del veicolo
     * @param telaio           Il codice telaio del veicolo
     * @param marcaVettura     La marca del veicolo
     * @param modello          Il modello del veicolo
     * @param idCliente        L'ID del cliente associato al veicolo
     * @param idDipendente     L'ID del dipendente associato al veicolo
     * @param diagnosi         La diagnosi del veicolo
     * @param statoRiparazione Lo stato di riparazione del veicolo
     */
    public Car(String targa, String telaio, String marcaVettura, String modello, String idCliente, String idDipendente, String diagnosi, String statoRiparazione) {
        this.targa = targa;
        this.telaio = telaio;
        this.marcaVettura = marcaVettura;
        this.modello = modello;
        this.idCliente = idCliente;
        this.idDipendente = idDipendente;
        this.diagnosi = diagnosi;
        this.statoRiparazione = statoRiparazione;
    }

    /**
     * Restituisce la targa del veicolo.
     *
     * @return La targa del veicolo
     */
    public String getTarga() {
        return targa;
    }

    /**
     * Imposta la targa del veicolo.
     *
     * @param targa La nuova targa del veicolo
     */
    public void setTarga(String targa) {
        this.targa = targa;
    }

    /**
     * Restituisce il codice telaio del veicolo.
     *
     * @return Il codice telaio del veicolo
     */
    public String getTelaio() {
        return telaio;
    }

    /**
     * Imposta il codice telaio del veicolo.
     *
     * @param telaio Il nuovo codice telaio del veicolo
     */
    public void setTelaio(String telaio) {
        this.telaio = telaio;
    }

    /**
     * Restituisce la marca del veicolo.
     *
     * @return La marca del veicolo
     */
    public String getMarcaVettura() {
        return marcaVettura;
    }

    /**
     * Imposta la marca del veicolo.
     *
     * @param marcaVettura La nuova marca del veicolo
     */
    public void setMarcaVettura(String marcaVettura) {
        this.marcaVettura = marcaVettura;
    }

    /**
     * Restituisce il modello del veicolo.
     *
     * @return Il modello del veicolo
     */
    public String getModello() {
        return modello;
    }

    /**
     * Imposta il modello del veicolo.
     *
     * @param modello Il nuovo modello del veicolo
     */
    public void setModello(String modello) {
        this.modello = modello;
    }

    /**
     * Restituisce l'ID del cliente associato al veicolo.
     *
     * @return L'ID del cliente associato al veicolo
     */
    public String getIdCliente() {
        return idCliente;
    }

    /**
     * Imposta l'ID del cliente associato al veicolo.
     *
     * @param idCliente Il nuovo ID del cliente associato al veicolo
     */
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Restituisce l'ID del dipendente associato al veicolo.
     *
     * @return L'ID del dipendente associato al veicolo
     */
    public String getIdDipendente() {
        return idDipendente;
    }

    /**
     * Imposta l'ID del dipendente associato al veicolo.
     *
     * @param idDipendente Il nuovo ID del dipendente associato al veicolo
     */
    public void setIdDipendente(String idDipendente) {
        this.idDipendente = idDipendente;
    }

    /**
     * Restituisce la diagnosi del veicolo.
     *
     * @return La diagnosi del veicolo
     */
    public String getDiagnosi() {
        return diagnosi;
    }

    /**
     * Imposta la diagnosi del veicolo.
     *
     * @param diagnosi La nuova diagnosi del veicolo
     */
    public void setDiagnosi(String diagnosi) {
        this.diagnosi = diagnosi;
    }

    /**
     * Restituisce lo stato di riparazione del veicolo.
     *
     * @return Lo stato di riparazione del veicolo
     */
    public String getStatoRiparazione() {
        return statoRiparazione;
    }

    /**
     * Imposta lo stato di riparazione del veicolo.
     *
     * @param statoRiparazione Il nuovo stato di riparazione del veicolo
     */
    public void setStatoRiparazione(String statoRiparazione) {
        this.statoRiparazione = statoRiparazione;
    }

    /**
     * Verifica se due oggetti Car sono uguali confrontando i loro attributi.
     *
     * @param o L'oggetto da confrontare con l'istanza corrente di Car
     * @return true se i due oggetti Car sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(targa, car.targa) && Objects.equals(telaio, car.telaio) && Objects.equals(marcaVettura, car.marcaVettura) && Objects.equals(modello, car.modello) && Objects.equals(idCliente, car.idCliente) && Objects.equals(idDipendente, car.idDipendente) && Objects.equals(diagnosi, car.diagnosi) && Objects.equals(statoRiparazione, car.statoRiparazione);
    }

    /**
     * Fornisce una rappresentazione in forma di stringa dell'oggetto Car.
     *
     * @return Una stringa che rappresenta l'oggetto Car e i suoi attributi
     */
    @Override
    public String toString() {
        return "Car{" +
                "targa='" + targa + '\'' +
                ", telaio='" + telaio + '\'' +
                ", marcaVettura='" + marcaVettura + '\'' +
                ", modello='" + modello + '\'' +
                ", idCliente='" + idCliente + '\'' +
                ", idDipendente='" + idDipendente + '\'' +
                ", diagnosi='" + diagnosi + '\'' +
                ", statoRiparazione='" + statoRiparazione + '\'' +
                '}';
    }
}
