
import exceptions.IllegalEntityException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jopendocument.dom.OOUtils;
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
        Person person = new Person("Tom","addres");
        in.setBillTo(person);
        in.setDueDate(LocalDate.MAX);
        in.setId(Long.MIN_VALUE);
        in.setPrice(200.0);
        in.setDescription("phone");
        InvoiceManager manager = new InvoiceManagerImpl();
        manager.createInvoice(in);
                
                
        }
    
    public static void addHeading(Sheet sheet) throws IOException {
        sheet.ensureRowCount(4);
        sheet.ensureColumnCount(10);
        sheet.getCellAt("A1").setValue("From");
        sheet.getCellAt("A2").setValue("To");
        sheet.getCellAt("A3").setValue("price");
        sheet.getCellAt("A4").setValue("description");
        

        sheet.getCellAt("B3").setValue(0.0);
        
        sheet.getCellAt("D1").setValue("id");
        sheet.getCellAt("D2").setValue("issue Date");
        sheet.getCellAt("D3").setValue("due Date");
        
    }
    
    
    public static void saveFile(Sheet sheet) throws IOException {
        File newFile = new File("evidence.ods");
        sheet.getSpreadSheet().saveAs(newFile);
    }

}
