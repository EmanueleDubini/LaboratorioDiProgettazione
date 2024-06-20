package it.bicocca.laboratoriodiprogettazione_java.entity;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * La classe Tire rappresenta un pneumatico all'interno dell'applicazione, contenendo dettagli
 * quali marca, dimensione, prezzo unitario, sconto applicabile, numero di pezzi, e il prezzo per lo smaltimento (PFU).
 * Fornisce metodi per ottenere e calcolare il prezzo totale dei pneumatici considerando lo sconto,
 * così come il prezzo totale del PFU, sia come valori formattati in stringa che come valori double.
 */
public class Tire {
    String idTire;
    String brandTire;
    String dimensionTire;
    String priceTire;
    String discountTire;
    int nTire;
    String pricePfu;

    /**
     * Costruttore vuoto
     */
    public Tire() {
    }

    /**
     * Costruttore per creare un'istanza di Tire con tutti i dettagli necessari.
     *
     * @param idTire L'identificativo univoco del pneumatico.
     * @param brandTire La marca del pneumatico.
     * @param dimensionTire La dimensione del pneumatico.
     * @param priceTire Il prezzo unitario del pneumatico.
     * @param discountTire Lo sconto applicabile al pneumatico, espresso in percentuale.
     * @param nTire Il numero di pezzi di pneumatici.
     * @param pricePfu Il prezzo per lo smaltimento del pneumatico (PFU).
     */
    public Tire(String idTire, String brandTire, String dimensionTire, String priceTire, String discountTire, int nTire, String pricePfu) {
        this.brandTire = brandTire;
        this.dimensionTire = dimensionTire;
        this.priceTire = priceTire;
        this.discountTire = discountTire;
        this.nTire = nTire;
        this.pricePfu = pricePfu;
        this.idTire = idTire;
    }

    /**
     * Restituisce l'ID del pneumatico.
     *
     * @return L'ID del pneumatico.
     */
    public String getIdTire() {
        return idTire;
    }

    /**
     * Restituisce lo sconto applicato al pneumatico con aggiunta del simbolo percentuale.
     *
     * @return Lo sconto con simbolo percentuale.
     */
    public String getDiscountTire() {
        return discountTire + "%";
    }

    /**
     * Restituisce lo sconto applicato al pneumatico senza modifiche.
     *
     * @return Lo sconto del pneumatico.
     */
    public String getDiscountTireEasy() {
        return discountTire;
    }

    /**
     * Calcola e restituisce il prezzo del singolo pneumatico formattato come stringa.
     * Gestisce la conversione da stringa a double e formatta il risultato nel formato valuta locale.
     *
     * @return Il prezzo del singolo pneumatico formattato.
     */
    public String getPriceTire (){
        Double priceTireDouble = 0.00;
        priceTire = priceTire.replaceAll(",", "\\.");
        try {
            priceTireDouble = Double.parseDouble(priceTire);
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Si è verificato un Errore");
            alert.setContentText("Hai inserito in modo errato il prezzo di uno pneumatico!\nRicorda che devi inserire il prezzo SENZA il simbolo '€'.\nRiprova.");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/org/example/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }
        String priceTireDoubleString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceTireDouble);
        return priceTireDoubleString;
    }

    /**
     * Restituisce il prezzo unitario del pneumatico senza modifiche.
     *
     * @return Il prezzo unitario del pneumatico.
     */
    public String getPriceTireEasy() {
        return priceTire;
    }

    /**
     * Restituisce il numero totale di pneumatici.
     *
     * @return Il numero totale di pneumatici come stringa.
     */
    public String getNumberTire(){
        return Integer.toString(nTire);
    }

    /**
     * Restituisce la marca del pneumatico.
     *
     * @return La marca del pneumatico.
     */
    public String getBrandTire() {
        return brandTire;
    }

    /**
     * Restituisce la dimensione del pneumatico.
     *
     * @return La dimensione del pneumatico.
     */
    public String getDimensionTire() {
        return dimensionTire;
    }

    /**
     * Calcola e restituisce il prezzo PFU (Prezzo per lo smaltimento) del singolo pneumatico formattato come stringa.
     *
     * @return Il prezzo PFU del singolo pneumatico formattato.
     */
    public String getPricePfu() {
        Double pricePfuDouble = 0.00;
        try {
            pricePfuDouble = Double.parseDouble(pricePfu);
        }catch(NumberFormatException e){
            System.err.println("Non è stato inserito il prezzo PFU degli pneumatici");
        }
        String prezzoPfuDoubleString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(pricePfuDouble);
        return prezzoPfuDoubleString;
    }

    /**
     * Restituisce il prezzo PFU del pneumatico senza modifiche.
     *
     * @return Il prezzo PFU del pneumatico.
     */
    public String getPricePfuEasy() {
        return pricePfu;
    }

    /**
     * Calcola e restituisce il prezzo totale dei pneumatici, considerando il numero di pezzi e lo sconto applicato.
     * Formatta il risultato nel formato valuta locale.
     *
     * @return Il prezzo totale dei pneumatici formattato come stringa.
     */
    public String getPriceTotTire() {
        Double priceTotTire = 0.00;
        Double priceDoubleTire = 0.00;
        Double dicountFloatTire = 0.00;

        if(priceTire != ""){
            priceTire = priceTire.replaceAll(",", "\\.");

            if(discountTire != ""){
                discountTire = discountTire.replaceAll("%", "");
                try {
                    dicountFloatTire = Double.parseDouble(discountTire);
                }catch(NumberFormatException e){

                }
            }try{
                priceDoubleTire = Double.parseDouble(priceTire);
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("OH NO!");
                alert.setContentText("Il programma ha flatlineato.\nC'è stato un errore nella conversione del prezzo dello pneumatico.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/org/example/css/style.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");
                alert.showAndWait();
            }

            priceTotTire = priceDoubleTire * nTire;
            Double discount = (dicountFloatTire*priceTotTire)/100;
            priceTotTire = priceTotTire - discount;
        }
        String priceTotTireString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceTotTire);
        priceTotTireString = priceTotTireString.replaceAll("\\."," ");

        return priceTotTireString;
    }

    /**
     * Calcola e restituisce il prezzo totale del PFU per tutti i pneumatici.
     *
     * @return Il prezzo totale del PFU per tutti i pneumatici come stringa.
     */
    public String getPriceTotPfu() {
        Double priceTotPfu = 0.00;
        Double priceFloatPfu = 0.00;

        if(pricePfu != "") {
            pricePfu = pricePfu.replaceAll(",", "\\.");
            try{
                priceFloatPfu = Double.parseDouble(pricePfu);
            }catch (NumberFormatException e){
                System.out.println("PFU non inserito");
            }
            priceTotPfu = nTire * priceFloatPfu;
        }

        String priceTotPfuString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceTotPfu);
        priceTotPfuString = priceTotPfuString.replaceAll("\\.",",");

        return  String.valueOf(priceTotPfu);
    }

    /**
     * Calcola e restituisce il prezzo totale dei pneumatici in formato double.
     *
     * @return Il prezzo totale dei pneumatici come valore double.
     */
    public Double getPriceTotTireDouble() {
        String priceTotTireString = getPriceTotTire();
        priceTotTireString = priceTotTireString.replaceAll("€", "");
        priceTotTireString = priceTotTireString.replaceAll(",", "\\.");
        priceTotTireString = priceTotTireString.replaceAll(" ", "");
        priceTotTireString = priceTotTireString.substring(0, priceTotTireString.length() - 1);

        Double priceTotTireDouble = 0.00;

        try{
            priceTotTireDouble = Double.parseDouble(priceTotTireString);
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("OH NO!");
            alert.setContentText("Il programma ha flatlineato.\nC'è stato un errore nel calcolo del prezzo totale degli pneumatici.");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/org/example/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }
        return priceTotTireDouble;
    }

    /**
     * Calcola e restituisce il prezzo totale del PFU per tutti i pneumatici in formato double.
     *
     * @return Il prezzo totale del PFU per tutti i pneumatici come valore double.
     */
    public Double getPriceTotPfuDouble() {
        String priceTotPfuString = getPriceTotPfu();
        priceTotPfuString = priceTotPfuString.replaceAll("€", "");
        priceTotPfuString = priceTotPfuString.replaceAll(",", "\\.");
        priceTotPfuString = priceTotPfuString.replaceAll(" ", "");

        Double priceTotPfuDouble = 0.00;

        try{
            priceTotPfuDouble = Double.parseDouble(priceTotPfuString);

        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("OH NO!");
            alert.setContentText("Il programma ha flatlineato.\nC'è stato un errore nl calcolo del prezzo totale del PFU.");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/org/example/css/style.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            alert.showAndWait();
        }
        return priceTotPfuDouble;
    }

    /**
     * Imposta l'ID del pneumatico.
     *
     * @param idTire L'ID univoco del pneumatico.
     */
    public void setIdTire(String idTire) {
        this.idTire = idTire;
    }

    /**
     * Imposta la marca del pneumatico.
     *
     * @param brandTire La marca del pneumatico da impostare.
     */
    public void setBrandTire(String brandTire) {
        this.brandTire = brandTire;
    }

    /**
     * Imposta la dimensione del pneumatico.
     *
     * @param dimensionTire La dimensione del pneumatico da impostare.
     */
    public void setDimensionTire(String dimensionTire) {
        this.dimensionTire = dimensionTire;
    }

    /**
     * Imposta il prezzo del pneumatico.
     *
     * @param priceTire Il prezzo del pneumatico da impostare.
     */
    public void setPriceTire(String priceTire) {
        this.priceTire = priceTire;
    }

    /**
     * Imposta lo sconto applicato al pneumatico.
     *
     * @param discountTire Lo sconto da applicare al pneumatico.
     */
    public void setDiscountTire(String discountTire) {
        this.discountTire = discountTire;
    }

    /**
     * Imposta il numero di pneumatici.
     *
     * @param nTire Il numero di pneumatici da impostare.
     */
    public void setnTire(int nTire) {
        this.nTire = nTire;
    }

    /**
     * Imposta il prezzo per lo smaltimento (PFU) del pneumatico.
     *
     * @param pricePfu Il prezzo PFU del pneumatico da impostare.
     */
    public void setPricePfu(String pricePfu) {
        this.pricePfu = pricePfu;
    }
}