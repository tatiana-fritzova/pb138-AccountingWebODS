package backend;


import exceptions.IllegalEntityException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.LoggerFactory;

public class InvoiceManagerImpl implements InvoiceManager {

    private final Map<Integer, List<Invoice>> invoices;
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(InvoiceManagerImpl.class);

    public InvoiceManagerImpl() {
        this.invoices = new HashMap<>();
    }

    public InvoiceManagerImpl(Map<Integer, List<Invoice>> invoices) {
        this.invoices = invoices;
    }

    @Override
    public void newYearSheet(int year) throws IOException {
        File file = new File("evidence.ods");
        SpreadSheet ss = SpreadSheet.createFromFile(file);
        if (ss.getSheet(String.valueOf(year)) != null) {
            log.error("Sheet for that year already exists.");
        }
        Sheet sheet = ss.addSheet(year + "");
        addHeading(sheet);
        saveFile(sheet);
        invoices.put(year, new ArrayList<>());
    }

    @Override
    public void endOfYear() throws IOException {
        File file = new File("evidence.ods");
        SpreadSheet ss = SpreadSheet.createFromFile(file);
        int year = getCurrentYear();
        if (ss.getSheet(String.valueOf(year)) == null) {
            log.error("Sheet does not exist.");
        }
        Sheet sheet = ss.getSheet(String.valueOf(year));

        int row = sheet.getRowCount() + 1;
        sheet.ensureRowCount(row);
        sheet.getCellAt("A" + row).setValue("end");
        saveFile(sheet);
    }
    
    @Override
    public void createInvoice(Invoice invoice) throws IllegalEntityException, IOException {
         if (invoice == null) {
            throw new IllegalEntityException("Invoice cannot be null");
        }
        File file = new File("evidence.ods");
        SpreadSheet ss = SpreadSheet.createFromFile(file);
        
        int year = invoice.getIssueDate().getYear();
        if (ss.getSheet(String.valueOf(year)) == null) {
            newYearSheet(year);
        }
        Sheet sheet = SpreadSheet.createFromFile(file).getSheet(String.valueOf(year));
        if (sheet.getCellAt("A" + Integer.toString(sheet.getRowCount())).getValue().equals("end")) {
            log.error("Year already ended.");
        }
        int row = sheet.getRowCount() + 1;
        sheet.ensureRowCount(row);
        addRow(invoice, row, sheet);
        saveFile(sheet);
        List<Invoice> yearlyInvoices = invoices.get(year);
        if (yearlyInvoices == null){
            yearlyInvoices = new ArrayList<>();
        }
        yearlyInvoices.add(invoice);
        invoices.put(year, yearlyInvoices);        
        saveFile(sheet);
    }
    
    private void addRow(Invoice invoice, int row, Sheet sheet){
        sheet.getCellAt("A" + row).setValue(invoice.getId());

        double totalPrice = 0.0;
        for (Item i : invoice.getItems()) {
            totalPrice += i.getPrice();
        }
        sheet.getCellAt("B" + row).setValue(invoice.getType());
        sheet.getCellAt("C" + row).setValue(invoice.getBillFrom().getName());
        sheet.getCellAt("D" + row).setValue(invoice.getBillTo().getName());

        sheet.getCellAt("E" + row).setValue(invoice.getIssueDate());
        sheet.getCellAt("F" + row).setValue(invoice.getDueDate());

        // also updates total value, total income and total expenses, doriesit ukladanie itemov
        if (invoice.getType() == InvoiceType.EXPENSE) {
            sheet.getCellAt("G" + row).setValue((-1) * totalPrice);

            double newTotalValue = Double.parseDouble(sheet.getCellAt(1, 0).getValue().toString()) - totalPrice;
            sheet.getCellAt(1, 0).setValue(newTotalValue);

            double newTotalExpenses = Double.parseDouble(sheet.getCellAt(3, 0).getValue().toString()) + totalPrice;
            sheet.getCellAt(3, 0).setValue(newTotalExpenses);
        } else {
            sheet.getCellAt("G" + row).setValue(totalPrice);
            double newTotalValue = Double.parseDouble(sheet.getCellAt(1, 0).getValue().toString()) + totalPrice;
            sheet.getCellAt(1, 0).setValue(newTotalValue);

            double newTotalIncome = Double.parseDouble(sheet.getCellAt(5, 0).getValue().toString()) + totalPrice;
            sheet.getCellAt(5, 0).setValue(newTotalIncome);
        }
        addItems(invoice, row, sheet);
    }

    private void addItems(Invoice invoice, int row, Sheet sheet) {
       /* int column = 8;
        for (Item item : invoice.getItems()){
            if (sheet.getColumnCount() <= column){
                sheet.ensureColumnCount(column + 2);
            }
            System.out.println(item.getDescription() + item.getPrice() + " " + column + " " + row);
            sheet.getCellAt(column - 1, row).setValue(item.getDescription());
            sheet.getCellAt(column, row).setValue(item.getPrice());
            column += 2;
        }  */  
        String items = "";
        for (Item item : invoice.getItems()){
            items += item.getDescription() + ":" + item.getPrice() + "; ";
            
        }
        sheet.ensureColumnCount(9);
        sheet.getCellAt("H" + row).setValue(items);
        
    }
    
    @Override
    public List<Invoice> findAllInvoices(Integer year) {
        return Collections.unmodifiableList(invoices.get(year));
    }

    @Override
    public List<Invoice> findAllExpenses(Integer year) {
        List<Invoice> expenses = new ArrayList<>();
        for (Invoice i : invoices.get(year)) {
            if (InvoiceType.EXPENSE.equals(i.getType())) {
                expenses.add(i);
            }
        }
        if (expenses.isEmpty()) {
            return Collections.emptyList();
        }
        return expenses;
    }

    @Override
    public List<Invoice> findAllIncomes(Integer year) {
        List<Invoice> incomes = new ArrayList<>();
        for (Invoice i : invoices.get(year)) {
            if (InvoiceType.INCOME.equals(i.getType())) {
                incomes.add(i);
            }
        }
        if (incomes.isEmpty()) {
            return Collections.emptyList();
        }
        return incomes;
    }


    @Override
    public void exportToPdf(int year) {
        PdfExporter exporter = new PdfExporter();
        exporter.export(invoices.get(year), year);
        
    }
    
    @Override
    public double getCurrentBalance() throws IOException {
        return Double.parseDouble(getCurrentSheet().getCellAt("B0").getTextValue());
    }

    public double getIncomeBalance() throws IOException {
        return Double.parseDouble(getCurrentSheet().getCellAt("F0").getTextValue());
    }

    public double GetExpenseBalance() throws IOException {
        return Double.parseDouble(getCurrentSheet().getCellAt("D0").getTextValue());
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        for (Integer year : invoices.keySet()) {
            for (Invoice i : invoices.get(year)) {
                if (Objects.equals(i.getId(), id)) {
                    return i;
                }
            }
        }
        return null;
    }
    
    private static void addHeading(Sheet sheet) throws IOException {
        sheet.ensureRowCount(2);
        sheet.ensureColumnCount(8);

        sheet.getCellAt(0, 0).setValue("Total value");
        sheet.getCellAt(2, 0).setValue("Total expenses");
        sheet.getCellAt(4, 0).setValue("Total incomes");

        sheet.getCellAt(1, 0).setValue(0.0);
        sheet.getCellAt(3, 0).setValue(0.0);
        sheet.getCellAt(5, 0).setValue(0.0);

        sheet.getCellAt(0, 1).setValue("Invoice ID");
        sheet.getCellAt(1, 1).setValue("Type");
        sheet.getCellAt(2, 1).setValue("From");
        sheet.getCellAt(3, 1).setValue("To");
        sheet.getCellAt(4, 1).setValue("Issue Date");
        sheet.getCellAt(5, 1).setValue("Due Date");
        sheet.getCellAt(6, 1).setValue("Total Amount");
        sheet.getCellAt(7, 1).setValue("Items");

    }

    private static void saveFile(Sheet sheet) throws IOException {
        File newFile = new File("evidence.ods");
        sheet.getSpreadSheet().saveAs(newFile);
    }

    private int getCurrentYear() throws IOException {
        try {
            File file = new File("evidence.ods");
            SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);
            Sheet s = spreadSheet.getSheet(spreadSheet.getSheetCount() - 1);
            return Integer.parseInt(s.getName());
        } catch (NumberFormatException e) {
            log.error("Year not started yet");
            return -1;
        }
    }

    private Sheet getCurrentSheet() throws IOException {
        File file = new File("evidence.ods");
        SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);
        return spreadSheet.getSheet(spreadSheet.getSheetCount() - 1);
    }
}