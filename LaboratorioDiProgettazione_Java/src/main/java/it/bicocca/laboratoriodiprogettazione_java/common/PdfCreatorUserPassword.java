package it.bicocca.laboratoriodiprogettazione_java.common;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.RoundDotsBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import it.bicocca.laboratoriodiprogettazione_java.entity.Client;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * La classe PdfCreatorUserPassword è progettata per generare un documento PDF contenente i dati del login di un cliente.
 */
public class PdfCreatorUserPassword {

    Client client;
    String password;

    /**
     * Costruttore della classe PdfCreatorUserPassword.
     *
     * @param client Cliente per il quale si vuole generare la password per il login.
     */
    public PdfCreatorUserPassword(Client client, String password) {
        this.client = client;
        this.password = password;
    }

    /**
     * Genera il documento PDF contenente i dati del cliente per il login.
     *
     * @return Il percorso del file PDF creato.
     * @throws IOException Se si verifica un errore durante la creazione o la scrittura del file PDF.
     */
    public String writeOnPdf() throws IOException {
        String nameDocument = "DatiLogin/" + client.getNome() + "_" + client.getCognome() + "_DatiLogin" + ".pdf";

        File file = new File(nameDocument);
        file.getParentFile().mkdirs();

        PdfWriter pdfWriter = new PdfWriter(nameDocument);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A4);

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

        /////INSERIMENTO DATI CLIENTE/////

        float columWidthCliente[] = {80, 100, 100, 280};
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

        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

        float columWidthDati[] = {327, 24, 56, 52, 101};
        Table datiTable = new Table(columWidthDati);

        datiTable.addCell(new Cell().add(new Paragraph("DATI LOGIN ")).setBorder(Border.NO_BORDER).setBold());
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

        datiTable.addCell(new Cell().add(new Paragraph("E-mail")).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph(client.getEmail())).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

        datiTable.addCell(new Cell().add(new Paragraph("Password")).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        datiTable.addCell(new Cell().add(new Paragraph(password)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

        document.add(datiTable);

        Paragraph p = new Paragraph("Grazie per averci scelto!");
        p.setFixedPosition(100, 60, 400);
        p.setTextAlignment(TextAlignment.CENTER);
        p.setItalic();
        document.add(p);

        Date data = new Date();
        DateFormat dataFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        String dataString = dataFormat.format(data);

        document.close();

        return nameDocument;
    }

}
