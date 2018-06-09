
import exceptions.IllegalEntityException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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

      /**  String[] columns = new String[]{"ID", "FROM", "TO", "PRICE","DESCRIPTION","ISSUE DATE" , "DUE DATE"};

        TableModel model = new DefaultTableModel(null, columns);

        // Save the data to an ODS file and open it.
        final File file = new File("evidence.ods");
        SpreadSheet.createEmpty(model).saveAs(file);
        SpreadSheet sheet = SpreadSheet.createFromFile(file);
        sheet.addSheet("one");
        final Sheet sheett = SpreadSheet.createFromFile(file).getSheet("one");
        System.out.println(sheett.getName());
        OOUtils.open(sheet.saveAs(file));*/
	
	//Similar to the HSSF demo, we can read in everything into lists of
	//lists, but it'd be better to abstract everything from here while
	//the file is being read so exceptions can be handled.
	Invoice in = new Invoice();
        in.setBillFrom(new Person("Philip" ,"Slovakia"));
        Person person = new Person("Tom","address");
        in.setBillTo(person);        
        in.setIssueDate(LocalDate.now());
        in.setDueDate(LocalDate.MAX);
        
        Invoice i = new Invoice();
        i.setBillFrom(new Person("Me" ,"A"));
        Person perso = new Person("You","B");
        i.setBillTo(perso);
        in.setIssueDate(LocalDate.now());
        i.setDueDate(LocalDate.MAX);
        
        try {
            File file = new File("evidence.ods");
            SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);
            Sheet sheet = spreadSheet.addSheet("Sheet");
            addHeading(sheet);
            InvoiceManager manager = new InvoiceManagerImpl(sheet);
            manager.createInvoice(in);
            manager.createInvoice(i);
            saveFile(sheet);
            
        } catch (IOException ex) {
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addHeading(Sheet sheet) throws IOException {
        sheet.ensureRowCount(1);
        sheet.ensureColumnCount(10);
        sheet.getCellAt(0,0).setValue("Invoice ID");
        sheet.getCellAt(1,0).setValue("From");
        sheet.getCellAt(2,0).setValue("To");    
        sheet.getCellAt(3,0).setValue("Issue Date");
        sheet.getCellAt(4,0).setValue("Due Date");        
        sheet.getCellAt(5,0).setValue("Items");             
        sheet.getCellAt(6,0).setValue("Total Amount");
        
    }
    
    
    public static void saveFile(Sheet sheet) throws IOException {
        File newFile = new File("evidence.ods");
        sheet.getSpreadSheet().saveAs(newFile);
    }

}
