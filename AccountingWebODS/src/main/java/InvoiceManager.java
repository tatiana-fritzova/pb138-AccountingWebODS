import exceptions.IllegalEntityException;
import java.io.IOException;
import java.util.List;

public interface InvoiceManager {

    /**
     * Creates new sheet for an upcoming year.
     * @param year new year 
     * @throws IOException
     */
    void newYearSheet(int year) throws IOException;
    
    /**
     * Ends current year.
     * @throws IOException
     */
    void endOfYear() throws IOException;
    
    /**
     * Adds new invoice (expense or income) to the ods file.
     * @param invoice invoice to be created
     * @throws exceptions.IllegalEntityException
     */
    void createInvoice(Invoice invoice) throws IllegalEntityException, IOException ;

    /**
     * Gets balance for current accounting year.
     * @return Double balance
     */
    double getCurrentBalance() throws IOException;

    /**
     * Returns an invoice with given id.
     * @param id id of demanded invoice
     * @return invoice with given id
     */
    Invoice getInvoiceById(Long id);

    /**
     * Returns a list of all invoices.
     * @param year year
     * @return list of all invoices
     */
    List<Invoice> findAllInvoices(Integer year);

    /** Returns a list of all expenses.
     * @param year year
     * @return list of all expenses
     */
    List<Invoice> findAllExpenses(Integer year);

    /**
     * Returns a list of all incomes.
     * @param year year
     * @return list of all incomes
     */
    List<Invoice> findAllIncomes(Integer year);


    /**
     * Exports history of all invoices to a pdf file.
     */
    void exportToPdf();
}
