import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private Long id;
    private Person billFrom;
    private Person billTo; //customer
    private LocalDate issueDate;
    private LocalDate dueDate;
    private double price;
    private String description;

    public Invoice() {}

    public Invoice(Long id, Person billFrom, Person billTo,
                   LocalDate issueDate, LocalDate dueDate, double price, String info) {
        this.id = id;
        this.billFrom = billFrom;
        this.billTo = billTo;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.price = price;
        this.description = info;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
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
        return "Invoice{" +
                "id=" + id +
                ", billFrom='" + billFrom + '\'' +
                ", billTo='" + billTo + '\'' +                
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", price=" + price +
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
                Objects.equals(price, invoice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  billFrom, billTo, issueDate, dueDate, price);
    }
}
