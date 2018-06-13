package backend;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfDocument;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.DottedLineSeparator;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.model.OpenDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Katarina Matusova
 */
public class PdfExporter {

    private Document document;
    private PdfWriter writer;

    public PdfExporter() {
        this.document = new Document(PageSize.A4);
    }

    public void export(List<Invoice> invoices, int year) {
        // Open the PDF document
        File outFile = new File("Dokumenty/InvoicesFor" + String.valueOf(year)+".pdf");
        PdfDocument pdf = new PdfDocument();
        document.addDocListener(pdf);

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(outFile);
            this.writer = PdfWriter.getInstance(pdf, fileOutputStream);
            pdf.addWriter(writer);
            System.out.println("done1");
        } catch (FileNotFoundException | DocumentException ex) {
             System.out.println("done2");
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.open();

        invoices.forEach((in) -> {
            try {
                addToPdf(in);
                document.newPage();
            } catch (DocumentException ex) {
                Logger.getLogger(PdfExporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        document.close();

    }

    private void addToPdf(Invoice in) throws DocumentException {
        Font font;
        font = new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC, Color.blue);

        document.add(createParagraph(String.valueOf(in.getId()), "Invoice ID " + "\t\t" + ":" + "\t"));
        document.add(createParagraph(String.valueOf(in.getIssueDate()), "Issue Date" + "\t" + ":" + "\t"));
        document.add(createParagraph(String.valueOf(in.getDueDate()), "Due Date" + "\t" + ":" + "\t"));
        document.add(createParagraph(String.valueOf(in.getType()),"Type"+ "\t" + ":" + "\t"));
        
        document.add(Chunk.NEWLINE);
        DottedLineSeparator dottedline = new DottedLineSeparator();
        document.add(dottedline);
        document.add(Chunk.NEWLINE);

        Paragraph pFrom = new Paragraph();
        Chunk nameText = new Chunk("From :", font);
        pFrom.add(nameText);
        document.add(pFrom);
        document.add(createPersonParagraph(in.getBillFrom()));
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        Paragraph pTo = new Paragraph();
        nameText = new Chunk("To :", font);
        pTo.add(nameText);
        document.add(pTo);
        document.add(createPersonParagraph(in.getBillTo()));
        document.add(Chunk.NEWLINE);
        document.add(dottedline);
        document.add(Chunk.NEWLINE);
        document.add(createParagraph("", "Items" + "\t" + ":" + "\t"));

        double totalPrice = 0.0;
        for (Item item : in.getItems()) {
            document.add(createItemParagraph(item));
            totalPrice += item.getPrice();
        }
        document.add(Chunk.NEWLINE);
        DecimalFormat df = new DecimalFormat("####0.00");
        
        document.add(createParagraph(String.valueOf(df.format(totalPrice) + "\t\t EUR \t\t"),"Total Price "+ "\t" + ":" + "\t"));
    }

    private Paragraph createParagraph(String value, String name) {
        Paragraph p = new Paragraph();
        Font font;
        font = new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC, Color.blue);
        Chunk nameText = new Chunk(name, font);
        p.add(nameText);
        font = new Font(Font.TIMES_ROMAN, 12, Font.BOLDITALIC, Color.black);
        Chunk valueName = new Chunk(value, font);
        p.add(value);
        p.getIndentationLeft();
        return p;
    }

    private Paragraph createPersonParagraph(Person person) {
        Paragraph p = new Paragraph();
        Font font;
        font = new Font(Font.TIMES_ROMAN, 12, Font.BOLDITALIC, Color.black);
        Chunk text = new Chunk("Name : " + "\t\t", font);
        p.add(text);
        p.setAlignment(Element.ALIGN_CENTER);
        Chunk valueName = new Chunk(person.getName(), font);
        p.add(valueName);
        p.add(Chunk.NEWLINE);
        p.setIndentationLeft(20);
        Chunk addressText = new Chunk("Address : " + "\t\t", font);
        p.add(addressText);
        valueName = new Chunk(person.getAddress(), font);
        p.add(valueName);

        return p;
    }
    
    private Paragraph createItemParagraph(Item item) {
        Paragraph p = new Paragraph();
        Font font;
        font = new Font(Font.TIMES_ROMAN, 12, Font.BOLDITALIC, Color.black);
        Chunk text = new Chunk(item.getDescription() + "\t\t : \t\t ", font);
        p.add(text);
        DecimalFormat df = new DecimalFormat("####0.00");
        text = new Chunk(df.format( item.getPrice()) + "\t\t EUR \t\t", font);
        p.add(text);
        p.setAlignment(Element.ALIGN_CENTER);
        return p;
        
    }

}
