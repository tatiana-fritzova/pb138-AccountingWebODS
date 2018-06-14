package backend;

import exceptions.IllegalEntityException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

        Person owner = new Person("Larry", "Hronsek");
        InvoiceManager manager = new InvoiceManagerImpl();

        manager.setOwner(owner);

      //  List<Invoice> list = new ArrayList();
        List<Item> items = new ArrayList();
        Item itemOne = new Item("Computer", 500.0);
        Item itemTwo = new Item("Headphones", 15.50);
        items.add(itemTwo);
        items.add(itemOne);
        Invoice in = new Invoice();
        in.setBillTo(new Person("Philip Smith", "Ulica mieru 23 01841 Ilava"));
        in.setIssueDate(LocalDate.of(2015, Month.MARCH, 01));
        in.setDueDate(LocalDate.of(2015, Month.MARCH, 10));
        in.setItems(items);
        in.setType(InvoiceType.EXPENSE);

        Invoice i = new Invoice();
        i.setBillFrom(new Person("Me", "A"));
        i.setIssueDate(LocalDate.of(2018, Month.APRIL, 05));
        i.setDueDate(LocalDate.of(2018, Month.MAY, 30));
        i.setType(InvoiceType.INCOME);
       // list.add(i);
     //   list.add(in);

        manager.createInvoice(in);
        // manager.exportAllToPfd();
        // manager.exportToPdf(2015);

        Item i1 = new Item("tv", 500.0);
        Item i2 = new Item("book", 10.5);
        List<Item> l = new ArrayList<>();
        l.add(i1);
        l.add(i2);

        Invoice n = new Invoice();
        n.setBillFrom(new Person("Philipppp", "Slovakia"));
        n.setType(InvoiceType.INCOME);
        n.setItems(l);
        n.setIssueDate(LocalDate.of(1980, Month.MARCH, 10));
        n.setDueDate(LocalDate.of(2015, Month.MARCH, 15));

        Item i3 = new Item("pen", 3.0);
        l.add(i3);
        Invoice inn = new Invoice();
        Person personn = new Person("Tom", "address");
        inn.setType(InvoiceType.EXPENSE);
        inn.setBillTo(personn);
        inn.setItems(l);
        inn.setIssueDate(LocalDate.of(1980, Month.MARCH, 01));
        inn.setDueDate(LocalDate.of(2015, Month.MARCH, 05));

        Invoice ii = new Invoice();
        Person pperso = new Person("You", "B");
        ii.setBillTo(pperso);
        ii.setType(InvoiceType.EXPENSE);
        ii.setIssueDate(LocalDate.of(2014, Month.MARCH, 10));
        ii.setDueDate(LocalDate.of(2015, Month.MARCH, 30));

        // manager.createInvoice(in);
        
        /*   manager.createInvoice(i);
        manager.createInvoice(n);
        manager.createInvoice(ii);
        manager.createInvoice(inn);
        //System.out.println(manager.findAllIncomes(1980));
        //manager.exportToPdf(1980);
         */
        // InvoiceManager manager = new InvoiceManagerImpl();
       /* for (int year : map.keySet()) {
            System.out.println(year);
            for (Invoice invcc : map.get(year)) {
                System.out.println(invcc.toString());
            }
        }*//*
       for (int year : map.keySet()) {
            System.out.println(year);
            for (Invoice invcc : map.get(year)) {
                System.out.println(invcc.toString());
            }*/

        List<Invoice> all = manager.findAllInvoices();
        for (Invoice invc : all) {
            System.out.println(invc.getType());
        }
        
        System.out.println(manager.getCurrentBalance());
    }
}
