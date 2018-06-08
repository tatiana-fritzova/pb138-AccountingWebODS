import exceptions.IllegalEntityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceManagerImpl implements InvoiceManager {
    
    private List<Invoice> invoices;
    
    public InvoiceManagerImpl(){
        this.invoices = new ArrayList<>();
    }
    
    public InvoiceManagerImpl(List<Invoice> invoices){
        this.invoices = invoices;
    }

    @Override
    public void createInvoice(Invoice invoice) throws IllegalEntityException{
        if (invoice == null){
            throw new IllegalEntityException("Invoice cannot be null");
        }
        invoices.add(invoice);
    }

    @Override
    public void updateInvoice(Invoice invoice) {

    }

    @Override
    public void deleteInvoice(Invoice invoice) {
        if (invoice == null){
            throw new IllegalArgumentException("Invoice cannot be null");
        }
        invoices.remove(invoice);
    }

    @Override
    public Double getCurrentBalance() {   
        double balance = 0.0;           
        for (Invoice i : invoices){       
            if (i.getBillFrom() == null){
                for (Item item : i.getItems()){
                    balance -= item.getPrice();
                }
            } else if (i.getBillTo() == null) {
                for (Item item : i.getItems()){
                    balance += item.getPrice();
                }
            }
        }
        return balance;
    }

    @Override
    public Invoice getInvoiceById(Long id) { 
        for (Invoice i : invoices){
            if (i.getId() == id){
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
        for (Invoice i : invoices){
            if (i.getBillFrom() == null){
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
        for (Invoice i : invoices){
            if (i.getBillTo() == null){
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
}
