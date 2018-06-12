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

      /*  List<Invoice> list = new ArrayList();
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

        Item i1 = new Item("tv", 500.0);
        Item i2 = new Item("book", 10.5);
        List<Item> l = new ArrayList<>();
        l.add(i1);
        l.add(i2);
        
        InvoiceManager manager = new InvoiceManagerImpl();
        
        Invoice n = new Invoice();
        n.setBillFrom(new Person("Philipppp", "Slovakia"));
        Person per = new Person("Tommmmm", "address");
        n.setType(InvoiceType.INCOME);
        n.setBillTo(per);
        n.setItems(l);
        n.setIssueDate(LocalDate.of(1980, Month.MARCH, 10));
        n.setDueDate(LocalDate.of(2015, Month.MARCH, 05));
        
        Item i3 = new Item("pen", 3.0);
        l.add(i3);
        Invoice inn = new Invoice();
        inn.setBillFrom(new Person("Philip", "Slovakia"));
        Person personn = new Person("Tom", "address");
        inn.setType(InvoiceType.EXPENSE);
        inn.setBillTo(personn);
        inn.setItems(l);
        inn.setIssueDate(LocalDate.of(1980, Month.MARCH, 11));
        inn.setDueDate(LocalDate.of(2015, Month.MARCH, 05));

        Invoice ii = new Invoice();
        ii.setBillFrom(new Person("Me", "A"));
        Person pperso = new Person("You", "B");
        ii.setBillTo(pperso);
        ii.setType(InvoiceType.EXPENSE);
        ii.setIssueDate(LocalDate.of(2016, Month.MARCH, 10));
        ii.setDueDate(LocalDate.of(2015, Month.MARCH, 30));
        
        manager.createInvoice(in);
        manager.createInvoice(i);
        manager.createInvoice(n);
        manager.createInvoice(ii);
        manager.createInvoice(inn);
    */
      InvoiceManager manager = new InvoiceManagerImpl();
      Map<Integer, List<Invoice>> map = manager.sheetToMap();
      for (int year : map.keySet()){
          System.out.println(year);
          for (Invoice i : map.get(year)){
              System.out.println(i.toString());
          }
      }
    }
}
