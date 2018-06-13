package backend;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class InvoiceBuilder {
    private Long id;
    private Person billFrom;
    private Person billTo; 
    private LocalDate issueDate;
    private LocalDate dueDate;    
    private List<Item> items;
    private InvoiceType type;

    
    public InvoiceBuilder type(InvoiceType type){
        this.type = type;
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

    public Invoice build() throws IOException {
        Invoice invoice = new Invoice();

        invoice.setBillFrom(billFrom);
        invoice.setBillTo(billTo);        
        invoice.setIssueDate(issueDate);
        invoice.setDueDate(dueDate);
        invoice.setItems(items);
        invoice.setType(type);
        return invoice;
    }
}
