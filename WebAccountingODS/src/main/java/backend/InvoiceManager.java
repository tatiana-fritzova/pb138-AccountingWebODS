package backend;

import exceptions.IllegalEntityException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InvoiceManager {
    
    /**
     * Method sets a new owner.
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
     * @throws IOException throws IOException
     */
    void newYearSheet(int year) throws IOException;
    
    /**
     * Adds new invoice (expense or income) to the ods file.
     * @param invoice invoice to be created
     * @throws exceptions.IllegalEntityException throws IllegalEntityException
     */
    void createInvoice(Invoice invoice) throws IllegalEntityException, IOException ;

    /**
     * Gets balance for certain accounting year.
     * @param year year
     * @return Double balance
     */
    double getCurrentBalance(int year);

    /**
     * Method returns a map containing balance for each accounting year.
     * @return map of balances
     */
    Map<Integer, Double> getCurrentBalances();

    /**
     * Returns an invoice with given id.
     * @param id id of demanded invoice
     * @return invoice with given id
     */
    Invoice getInvoiceById(Long id);

    /**
     * Method returns all invoices.
     * @return list of all invoices
     */
    List<Invoice> findAllInvoices();

    /**
     * Exports history of invoices from a certain year to a pdf file.
     * @param year year of invoices to export to pdf
     */
    File exportToPdf(int year);
    
    /**
     * Exports every invoice existing to pdf.
     */
    File exportAllToPfd();
    
    /**
     * Reads all sheets and adds invoices to map.
     * @return map of all invoices
     * @throws IOException throws IOException
     */
    Map<Integer, List<Invoice>>  sheetToMap() throws IOException;

    /**
     * Gets accounting years.
     * @return set of accounting years
     */
    Set<Integer> getYears();


}
