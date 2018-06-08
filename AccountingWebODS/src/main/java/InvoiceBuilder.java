import java.time.LocalDate;
import java.util.List;

public class InvoiceBuilder {
    private Long id;
    private Person billFrom;
    private Person billTo; //customer
    private LocalDate date;
    private List<Item> items;

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


    public InvoiceBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public InvoiceBuilder items(List<Item> items) {
        this.items = items;
        return this;
    }

    public Invoice build() {
        Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setBillFrom(billFrom);
        invoice.setBillTo(billTo);
        invoice.setDate(date);
        invoice.setItems(items);
        return invoice;
    }
}
