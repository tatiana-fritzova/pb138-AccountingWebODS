package backend;

import exceptions.IllegalEntityException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface InvoiceManager {
    
    /**
     * Method sets an owner.
     * @param person owner
     */
    void setOwner(Person person);

    /**
     * Method returns the owner.
     * @return owner
     */
    Person getOwner();

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
     * @param year year of invoices to export to pdf
     */
    void exportToPdf(int year);
    
    /**
     * Exports every invoice existing to pdf.
     */
    void exportAllToPfd();
    
    /**
     * Reads sheets and adds invoices to map.
     * @throws IOException
     */
    Map<Integer, List<Invoice>>  sheetToMap() throws IOException;
}
