package it.bicocca.laboratoriodiprogettazione_java.common;

import it.bicocca.laboratoriodiprogettazione_java.entity.Tire;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * La classe PriceCalculatorPartial fornisce funzionalità per calcolare parti specifiche del preventivo,
 * concentrando l'attenzione su un singolo pneumatico alla volta, basandosi sulla sua posizione nella lista fornita.
 * Questo approccio parziale è utile per generare dettagli di preventivo quando si richiede di considerare
 * elementi individuali piuttosto che il totale complessivo.
 */
    public class PriceCalculatorPartial {
        ArrayList<Tire> listTire = new ArrayList<>();
        String priceAssemblyDoubleTotString;
        int posizione = 0;

    /**
     * Costruisce un nuovo calcolatore di prezzi parziale.
     *
     * @param listTire Lista di pneumatici da considerare per il calcolo.
     * @param priceAssemblyDoubleTotString Costo totale per montaggio e bilanciatura, espresso come stringa.
     * @param posizione Indice del pneumatico specifico nella lista da considerare per il calcolo.
     */
        public PriceCalculatorPartial(ArrayList<Tire> listTire, String priceAssemblyDoubleTotString, int posizione) {
            this.listTire = listTire;
            this.priceAssemblyDoubleTotString = priceAssemblyDoubleTotString;
            this.posizione = posizione;
        }

    /**
     * Calcola e restituisce il prezzo totale dei ricambi per il pneumatico specificato, inclusi i costi PFU.
     *
     * @return Prezzo totale dei ricambi in formato valuta locale come stringa.
     */
        //RITORNA IL PREZZO TOALE DEI RICAMBI IN STRING
        public String getPriceTotParts() {
            Double priceTotParts = 0.00;
            Double priceTotTire = 0.00;
            Double priceTotPfu = 0.00;

            priceTotTire = listTire.get(posizione).getPriceTotTireDouble();
            System.err.println("Prezzo totale pneumatici: " + priceTotTire);
            priceTotPfu = listTire.get(posizione).getPriceTotPfuDouble();
            System.err.println("Prezzo totale PFU: " + priceTotPfu);

            priceTotParts = priceTotTire + priceTotPfu;
            String priceTotPartsString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceTotParts);
            priceTotPartsString = priceTotPartsString.replaceAll("\\.", " ");
            return priceTotPartsString;
        }

    /**
     * Calcola l'imponibile totale combinando il costo dei ricambi e del montaggio e bilanciatura.
     *
     * @return L'imponibile totale in formato valuta locale come stringa.
     */
        public String getPriceTotTaxable() {
            String priceTotPartsString = getPriceTotParts();
            priceTotPartsString = priceTotPartsString.replaceAll("€", "");
            priceTotPartsString = priceTotPartsString.replaceAll(",", ".");
            priceTotPartsString = priceTotPartsString.substring(0, priceTotPartsString.length() - 1);
            priceTotPartsString = priceTotPartsString.replaceAll(" ", "");

            System.out.println(priceTotPartsString);

            Double priceTotPartsDouble = 0.00;

            try {
                priceTotPartsDouble = Double.parseDouble(priceTotPartsString);
            } catch (NumberFormatException e) {
                System.err.println("Problema 1 - Classe ClacolatorePrezziParziale.java");
                e.printStackTrace();
            }

            Double priceTotManoDDouble = 0.00;
            Double priceTotManoDDoubleTOT = 0.00;

            priceAssemblyDoubleTotString = priceAssemblyDoubleTotString.replaceAll("€", "");
            priceAssemblyDoubleTotString = priceAssemblyDoubleTotString.replaceAll(",", "\\.");
            priceAssemblyDoubleTotString = priceAssemblyDoubleTotString.substring(0, priceAssemblyDoubleTotString.length() - 1);

            try {
                priceTotManoDDouble = Double.parseDouble(priceAssemblyDoubleTotString);
            } catch (NumberFormatException e) {
                System.err.println("Problema 2 - Classe ClacolatorePrezziParziale.java");
                e.printStackTrace();
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
     * @return L'IVA in formato valuta locale come stringa.
     */
        public String getVatTot() {
            Double vatTot = 0.00;

            String priceTotTaxableString = getPriceTotTaxable();
            priceTotTaxableString = priceTotTaxableString.replaceAll("€", "");
            priceTotTaxableString = priceTotTaxableString.replaceAll(",", ".");
            priceTotTaxableString = priceTotTaxableString.substring(0, priceTotTaxableString.length() - 1);
            priceTotTaxableString = priceTotTaxableString.replaceAll(" ", "");

            Double priceTotTaxableDouble = 0.00;

            try {
                priceTotTaxableDouble = Double.parseDouble(priceTotTaxableString);
            } catch (NumberFormatException e) {
                System.out.println("Problema 3 - Classe ClacolatorePrezzi.java");
                e.printStackTrace();
            }
            vatTot = priceTotTaxableDouble * (22.00 / 100.00);

            String vatTotString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(vatTot);
            vatTotString = vatTotString.replaceAll("\\.", " ");
            return vatTotString;
        }


    /**
     * Calcola il totale generale del preventivo, sommando l'imponibile e l'IVA.
     *
     * @return Il totale generale in formato valuta locale come stringa.
     */
        public String getTotal() {
            Double total = 0.00;

            //trasformo in double l'iva
            String vatTotString = getVatTot();
            vatTotString = vatTotString.replaceAll("€", "");
            vatTotString = vatTotString.replaceAll(",", ".");
            vatTotString = vatTotString.substring(0, vatTotString.length() - 1);
            vatTotString = vatTotString.replaceAll(" ", "");

            Double vatTotDouble = 0.00;

            try {
                vatTotDouble = Double.parseDouble(vatTotString);
            } catch (NumberFormatException e) {
                System.out.println("Problema 4 - Classe ClacolatorePrezzi.java");
                e.printStackTrace();
            }

            //trasformo in double in prezzo imponibile
            String priceTotTaxableString = getPriceTotTaxable();
            priceTotTaxableString = priceTotTaxableString.replaceAll("€", "");
            priceTotTaxableString = priceTotTaxableString.replaceAll(",", ".");
            priceTotTaxableString = priceTotTaxableString.substring(0, priceTotTaxableString.length() - 1);
            priceTotTaxableString = priceTotTaxableString.replaceAll(" ", "");

            Double priceTotTaxableDouble = 0.00;

            try {
                priceTotTaxableDouble = Double.parseDouble(priceTotTaxableString);
            } catch (NumberFormatException e) {
                System.out.println("Problema 5 - Classe ClacolatorePrezzi.java");
                e.printStackTrace();
            }

            total = priceTotTaxableDouble + vatTotDouble;

            String totalString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(total);
            totalString = totalString.replaceAll("\\.", " ");
            return totalString;
        }
    }
