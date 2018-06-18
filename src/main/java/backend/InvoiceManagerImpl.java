package backend;

import com.lowagie.text.DocumentException;
import exceptions.IllegalEntityException;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.LoggerFactory;

public class InvoiceManagerImpl implements InvoiceManager {

    private Map<Integer, List<Invoice>> invoices = new HashMap<>();
    private Person owner;
    private String filePath ;
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(InvoiceManagerImpl.class);

    public InvoiceManagerImpl() {
     //   this.filePath = path;
        File directory = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        this.filePath = directory.getParent() + "/evidence.ods";
        try {
            File file = new File(filePath);
            this.invoices = sheetToMap();
            SpreadSheet ss = SpreadSheet.createFromFile(file);

            if (ss.getSheet("OwnerInfo") != null) {
                Sheet sheet = ss.getSheet("OwnerInfo");
                String name = sheet.getCellAt("B" + 2).getValue().toString();
                String address = sheet.getCellAt("B" + 3).getValue().toString();
                this.owner = new Person(name, address);
            }
        } catch (IOException ex) {
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public InvoiceManagerImpl(String path) {
        //   this.filePath = path;
       // File directory = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        this.filePath = path;
        try {
            File file = new File(filePath);
            this.invoices = sheetToMap();
            SpreadSheet ss = SpreadSheet.createFromFile(file);

            if (ss.getSheet("OwnerInfo") != null) {
                Sheet sheet = ss.getSheet("OwnerInfo");
                String name = sheet.getCellAt("B" + 2).getValue().toString();
                String address = sheet.getCellAt("B" + 3).getValue().toString();
                this.owner = new Person(name, address);
            }
        } catch (IOException ex) {
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setOwner(Person person) {
        this.owner = person;
        try{
            File file = new File(filePath);
            SpreadSheet ss = SpreadSheet.createFromFile(file);
            
            if (ss.getSheet("OwnerInfo") != null) {
                Sheet sheet = ss.getSheet("OwnerInfo");
                sheet.getCellAt("B" + 2).setValue(person.getName());
                sheet.getCellAt("B" + 3).setValue(person.getAddress());
                saveFile(sheet);
            } else {
                addOwnerSheet(ss);
            }
        } catch (IOException ex) {
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Person getOwner() {
        return owner;
    }

    @Override
    public void newYearSheet(int year) throws IOException {
        File file = new File(filePath);
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
    public void createInvoice(Invoice invoice) throws IllegalEntityException, IOException {
        if (invoice == null) {
            throw new IllegalEntityException("Invoice cannot be null");
        }
        if (owner == null) {
            throw new IllegalEntityException("Owner cannot be null");
        }
        isValid(invoice);
        File file = new File(filePath);
        SpreadSheet ss = SpreadSheet.createFromFile(file);

        if (ss.getSheet("OwnerInfo") == null) {
            addOwnerSheet(ss);
        }
        int year = invoice.getIssueDate().getYear();
        
        List<Invoice> yearlyInvoices = invoices.get(year);
        if (yearlyInvoices == null) {
            yearlyInvoices = new ArrayList<>();
        }
        yearlyInvoices.add(invoice);
        invoices.put(year, yearlyInvoices);
        
        if (ss.getSheet(String.valueOf(year)) == null) {
            newYearSheet(year);
        }
        Sheet sheet = SpreadSheet.createFromFile(file).getSheet(String.valueOf(year));

        int row = sheet.getRowCount() + 1;
        sheet.ensureRowCount(row);
        addRow(invoice, row, sheet);
        saveFile(sheet);
        
    }

    private void addRow(Invoice invoice, int row, Sheet sheet) {
        sheet.getCellAt("A" + row).setValue(invoice.getId());

        double totalPrice = 0.0;
        for (Item i : invoice.getItems()) {
            totalPrice += i.getPrice();
        }
        sheet.getCellAt("B" + row).setValue(invoice.getType());

        sheet.getCellAt("G" + row).setValue(invoice.getIssueDate());
        sheet.getCellAt("H" + row).setValue(invoice.getDueDate());

        // also updates total value, total income and total expenses, billTo and billFrom
        if (invoice.getType() == InvoiceType.EXPENSE) {

            sheet.getCellAt("C" + row).setValue(owner.getName());
            sheet.getCellAt("D" + row).setValue(owner.getAddress());
            invoice.setBillFrom(owner);
            sheet.getCellAt("E" + row).setValue(invoice.getBillTo().getName());
            sheet.getCellAt("F" + row).setValue(invoice.getBillTo().getAddress());

            sheet.getCellAt("I" + row).setValue((-1) * totalPrice);

            double newTotalValue = Double.parseDouble(sheet.getCellAt(1, 0).getValue().toString()) - totalPrice;
            sheet.getCellAt(1, 0).setValue(newTotalValue);

            double newTotalExpenses = Double.parseDouble(sheet.getCellAt(3, 0).getValue().toString()) + totalPrice;
            sheet.getCellAt(3, 0).setValue(newTotalExpenses);
        } else {

            sheet.getCellAt("C" + row).setValue(invoice.getBillFrom().getName());
            sheet.getCellAt("D" + row).setValue(invoice.getBillFrom().getAddress());
            invoice.setBillTo(owner);
            sheet.getCellAt("E" + row).setValue(owner.getName());
            sheet.getCellAt("F" + row).setValue(owner.getAddress());

            sheet.getCellAt("I" + row).setValue(totalPrice);
            double newTotalValue = Double.parseDouble(sheet.getCellAt(1, 0).getValue().toString()) + totalPrice;
            sheet.getCellAt(1, 0).setValue(newTotalValue);

            double newTotalIncome = Double.parseDouble(sheet.getCellAt(5, 0).getValue().toString()) + totalPrice;
            sheet.getCellAt(5, 0).setValue(newTotalIncome);
        }
        addItems(invoice, row, sheet);
    }

    private void addItems(Invoice invoice, int row, Sheet sheet) {
        String items = "";
        for (Item item : invoice.getItems()) {
            items += item.getDescription() + ":" + item.getPrice() + "; ";

        }
        sheet.ensureColumnCount(11);
        sheet.getCellAt("J" + row).setValue(items);

    }

    private void addOwnerSheet(SpreadSheet ss) throws IOException {
        ss.addSheet("OwnerInfo");
        Sheet sheet = ss.getSheet("OwnerInfo");

        sheet.ensureRowCount(3);
        sheet.ensureColumnCount(2);

        sheet.getCellAt(0, 0).setValue("Owner Information: ");
        sheet.getCellAt(0, 1).setValue("Name: ");
        sheet.getCellAt(0, 2).setValue("Address: ");

        sheet.getCellAt("B" + 2).setValue(owner.getName());
        sheet.getCellAt("B" + 3).setValue(owner.getAddress());
        saveFile(sheet);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        List<Invoice> allLists = new ArrayList<>();
        invoices.values().forEach((invoiceList) -> {
            allLists.addAll(invoiceList);
        });
        return allLists;
    }

    @Override
    public File exportToPdf(int year) {
        PdfExporter exporter = new PdfExporter();
        try {
            File file = exporter.export(invoices.get(year), year);
            return file;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public File exportAllToPfd() {
        PdfExporter exporter = new PdfExporter();
        try {
            File file = exporter.exportAll(findAllInvoices());
            return file;
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
        sheet.ensureColumnCount(10);

        sheet.getCellAt(0, 0).setValue("Total value");
        sheet.getCellAt(2, 0).setValue("Total expenses");
        sheet.getCellAt(4, 0).setValue("Total incomes");

        sheet.getCellAt(1, 0).setValue(0.0);
        sheet.getCellAt(3, 0).setValue(0.0);
        sheet.getCellAt(5, 0).setValue(0.0);

        sheet.getCellAt(0, 1).setValue("Invoice ID");
        sheet.getCellAt(1, 1).setValue("Type");
        sheet.getCellAt(2, 1).setValue("From Name");
        sheet.getCellAt(3, 1).setValue("From Address");
        sheet.getCellAt(4, 1).setValue("To Name");
        sheet.getCellAt(5, 1).setValue("To Address");
        sheet.getCellAt(6, 1).setValue("Issue Date");
        sheet.getCellAt(7, 1).setValue("Due Date");
        sheet.getCellAt(8, 1).setValue("Total Amount");
        sheet.getCellAt(9, 1).setValue("Items");

    }

    private void saveFile(Sheet sheet) throws IOException {
        File newFile = new File(filePath);
        sheet.getSpreadSheet().saveAs(newFile);
    }

    @Override
    public Map<Integer, List<Invoice>> sheetToMap() throws IOException {
        File file = new File(filePath);
        SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);

        for (int i = 0; i < spreadSheet.getSheetCount(); i++) {
            Sheet sheet = spreadSheet.getSheet(i);
            if (sheet.getName().equals("OwnerInfo")) {
                continue;
            }
            int year = 0;
            try {
                year = Integer.parseInt(sheet.getName());
            } catch (NumberFormatException e){
                continue;
            }

            for (int row = 2; row < sheet.getRowCount(); row++) {
                Invoice invoice = new Invoice();

                //set ID
                String id = sheet.getCellAt(0, row).getValue().toString();
                invoice.setId(Long.parseLong(id));

                //set type
                if (sheet.getCellAt(1, row).getValue().toString().equals("EXPENSE")) {
                    invoice.setType(InvoiceType.EXPENSE);
                } else {
                    invoice.setType(InvoiceType.INCOME);
                }

                //set From and To people
                String fromName = sheet.getCellAt(2, row).getValue().toString();
                String fromAddress = sheet.getCellAt(3, row).getValue().toString();
                Person from = new Person(fromName, fromAddress);
                invoice.setBillFrom(from);

                String toName = sheet.getCellAt(4, row).getValue().toString();
                String toAddress = sheet.getCellAt(5, row).getValue().toString();
                Person to = new Person(toName, toAddress);
                invoice.setBillTo(to);

                //set issueDate and dueDate
                String issueDate = sheet.getCellAt(6, row).getValue().toString();
                invoice.setIssueDate(LocalDate.parse(issueDate));

                String dueDate = sheet.getCellAt(7, row).getValue().toString();
                invoice.setDueDate(LocalDate.parse(dueDate));

                //set Items
                List<Item> allItems = new ArrayList<>();
                String itemString = sheet.getCellAt(9, row).getValue().toString();
                String[] items = itemString.split(";");
                for (int j = 0; j < items.length - 1; j++) {
                    String[] nameAndPrice = items[j].split(":");
                    String description = nameAndPrice[0].trim();
                    double price = Double.parseDouble(nameAndPrice[1]);
                    Item item = new Item(description, price);
                    allItems.add(item);
                }
                invoice.setItems(allItems);

                List<Invoice> yearlyInvoices = invoices.get(year);
                if (yearlyInvoices == null) {
                    yearlyInvoices = new ArrayList<>();
                }
                yearlyInvoices.add(invoice);
                invoices.put(year, yearlyInvoices);
            }
        }
        return invoices;
    }

    private void isValid(Invoice invoice) throws IllegalEntityException {
        if (invoice.getIssueDate() == null || invoice.getDueDate() == null){
            throw new IllegalEntityException("Date cannot be null");
        }
        if (invoice.getType() == null){
            throw new IllegalEntityException("Type cannot be null");
        }
        if (invoice.getIssueDate().isAfter(invoice.getDueDate())){
            throw new IllegalEntityException("Due date cannot be before issue date");
        }
        if (invoice.getType() == InvoiceType.EXPENSE && invoice.getBillTo() == null){
            throw new IllegalEntityException("Expense cannot have null as billTo");
        }
        if (invoice.getType() == InvoiceType.INCOME && invoice.getBillFrom() == null){
            throw new IllegalEntityException("Income cannot have null as billFrom");
        }
    }

    @Override
    public Set<Integer> getYears() {
        return invoices.keySet();
    }

    @Override
    public double getCurrentBalance(int year) {
        Double balance = 0d;
        for (Invoice invoice : invoices.get(year))
        {
            if (invoice.getType().equals(InvoiceType.INCOME)) {
                balance += invoice.getTotalAmount();
            } else {
                balance -= invoice.getTotalAmount();
            }
        }
        return balance;
    }

    @Override
    public Map<Integer, Double> getCurrentBalances() {
        Map<Integer, Double> currentBalances = new HashMap<>();
        for (Integer year : invoices.keySet()) {
            currentBalances.put(year, getCurrentBalance(year)) ;
        }
        return currentBalances;

    }
}
