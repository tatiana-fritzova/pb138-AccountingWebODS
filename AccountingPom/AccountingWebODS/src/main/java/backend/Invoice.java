package backend;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class Invoice {

    private Long id = 0l;
    public static Long count = 0l;
    private Person billFrom;
    private Person billTo; 
    private LocalDate issueDate;
    private LocalDate dueDate;
    private List<Item> items = new ArrayList<>();
    private InvoiceType type;

    public Invoice() throws IOException{
        /*File file = new File("evidence.ods");
        SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);
        int year = Integer.parseInt(spreadSheet.getSheet(spreadSheet.getSheetCount() - 1).getName());
        Sheet sheet = spreadSheet.getSheet(String.valueOf(year));
        Integer number = sheet.getRowCount() - 2;
        this.id = number.longValue();*/
        this.id = count++;
    }

    public Long getId() {
        return id;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Person getBillFrom() {
        return billFrom;
    }

    public Person getBillTo() {
        return billTo;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBillFrom(Person billFrom) {
        this.billFrom = billFrom;
    }

    public void setBillTo(Person billTo) {
        this.billTo = billTo;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "Invoice{"
                + "id=" + id
                + "type=" + type
                + ", billFrom='" + billFrom + '\''
                + ", billTo='" + billTo + '\''
                + ", issueDate=" + issueDate
                + ", dueDate=" + dueDate
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id)
                && Objects.equals(billFrom, invoice.billFrom)
                && Objects.equals(billTo, invoice.billTo)
                && Objects.equals(type, invoice.type)
                && Objects.equals(issueDate, invoice.issueDate)
                && Objects.equals(dueDate, invoice.dueDate)
                && Objects.equals(items, invoice.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, billFrom, billTo, issueDate, dueDate, items);
    }
}
