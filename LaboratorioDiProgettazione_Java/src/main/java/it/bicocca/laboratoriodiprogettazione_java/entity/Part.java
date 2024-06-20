package it.bicocca.laboratoriodiprogettazione_java.entity;

/**
 * La classe Part rappresenta un ricambio con informazioni di base come id, nome, marca e quantità.
 */
public class Part {
    /**
     * L'id del ricambio.
     */
    private String id;

    /**
     * Il nome del ricambio.
     */
    private String name;

    /**
     * La marca del cliente.
     */
    private String brand;

    /**
     * Il prezzo del ricambio.
     */
    private String price;

    /**
     * La quantità del ricambio disponibile in magazzino.
     */
    private int quantity;

    /**
     * Costruisce un nuovo oggetto Part.
     */
    public Part() {
    }

    /**
     * Costruisce un nuovo oggetto Client.
     *
     * @param id        L'id del ricambio.
     * @param name      Il nome del ricambio.
     * @param brand     Il brand del ricambio.
     * @param price     il prezzo del ricambio.
     * @param quantity  La quantità del ricambio disponibile.
     */
    public Part(String id, String name, String brand, String price, int quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Ottiene l'id del ricambio
     *
     * @return l'id del ricambio
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta l'id del ricambio
     *
     * @param id l'id del ricambio
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Ottiene il nome del ricambio
     *
     * @return il nome del ricambio
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del ricambio
     *
     * @param name il nome del ricambio
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ottiene la marca del ricambio
     *
     * @return la marca del ricambio
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Imposta la marca del ricambio
     *
     * @param brand la marca del ricambio
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Ottiene il prezzo del ricambio
     *
     * @return il prezzo del ricambio
     */
    public String getPrice() {
        return price;
    }

    /**
     * Imposta il prezzo del ricambio
     *
     * @param price il prezzo del ricambio
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Ottiene la quantità del ricambio
     *
     * @return la quantità del ricambio
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Imposta la quantità del ricambio
     *
     * @param quantity la quantità del ricambio
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
