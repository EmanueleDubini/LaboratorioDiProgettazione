package it.bicocca.laboratoriodiprogettazione_java.common;

import it.bicocca.laboratoriodiprogettazione_java.entity.Rim;
import it.bicocca.laboratoriodiprogettazione_java.entity.Tire;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * La classe PriceCalculator è responsabile del calcolo del preventivo complessivo
 * basato sui pneumatici, i cerchi selezionati e il costo di montaggio e bilanciatura.
 * Utilizza le liste fornite di pneumatici e cerchi per determinare il costo totale dei ricambi,
 * l'imponibile, l'IVA applicabile e il totale complessivo del preventivo.
 */
public class PriceCalculator {
    ArrayList<Tire> listTire = new ArrayList<>();
    ArrayList<Rim> listRim = new ArrayList<>();
    String priceAssemblyDoubleTotString;


    /**
     * Costruttore della classe PriceCalculator.
     *
     * @param listTire Lista di pneumatici selezionati per il preventivo.
     * @param listRim Lista di cerchi selezionati per il preventivo.
     * @param priceAssemblyDoubleTotString Costo totale di montaggio e bilanciatura come stringa.
     */
    public PriceCalculator(ArrayList<Tire> listTire, ArrayList<Rim> listRim, String priceAssemblyDoubleTotString) {
        this.listTire = listTire;
        this.listRim = listRim;
        this.priceAssemblyDoubleTotString = priceAssemblyDoubleTotString;
    }

    /**
     * Calcola il prezzo totale dei ricambi, sommando i costi dei pneumatici, cerchi e PFU.
     *
     * @return Il prezzo totale dei ricambi in formato stringa, formattato in euro.
     */
    //RITORNA IL PREZZO TOALE DEI RICAMBI IN STRING
    public String getPriceTotParts() {
        Double priceTotParts = 0.00;
        Double priceTotTire = 0.00;
        Double priceTotRim = 0.00;
        Double priceTotPfu = 0.00;

        for (int i = 0; i < listTire.size(); i++){
            priceTotTire = priceTotTire + listTire.get(i).getPriceTotTireDouble();
            priceTotPfu = priceTotPfu + listTire.get(i).getPriceTotPfuDouble();
        }

        for (int i = 0; i < listRim.size(); i++){
            priceTotRim = priceTotRim + listRim.get(i).getPriceTotRimDouble();
        }

        priceTotParts = priceTotTire + priceTotRim + priceTotPfu;
        String priceTotPartsString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceTotParts);
        priceTotPartsString = priceTotPartsString.replaceAll("\\.", " ");
        return priceTotPartsString;
    }

    /**
     * Calcola l'imponibile del preventivo, sommando il costo dei ricambi e del montaggio e bilanciatura.
     *
     * @return L'imponibile in formato stringa, formattato in euro.
     */
    public String getPriceTotTaxable() {
        String priceTotPartsString = getPriceTotParts();
        priceTotPartsString = priceTotPartsString.replaceAll("€", "");
        priceTotPartsString = priceTotPartsString.replaceAll("," ,".");
        priceTotPartsString = priceTotPartsString.substring(0, priceTotPartsString.length() -1);
        priceTotPartsString = priceTotPartsString.replaceAll(" ", "");

        System.out.println(priceTotPartsString);

        Double priceTotPartsDouble = 0.00;

        try{
            priceTotPartsDouble = Double.parseDouble(priceTotPartsString);
        }catch (NumberFormatException e){
            System.out.println("Problema 1 - Classe ClacolatorePrezzi.java");
            e.printStackTrace();
        }

        Double priceTotManoDDouble = 0.00;
        Double priceTotManoDDoubleTOT = 0.00;

        priceAssemblyDoubleTotString = priceAssemblyDoubleTotString.replaceAll("€", "");
        priceAssemblyDoubleTotString = priceAssemblyDoubleTotString.replaceAll("," ,"\\.");
        priceAssemblyDoubleTotString = priceAssemblyDoubleTotString.substring(0, priceAssemblyDoubleTotString.length() -1);

        try{
            priceTotManoDDouble = Double.parseDouble(priceAssemblyDoubleTotString);
        }catch (NumberFormatException e){
            System.out.println("Problema 2 - Classe ClacolatorePrezzi.java");;
        }

        priceTotManoDDoubleTOT = priceTotManoDDoubleTOT + priceTotManoDDouble;

        Double priceTotTaxable = priceTotManoDDoubleTOT + priceTotPartsDouble;

        String priceTotTaxableString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceTotTaxable);
        priceTotTaxableString = priceTotTaxableString.replaceAll("\\.", " ");
        return priceTotTaxableString;
    }

    /**
     * Calcola l'IVA totale applicabile al preventivo.
     *
     * @return L'IVA in formato stringa, formattato in euro.
     */
    public String getVatTot() {
        Double vatTot = 0.00;

        String priceTotTaxableString = getPriceTotTaxable();
        priceTotTaxableString = priceTotTaxableString.replaceAll("€", "");
        priceTotTaxableString = priceTotTaxableString.replaceAll("," ,".");
        priceTotTaxableString = priceTotTaxableString.substring(0, priceTotTaxableString.length() -1);
        priceTotTaxableString = priceTotTaxableString.replaceAll(" ", "");

        Double priceTotTaxableDouble = 0.00;

        try{
            priceTotTaxableDouble = Double.parseDouble(priceTotTaxableString);
        }catch (NumberFormatException e){
            System.out.println("Problema 3 - Classe CalcolatorePrezzi.java");
        }
        vatTot = priceTotTaxableDouble * (22.00/100.00);

        String vatTotString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(vatTot);
        vatTotString = vatTotString.replaceAll("\\." , " ");
        return vatTotString;
    }

    /**
     * Calcola il totale complessivo del preventivo, includendo l'imponibile e l'IVA.
     *
     * @return Il totale complessivo in formato stringa, formattato in euro.
     */
    public String getTotal() {
        Double total = 0.00;

        //trasformo in double l'iva
        String vatTotString = getVatTot();
        vatTotString = vatTotString.replaceAll("€", "");
        vatTotString = vatTotString.replaceAll("," ,".");
        vatTotString = vatTotString.substring(0, vatTotString.length() -1);
        vatTotString = vatTotString.replaceAll(" ", "");

        Double vatTotDouble = 0.00;

        try{
            vatTotDouble = Double.parseDouble(vatTotString);
        }catch (NumberFormatException e){
            System.out.println("Problema 4 - Classe CalcolatorePrezzi.java");
        }

        //trasformo in double in prezzo imponibile
        String priceTotTaxableString = getPriceTotTaxable();
        priceTotTaxableString = priceTotTaxableString.replaceAll("€", "");
        priceTotTaxableString = priceTotTaxableString.replaceAll("," ,".");
        priceTotTaxableString = priceTotTaxableString.substring(0, priceTotTaxableString.length() -1);
        priceTotTaxableString = priceTotTaxableString.replaceAll(" ", "");

        Double priceTotTaxableDouble = 0.00;

        try{
            priceTotTaxableDouble = Double.parseDouble(priceTotTaxableString);
        }catch (NumberFormatException e){
            System.out.println("Problema 5 - Classe CalcolatorePrezzi.java");
        }

        total = priceTotTaxableDouble + vatTotDouble;

        String totalString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(total);
        totalString = totalString.replaceAll("\\.", " ");
        return totalString;
    }
}
