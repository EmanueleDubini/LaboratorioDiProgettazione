package it.bicocca.laboratoriodiprogettazione_java.entity;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * La classe Rim rappresenta un cerchio nell'applicazione, conservando le informazioni relative
 * al tipo, al numero, al prezzo e allo sconto dei cerchi. Offre metodi per ottenere e manipolare
 * queste informazioni in formati specifici, adatti sia per calcoli che per visualizzazione.
 */
public class Rim {
    String typeRim;
    int nRim;
    String priceRim;
    String discountRim;

    /**
     * Costruttore vuoto
     */
    public Rim() {
    }

    /**
     * Costruisce un oggetto Rim con i dettagli specificati.
     *
     * @param typeRim     Tipo del cerchio.
     * @param nRim        Quantità di cerchi.
     * @param priceRim    Prezzo di un singolo cerchio.
     * @param discountRim Sconto applicato al cerchio.
     */
    public Rim(String typeRim, int nRim, String priceRim, String discountRim) {
        this.typeRim = typeRim;
        this.nRim = nRim;
        this.priceRim = priceRim;
        this.discountRim = discountRim;
    }

    /**
     * Restituisce il tipo del cerchio.
     *
     * @return Tipo del cerchio.
     */
    public String getTypeRim() {
        return typeRim;
    }

    /**
     * Restituisce lo sconto applicato al cerchio con il simbolo percentuale.
     *
     * @return Sconto del cerchio con simbolo percentuale.
     */
    //RITORNA LO SCONTO DEL SINGOLO CERCHIO
    public String getDiscountRim() {
        return discountRim + "%";
    }

    /**
     * Restituisce lo sconto applicato al cerchio senza modifiche.
     *
     * @return Sconto del cerchio.
     */
    public String getDiscountRimEasy() {
        return discountRim;
    }

    /**
     * Restituisce il prezzo di un singolo cerchio, formattato in euro.
     *
     * @return Prezzo del cerchio formattato.
     */
    //RITORNA IL PREZZO DEL SINGOLO CERCHIO
    public String getPriceRim(){
        Double priceRimDouble = 0.00;
        priceRim = priceRim.replaceAll(",", "\\.");
        try {
            priceRimDouble = Double.parseDouble(priceRim);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        String priceRimDoubleString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceRimDouble);
        return priceRimDoubleString;
    }

    /**
     * Restituisce il prezzo di un singolo cerchio senza modifiche.
     *
     * @return Prezzo del cerchio.
     */
    //RITORNA IL PREZZO DEL SINGOLO CERCHIO NON MODIFICATO
    public String getPriceRimEasy() {
        return priceRim;
    }

    /**
     * Restituisce il numero dei cerchi come stringa.
     *
     * @return Numero dei cerchi.
     */
    //RITORNA IL NUMERO DEI CERCHI
    public String getNumberRim(){
        return Integer.toString(nRim);
    }

    /**
     * Calcola e restituisce il prezzo totale dei cerchi, tenendo conto dello sconto e formattando il risultato in euro.
     *
     * @return Prezzo totale dei cerchi formattato.
     */
    //RITORNA IL PREZZO TOTALE DEI CERCHI
    public String getPriceTotRim() {
        Double priceTotRim = 0.00;
        Double priceDoubleRim = 0.00;
        Double discountDoubleRim = 0.00;

        if(priceRim != "") {
            priceRim = priceRim.replaceAll(",", "\\.");

            if(discountRim != ""){
                discountRim = discountRim.replaceAll("%", "");
                try {
                    discountDoubleRim = Double.parseDouble(discountRim);
                }catch (NumberFormatException e){
                    System.err.println("Problema di conversione dello sconto");
                }
            }
            try{
                priceDoubleRim = Double.parseDouble(priceRim);
            }catch (NumberFormatException e){
                System.err.println("Problema di conversione dello del prezzo del cerchio");
            }
            priceTotRim = (nRim *priceDoubleRim);
            Double discount = (discountDoubleRim*priceTotRim)/100;
            priceTotRim = priceTotRim - discount;
        }
        String priceTotRimString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceTotRim);
        priceTotRimString = priceTotRimString.replaceAll("\\.", " ");
        return priceTotRimString;
    }

    /**
     * Calcola il prezzo totale dei cerchi come valore {@code Double}.
     *
     * @return Prezzo totale dei cerchi come valore double.
     */
    //CALCOLA IL PREZZO TOTALE DEI CERCHI IN DOUBLE
    public Double getPriceTotRimDouble() {
        String priceTotRimString = getPriceTotRim();
        priceTotRimString = priceTotRimString.replaceAll("€", "");
        priceTotRimString = priceTotRimString.replaceAll(",", "\\.");
        priceTotRimString = priceTotRimString.substring(0, priceTotRimString.length() - 1);
        priceTotRimString = priceTotRimString.replaceAll(" ","");

        Double priceTotRimDouble = 0.00;

        try{
            priceTotRimDouble = Double.parseDouble(priceTotRimString);
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return priceTotRimDouble;
    }

    /**
     * Imposta il tipo del cerchio.
     *
     * @param typeRim Il nuovo tipo del cerchio da impostare.
     */
    public void setTypeRim(String typeRim) {
        this.typeRim = typeRim;
    }

    /**
     * Imposta il numero di cerchi.
     *
     * @param nRim Il nuovo numero di cerchi da impostare.
     */
    public void setnRim(int nRim) {
        this.nRim = nRim;
    }

    /**
     * Imposta il prezzo del cerchio.
     *
     * @param priceRim Il nuovo prezzo del cerchio da impostare.
     */
    public void setPriceRim(String priceRim) {
        this.priceRim = priceRim;
    }

    /**
     * Imposta lo sconto applicato al cerchio.
     *
     * @param discountRim Il nuovo sconto da applicare al cerchio.
     */
    public void setDiscountRim(String discountRim) {
        this.discountRim = discountRim;
    }
}
