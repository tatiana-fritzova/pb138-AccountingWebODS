import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Invoice {
    public static int id = 0;
    private Person billFrom;
    private Person billTo; //customer
    private LocalDate issueDate;
    private LocalDate dueDate;
    private List<Item> items = new ArrayList<>();

    public Invoice() {
        id++;
    }

    public Invoice(Person billFrom, Person billTo,
                   LocalDate issueDate, LocalDate dueDate, List<Item> items) {
        id++;
        this.billFrom = billFrom;
        this.billTo = billTo;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.items = items;
    }    

    public int getId() {
        return id;
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
    

    public void setId(int id) {
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
        return "Invoice{" +
                "id=" + id +
                ", billFrom='" + billFrom + '\'' +
                ", billTo='" + billTo + '\'' +                
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) &&
                Objects.equals(billFrom, invoice.billFrom) &&
                Objects.equals(billTo, invoice.billTo) &&                
                Objects.equals(issueDate, invoice.issueDate) &&
                Objects.equals(dueDate, invoice.dueDate) &&
                Objects.equals(items, invoice.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  billFrom, billTo, issueDate, dueDate, items);
    }
}
