import java.time.LocalDate;
import java.util.List;

public class InvoiceBuilder {
    private int id;
    private Person billFrom;
    private Person billTo; //customer
    private LocalDate issueDate;
    private LocalDate dueDate;    
    private List<Item> items;

    public InvoiceBuilder id(){
        this.id = id;
        return this;
    }

    public InvoiceBuilder items(List<Item> items) {
         this.items = items;
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

    public Invoice build() {
        Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setBillFrom(billFrom);
        invoice.setBillTo(billTo);        
        invoice.setIssueDate(issueDate);
        invoice.setDueDate(dueDate);
        invoice.setItems(items);
        return invoice;
    }
}
