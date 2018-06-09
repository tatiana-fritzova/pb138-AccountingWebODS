
import exceptions.IllegalEntityException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class InvoiceManagerImpl implements InvoiceManager {

    private List<Invoice> invoices;

    public InvoiceManagerImpl() {
        this.invoices = new ArrayList<>();
    }

    public InvoiceManagerImpl(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public void createInvoice(Invoice invoice) throws IllegalEntityException {
        if (invoice == null) {
            throw new IllegalEntityException("Invoice cannot be null");
        }
        File file = new File("evidence.ods");
        try {
            SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);
            createIt(spreadSheet, invoice);
        } catch (IOException ex) {
            Logger.getLogger(InvoiceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createIt(SpreadSheet spreadSheet, Invoice invoice) throws IOException {
        Sheet newSheet = spreadSheet.addSheet("oasdfg");
        addHeading(newSheet, invoice);
        newSheet.getCellAt("B3").setValue(invoice.getPrice());
        newSheet.getCellAt("B2").setValue(invoice.getBillTo().getName());
        newSheet.getCellAt("B1").setValue(invoice.getBillFrom().getName());
        newSheet.getCellAt("B4").setValue(invoice.getDescription());
        newSheet.getCellAt("E1").setValue(invoice.getId());
        saveFile(newSheet);

    }

    @Override
    public void updateInvoice(Invoice invoice) {

    }

    @Override
    public void deleteInvoice(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }
        invoices.remove(invoice);
    }

    @Override
    public Double getCurrentBalance() {
        double balance = 0.0;
        /**
         * for (Invoice i : invoices) { if (i.getBillFrom() == null) { for (Item
         * item : i.getItems()) { balance -= item.getPrice(); } } else if
         * (i.getBillTo() == null) { for (Item item : i.getItems()) { balance +=
         * item.getPrice(); } } }
         */
        return balance;
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        for (Invoice i : invoices) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Invoice> findAllInvoices() {
        return Collections.unmodifiableList(invoices);
    }

    @Override
    public List<Invoice> findAllExpenses() {
        List<Invoice> expenses = new ArrayList<>();
        for (Invoice i : invoices) {
            if (i.getBillFrom() == null) {
                expenses.add(i);
            }
        }
        if (expenses.isEmpty()) {
            return Collections.emptyList();
        }
        return expenses;
    }

    @Override
    public List<Invoice> findAllIncomes() {
        List<Invoice> incomes = new ArrayList<>();
        for (Invoice i : invoices) {
            if (i.getBillTo() == null) {
                incomes.add(i);
            }
        }
        if (incomes.isEmpty()) {
            return Collections.emptyList();
        }
        return incomes;
    }

    @Override
    public void exportToPdf() {

    }

    public static void addHeading(Sheet sheet, Invoice invoice) throws IOException {
        sheet.ensureRowCount(4);
        sheet.ensureColumnCount(10);
        sheet.getCellAt("A1").setValue("From");
        sheet.getCellAt("A2").setValue("To");
        sheet.getCellAt("A3").setValue("price");
        sheet.getCellAt("A4").setValue("description");

        sheet.getCellAt("D1").setValue("id");
        sheet.getCellAt("D2").setValue("issue Date");
        sheet.getCellAt("D3").setValue("due Date");

    }

    public static void saveFile(Sheet sheet) throws IOException {
        File newFile = new File("evidence.ods");
        sheet.getSpreadSheet().saveAs(newFile);
    }
}
