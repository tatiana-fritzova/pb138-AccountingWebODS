package backend;

import exceptions.IllegalEntityException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
    private Person owner;
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(InvoiceManagerImpl.class);

    public InvoiceManagerImpl() {
        this.invoices = new HashMap<>();
    }

    @Override
    public void setOwner(Person person) {
        this.owner = person;
    }

    @Override
    public Person getOwner() {
        return owner;
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
        if (owner == null) {
            throw new IllegalEntityException("Owner cannot be null");
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
        if (yearlyInvoices == null) {
            yearlyInvoices = new ArrayList<>();
        }
        yearlyInvoices.add(invoice);
        invoices.put(year, yearlyInvoices);
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
    
    @Override
    public List<Invoice> findAllInvoices(){
        List<Invoice> allLists = new ArrayList<>();
        invoices.values().forEach((invoiceList) -> {
            allLists.addAll(invoiceList);
        });
        return allLists;
    }

    @Override
    public double getTotalAmount(Invoice invoice){
        double totalPrice = 0.0;
        for (Item i : invoice.getItems()) {
            totalPrice += i.getPrice();
        }
        return totalPrice;
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
    public void exportAllToPfd() {
        invoices.entrySet().forEach((entry) -> {
            exportToPdf(entry.getKey());
        });
    }

    @Override
    public double getCurrentBalance() throws IOException {
        return Double.parseDouble(getCurrentSheet().getCellAt("B0").getTextValue());
    }

    public double getIncomeBalance() throws IOException {
        return Double.parseDouble(getCurrentSheet().getCellAt("F0").getTextValue());
    }

    public double getExpenseBalance() throws IOException {
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

    @Override
    public Map<Integer, List<Invoice>> sheetToMap() throws IOException {
        File file = new File("evidence.ods");
        SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);

        for (int i = 0; i < spreadSheet.getSheetCount(); i++) {
            Sheet sheet = spreadSheet.getSheet(i);
            int year = Integer.parseInt(sheet.getName());

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
}
