import java.time.LocalDate;
import java.util.List;

public class InvoiceBuilder {
    private Long id;
    private Person billFrom;
    private Person billTo; //customer
    private LocalDate issueDate;
    private LocalDate dueDate;
    private double price;
    private String description;

    public InvoiceBuilder id(){
        this.id = id;
        return this;
    }

    public InvoiceBuilder billFrom(Person billFrom) {
        this.billFrom = billFrom;
        return this;
    }

    public InvoiceBuilder billTo(Person billTo) {
        this.billTo = billTo;
        return this;
    }


    public InvoiceBuilder dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }
    
    public InvoiceBuilder issueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }    

    public InvoiceBuilder price(double price) {
        this.price = price;
        return this;
    }
    public InvoiceBuilder description(String description) {
        this.description = description;
        return this;
    }

    public Invoice build() {
        Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setBillFrom(billFrom);
        invoice.setBillTo(billTo);        
        invoice.setIssueDate(issueDate);
        invoice.setDueDate(dueDate);
        invoice.setPrice(price);
        invoice.setDescription(description);
        return invoice;
    }
}
