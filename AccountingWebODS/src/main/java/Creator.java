
import exceptions.IllegalEntityException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Row;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kkata
 */
public class Creator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, IllegalEntityException {
        // TODO code application logic here
        createOds();
    }

    public static void createOds() throws IOException, IllegalEntityException {

        /*  //Similar to the HSSF demo, we can read in everything into lists of
        //lists, but it'd be better to abstract everything from here while
        //the file is being read so exceptions can be handled.
        InvoiceManager manager = new InvoiceManagerImpl();
        manager.newYearSheet(2018);
        
        Invoice in = new Invoice();
        in.setBillFrom(new Person("Philip", "Slovakia"));
        Person person = new Person("Tom", "address");
        in.setType(InvoiceType.INCOME);
        in.setBillTo(person);
        in.setIssueDate(LocalDate.of(1989, Month.MARCH, 10));
        in.setDueDate(LocalDate.of(2015, Month.MARCH, 05));

        Invoice i = new Invoice();
        i.setBillFrom(new Person("Me", "A"));
        Person perso = new Person("You", "B");
        i.setBillTo(perso);
        i.setType(InvoiceType.EXPENSE);
        i.setIssueDate(LocalDate.of(2015, Month.MARCH, 10));
        i.setDueDate(LocalDate.of(2015, Month.MARCH, 30));
        
        manager.createInvoice(in);
        manager.createInvoice(i);
         */
        List<Invoice> list = new ArrayList();
        List<Item> items = new ArrayList();
        Item itemOne = new Item("Computer", 500.0);
        Item itemTwo = new Item("Headphones", 15.50);
        items.add(itemTwo);
        items.add(itemOne);
        Invoice in = new Invoice();
        in.setBillTo(new Person("Philip Smith", "Ulica mieru 23 01841 Ilava"));
        Person person = new Person("Katarina Matusova", "Pod Hajom 167 01841 Dubnica nad Vahom");
        in.setBillFrom(person);
        in.setIssueDate(LocalDate.of(2015, Month.MARCH, 21));
        in.setDueDate(LocalDate.of(2015, Month.MARCH, 10));
        in.setItems(items);
        in.setType(InvoiceType.EXPENSE);

        Invoice i = new Invoice();
        i.setBillFrom(new Person("Me", "A"));
        Person perso = new Person("You", "B");
        i.setBillTo(perso);
        i.setIssueDate(LocalDate.of(2018, Month.APRIL, 05));
        i.setDueDate(LocalDate.of(2018, Month.MAY, 30));
        i.setType(InvoiceType.INCOME);
        list.add(i);
        list.add(in);
        PdfExporter e = new PdfExporter();
        e.export(list, 2017);

    }

}
