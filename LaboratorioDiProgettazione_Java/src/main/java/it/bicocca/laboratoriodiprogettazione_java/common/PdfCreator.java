package it.bicocca.laboratoriodiprogettazione_java.common;
import it.bicocca.laboratoriodiprogettazione_java.entity.*;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.RoundDotsBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * La classe PdfCreator è progettata per generare un documento PDF contenente un preventivo personalizzato
 * per un cliente, includendo dettagli sui pneumatici e sui cerchi selezionati, nonché le opzioni di montaggio
 * e bilanciatura. Offre inoltre la possibilità di gestire i conti separatamente per cerchi e pneumatici.
 */
    public class PdfCreator {

        Client client;
        ArrayList<Tire> listTire = new ArrayList<>();
        ArrayList<Rim> listRim = new ArrayList<>();
        String assemblyAndBalancing;
        Boolean separateCount;

        public String prezzoTotale;

    /**
     * Costruttore della classe PdfCreator.
     *
     * @param client Cliente per il quale il preventivo è destinato.
     * @param listTire Elenco dei pneumatici selezionati per il preventivo.
     * @param listRim Elenco dei cerchi selezionati per il preventivo.
     * @param assemblyAndBalancing Costo del montaggio e bilanciatura.
     * @param separateCount Flag per determinare se gestire i conti dei cerchi separatamente dai pneumatici.
     */
        public PdfCreator(Client client, ArrayList<Tire> listTire, ArrayList<Rim> listRim, String assemblyAndBalancing, Boolean separateCount) {
            this.client = client;
            this.listTire = listTire;
            this.listRim = listRim;
            this.assemblyAndBalancing = assemblyAndBalancing;
            this.separateCount = separateCount;
        }

    /**
     * Genera il documento PDF del preventivo.
     *
     * @return Il percorso del file PDF creato.
     * @throws IOException Se si verifica un errore durante la creazione o la scrittura del file PDF.
     */
        public String writeOnPdf() throws IOException {

            /////CREAZIONE DOCUMENTO PDF/////
            String nameDocument = "Preventivi/" + client.getNome() + "_" + client.getCognome() + ".pdf";

            File file = new File(nameDocument);
            file.getParentFile().mkdirs();

            PdfWriter pdfWriter = new PdfWriter(nameDocument);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            pdfDocument.setDefaultPageSize(PageSize.A4);


            /////INSERIMENTO LOGHI/////

            ImageData imageData = ImageDataFactory.create(this.getClass().getResource("/it/bicocca/laboratoriodiprogettazione_java/icons/renaultgroupLogo.png"));
            Image renault = new Image(imageData);
            renault.setFixedPosition(425f, 770f);
            renault.scaleToFit(135, 40);

            document.add(renault);

            ///INSERIMENTO TITOLO/////
            float col = 280f;
            float columWidthTitolo[] = {col, col};

            Table tableTitolo = new Table(columWidthTitolo);

            tableTitolo.addCell(new Cell().add(new Paragraph("Preventivo.it"))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setMarginTop(30f)
                    .setMarginBottom(30f)
                    .setFontSize(25f)
                    .setBorder(Border.NO_BORDER)
            );

            //document.add(tableTitolo);

            /////INSERIMENTO DATI CLIENTE/////

            float columWidthCliente[] = {80, 300, 100, 80};
            Table infoClustomerTable = new Table(columWidthCliente);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            infoClustomerTable.addCell(new Cell(0,4).add(new Paragraph("DATI CLIENTE"))
                    .setBold()
                    .setBorder(Border.NO_BORDER)
                    .setFontSize(14f));

            infoClustomerTable.addCell(new Cell().add(new Paragraph("Nome: ")).setBorder(Border.NO_BORDER));
            infoClustomerTable.addCell(new Cell().add(new Paragraph(client.getNome())).setBorder(Border.NO_BORDER));
            infoClustomerTable.addCell(new Cell().add(new Paragraph("Tel.: ")).setBorder(Border.NO_BORDER));
            infoClustomerTable.addCell(new Cell().add(new Paragraph(client.getTelefono())).setBorder(Border.NO_BORDER));
            infoClustomerTable.addCell(new Cell().add(new Paragraph("Cognome: ")).setBorder(Border.NO_BORDER));
            infoClustomerTable.addCell(new Cell().add(new Paragraph(client.getCognome())).setBorder(Border.NO_BORDER));
            infoClustomerTable.addCell(new Cell().add(new Paragraph("Email.: ")).setBorder(Border.NO_BORDER));
            infoClustomerTable.addCell(new Cell().add(new Paragraph(client.getEmail())).setBorder(Border.NO_BORDER));


            document.add(infoClustomerTable);

            /////COLONNA DATI//////

            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            float columWidthDati[] = {327, 24, 56, 52, 101};
            Table datiTable = new Table(columWidthDati);

            int counter = 1;

            if(separateCount){

                listRim.clear();

                for (int i = 0; i < listTire.size(); i++){
                    //inizializziamo le variabili

                    //DEBUG: System.err.println("Dimensione lista: " + listPneum.size());
                    //DEBUG: System.err.println("Elementi nella lista: " + listPneum);
                    //DEBUG: System.out.println(listPneum.get(i));

                    int nSolution = i + 1;

                    if(i != 0){
                        datiTable.addCell(new Cell().add(new Paragraph("")).setBold().setBorderTop(new RoundDotsBorder(1f)).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new RoundDotsBorder(1f)).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new RoundDotsBorder(1f)).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new RoundDotsBorder(1f)).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new RoundDotsBorder(1f)).setBorder(Border.NO_BORDER));

                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                        //datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        //datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        //datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        //datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                        //datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    }

                    /////RIGA 6/////

                    datiTable.addCell(new Cell().add(new Paragraph("PROPOSTA " + nSolution)).setBorder(Border.NO_BORDER).setBold());
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));


                    /////RIGA 1/////
                    datiTable.addCell(new Cell().add(new Paragraph("DESCRIZIONE")).setBold());
                    datiTable.addCell(new Cell().add(new Paragraph("Q.")).setTextAlignment(TextAlignment.CENTER).setBold());
                    datiTable.addCell(new Cell().add(new Paragraph("LISTINO")).setTextAlignment(TextAlignment.CENTER).setBold());
                    datiTable.addCell(new Cell().add(new Paragraph("SCONTO")).setTextAlignment(TextAlignment.CENTER).setBold());
                    datiTable.addCell(new Cell().add(new Paragraph("TOTALE")).setTextAlignment(TextAlignment.RIGHT).setBold());


                    datiTable.addCell(new Cell().add(new Paragraph("PNEUM.: " + listTire.get(i).getBrandTire() + " " + listTire.get(i).getDimensionTire())).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph(String.valueOf(listTire.get(i).getNumberTire()))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    //Prezzo singolo pneumatico formattatto in euro
                    datiTable.addCell(new Cell().add(new Paragraph((listTire.get(i).getPriceTire()))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                    datiTable.addCell(new Cell().add(new Paragraph(listTire.get(i).getDiscountTire())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                    //Prezzo totale pneumatico formattatto in euro
                    datiTable.addCell(new Cell().add(new Paragraph((listTire.get(i).getPriceTotTire()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

                    datiTable.addCell(new Cell().add(new Paragraph("PFU (Tassa di smaltimento)")).setBorder(Border.NO_BORDER).setBold());
                    int nTiresTot = 0;


                    nTiresTot = nTiresTot + Integer.parseInt(listTire.get(i).getNumberTire());
                    Double priceTotPfu = 0.00;
                    priceTotPfu = priceTotPfu + Double.parseDouble(listTire.get(i).getPriceTotPfu());

                    datiTable.addCell(new Cell().add(new Paragraph(String.valueOf(nTiresTot))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    //Prezzo pfu formattatto
                    if (listTire.size() == 0){
                        datiTable.addCell(new Cell().add(new Paragraph("€ 0,00").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
                    }else{
                        datiTable.addCell(new Cell().add(new Paragraph(listTire.get(0).getPricePfu()).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
                    }
                    datiTable.addCell(new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                    String priceTotPfuString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceTotPfu);
                    priceTotPfuString = priceTotPfuString.replaceAll("\\.",",");
                    datiTable.addCell(new Cell().add(new Paragraph(priceTotPfuString).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

                    /////RIGA 5/////
                    datiTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("------------------").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

                    /////RIGA 6/////
                    datiTable.addCell(new Cell().add(new Paragraph("TOTALE RICAMBI: ")).setBorder(Border.NO_BORDER).setBold());
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                    //Andiamo a calcolare i valori per il montaggio e bilanciatura che dobbiamo passare al calculator
                    //Numero totali di pneumatici:
                    int numberTiresTot = Integer.parseInt(listTire.get(i).getNumberTire());
                    //Montaggio e bilanciatura in double:
                    Double priceAssemblyDouble = 0.00;
                    try {
                        assemblyAndBalancing = assemblyAndBalancing.replaceAll(",", "\\.");
                        priceAssemblyDouble = Double.parseDouble(assemblyAndBalancing);
                    }catch(NumberFormatException e){
                        //DEBUG: System.err.println("Non è stato inserito il prezzo di montaggio e bilanciatura");
                    }

                    String priceAssemblyDoubleString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceAssemblyDouble);
                    //Prezzo totale del montaggio e bilanciatura:
                    Double priceAssemblyDoubleTot = 0.00;
                    priceAssemblyDoubleTot = priceAssemblyDouble * numberTiresTot;
                    String priceAssemblyDoubleTotString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceAssemblyDoubleTot);
                    priceAssemblyDoubleTotString = priceAssemblyDoubleTotString.replaceAll("\\.", " ");

                    PriceCalculatorPartial calculator = new PriceCalculatorPartial(listTire, priceAssemblyDoubleTotString, i);
                    //Prezzo tot ricambi formattatto in euro
                    //todo: da modificare: serve solo il prezzo del tipo di pneumatico corrente
                    datiTable.addCell(new Cell().add(new Paragraph(calculator.getPriceTotParts()).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

                    /////RIGA 7/////
                    datiTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("------------------").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

                    /////RIGA 8/////
                    datiTable.addCell(new Cell().add(new Paragraph("MONTAGGIO E BILACIATURA: ")).setBorder(Border.NO_BORDER).setBold());

                    datiTable.addCell(new Cell().add(new Paragraph(String.valueOf(numberTiresTot))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph(priceAssemblyDoubleString).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                    //Prezzo tot mano d'opera formattatto in euro
                    datiTable.addCell(new Cell().add(new Paragraph(priceAssemblyDoubleTotString).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

                    /////RIGA 9/////
                    datiTable.addCell(new Cell().add(new Paragraph("IMPONIBILE: ")).setBold().setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));

                    //todo: da modificare: bisogna avere solo l'imponibile dello pneumatico corrente
                    datiTable.addCell(new Cell().add(new Paragraph(calculator.getPriceTotTaxable()).setTextAlignment(TextAlignment.RIGHT)).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));

                    /////RIGA 10/////
                    datiTable.addCell(new Cell().add(new Paragraph("IVA 22%: ")).setBold().setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));

                    datiTable.addCell(new Cell().add(new Paragraph(calculator.getVatTot()).setTextAlignment(TextAlignment.RIGHT)).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));

                    /////RIGA 11/////
                    datiTable.addCell(new Cell().add(new Paragraph("TOTALE: ")).setBold().setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER).setFontSize(12f));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER));

                    prezzoTotale = calculator.getTotal();

                    datiTable.addCell(new Cell().add(new Paragraph(prezzoTotale).setTextAlignment(TextAlignment.RIGHT)).setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER));



                    document.add(datiTable);

                    datiTable = new Table(columWidthDati);

                    if(counter != listTire.size()){
                        if (counter % 2 == 0){
                            document.add(new AreaBreak());
                        }
                    }

                    System.err.println(counter);
                    counter ++;
                }

            }else{

                /////RIGA 1/////
                datiTable.addCell(new Cell().add(new Paragraph("DESCRIZIONE")).setBold());
                datiTable.addCell(new Cell().add(new Paragraph("Q.")).setTextAlignment(TextAlignment.CENTER).setBold());
                datiTable.addCell(new Cell().add(new Paragraph("LISTINO")).setTextAlignment(TextAlignment.CENTER).setBold());
                datiTable.addCell(new Cell().add(new Paragraph("SCONTO")).setTextAlignment(TextAlignment.CENTER).setBold());
                datiTable.addCell(new Cell().add(new Paragraph("TOTALE")).setTextAlignment(TextAlignment.RIGHT).setBold());

                /////RIGA 2/////
                for (int i = 0; i < listTire.size(); i++){
                    datiTable.addCell(new Cell().add(new Paragraph("PNEUM.: " + listTire.get(i).getBrandTire() + " " + listTire.get(i).getDimensionTire())).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph(String.valueOf(listTire.get(i).getNumberTire()))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                    //Prezzo singolo pneumatico formattatto in euro
                    datiTable.addCell(new Cell().add(new Paragraph((listTire.get(i).getPriceTire()))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                    datiTable.addCell(new Cell().add(new Paragraph(listTire.get(i).getDiscountTire())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                    //Prezzo totale pneumatico formattatto in euro
                    datiTable.addCell(new Cell().add(new Paragraph((listTire.get(i).getPriceTotTire()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
                }

                /////RIGA 3/////
                datiTable.addCell(new Cell().add(new Paragraph("PFU (Tassa di smaltimento)")).setBorder(Border.NO_BORDER).setBold());
                int nTiresTot = 0;
                Double priceTotPfu = 0.00;
                for (int i = 0; i < listTire.size(); i++){
                    //Calcoliamo il numero totale degli pneumatici presenti nella lista
                    nTiresTot = nTiresTot + Integer.parseInt(listTire.get(i).getNumberTire());
                    priceTotPfu = priceTotPfu + Double.parseDouble(listTire.get(i).getPriceTotPfu());
                }

                datiTable.addCell(new Cell().add(new Paragraph(String.valueOf(nTiresTot))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                //Prezzo pfu formattatto
                if (listTire.size() == 0){
                    datiTable.addCell(new Cell().add(new Paragraph("€ 0,00").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
                }else{
                    datiTable.addCell(new Cell().add(new Paragraph(listTire.get(0).getPricePfu()).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
                }
                datiTable.addCell(new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
                //Prezzo tot pfu formattatto in euro
                String priceTotPfuString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceTotPfu);
                priceTotPfuString = priceTotPfuString.replaceAll("\\.",",");
                datiTable.addCell(new Cell().add(new Paragraph(priceTotPfuString).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

                /////RIGA 4/////
                for (int i = 0; i < listRim.size(); i++){
                    datiTable.addCell(new Cell().add(new Paragraph("CERCHI: " + listRim.get(i).getTypeRim())).setBorder(Border.NO_BORDER));
                    datiTable.addCell(new Cell().add(new Paragraph(String.valueOf(listRim.get(i).getNumberRim()))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                    //Prezzo cerchio formattatto in euro
                    datiTable.addCell(new Cell().add(new Paragraph(listRim.get(i).getPriceRim()).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                    datiTable.addCell(new Cell().add(new Paragraph(listRim.get(i).getDiscountRim())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                    //Prezzo tot Cerchi formattatto in euro
                    datiTable.addCell(new Cell().add(new Paragraph(listRim.get(i).getPriceTotRim()).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
                }

                /////RIGA 5/////
                datiTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("------------------").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

                /////RIGA 6/////
                datiTable.addCell(new Cell().add(new Paragraph("TOTALE RICAMBI: ")).setBorder(Border.NO_BORDER).setBold());
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                //Andiamo a calcolare i valori per il montaggio e bilanciatura che dobbiamo passare al calcolatore
                //Numero totali di pneumatici:
                int nuberTiresTot = 0;
                for (int i = 0; i < listTire.size(); i++){
                    nuberTiresTot = nuberTiresTot + Integer.parseInt(listTire.get(i).getNumberTire());
                }
                //Montaggio e bilanciatura in double:
                Double priceAssemblyDouble = 0.00;
                try {
                    assemblyAndBalancing = assemblyAndBalancing.replaceAll(",", "\\.");
                    priceAssemblyDouble = Double.parseDouble(assemblyAndBalancing);
                }catch(NumberFormatException e){
                    System.err.println("Non è stato inserito il prezzo di montaggio e bilanciatura");
                }
                String priceAssemblyDoubleString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceAssemblyDouble);
                //Prezzo totale del montaggio e bilanciatura:
                Double priceAssemblyDoubleTot = 0.00;
                priceAssemblyDoubleTot = priceAssemblyDouble * nuberTiresTot;
                String priceAssemblyDoubleTotString = NumberFormat.getCurrencyInstance(new Locale("it", "IT")).format(priceAssemblyDoubleTot);
                priceAssemblyDoubleTotString = priceAssemblyDoubleTotString.replaceAll("\\.", " ");

                PriceCalculator calculator = new PriceCalculator(listTire, listRim, priceAssemblyDoubleTotString);
                //Prezzo tot ricambi formattatto in euro
                datiTable.addCell(new Cell().add(new Paragraph(calculator.getPriceTotParts()).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));


                /////RIGA 7/////
                datiTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("------------------").setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

                /////RIGA 8/////
                datiTable.addCell(new Cell().add(new Paragraph("MONTAGGIO E BILACIATURA: ")).setBorder(Border.NO_BORDER).setBold());

                datiTable.addCell(new Cell().add(new Paragraph(String.valueOf(nuberTiresTot))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph(priceAssemblyDoubleString).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                //Prezzo tot mano d'opera formattatto in euro

                datiTable.addCell(new Cell().add(new Paragraph(priceAssemblyDoubleTotString).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));

                /////RIGA 9/////
                datiTable.addCell(new Cell().add(new Paragraph("IMPONIBILE: ")).setBold().setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));

                datiTable.addCell(new Cell().add(new Paragraph(calculator.getPriceTotTaxable()).setTextAlignment(TextAlignment.RIGHT)).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));

                /////RIGA 10/////
                datiTable.addCell(new Cell().add(new Paragraph("IVA 22%: ")).setBold().setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));

                datiTable.addCell(new Cell().add(new Paragraph(calculator.getVatTot()).setTextAlignment(TextAlignment.RIGHT)).setBorderTop(new SolidBorder(1f)).setBorder(Border.NO_BORDER));

                /////RIGA 11/////
                datiTable.addCell(new Cell().add(new Paragraph("TOTALE: ")).setBold().setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER).setFontSize(12f));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER));
                datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER));

                prezzoTotale = calculator.getTotal();

                datiTable.addCell(new Cell().add(new Paragraph(prezzoTotale).setTextAlignment(TextAlignment.RIGHT)).setBorderTop(new SolidBorder(3f)).setBorder(Border.NO_BORDER));

                document.add(datiTable);
            }

            //document.add(datiTable);

            Paragraph p = new Paragraph("Preventivo valido 30 giorni dalla data di emissione");
            p.setFixedPosition(100, 60, 400);
            p.setTextAlignment(TextAlignment.CENTER);
            p.setItalic();
            document.add(p);

            Date data = new Date();
            DateFormat dataFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
            String dataString = dataFormat.format(data);

            //DEBUG: System.out.println(nPrevInt);

            document.close();

            listTire.clear();
            listRim.clear();

            return nameDocument;
        }
    }
