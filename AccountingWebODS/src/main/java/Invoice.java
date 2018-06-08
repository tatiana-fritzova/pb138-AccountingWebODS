import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private Long id;
    private Person billFrom;
    private Person billTo; //customer
    private LocalDate date;
    private List<Item> items;

    public Invoice() {}

    public Invoice(Long id, Person billFrom, Person billTo,
                   LocalDate date, List<Item> items) {
        this.id = id;
        this.billFrom = billFrom;
        this.billTo = billTo;
        this.date = date;
        this.items = items;
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

    public LocalDate getDate() {
        return date;
    }

    public List<Item> getItems() {
        return items;
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


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", billFrom='" + billFrom + '\'' +
                ", billTo='" + billTo + '\'' +
                ", date=" + date +
                ", items=" + items +
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
                Objects.equals(date, invoice.date) &&
                Objects.equals(items, invoice.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  billFrom, billTo, date, items);
    }
}
