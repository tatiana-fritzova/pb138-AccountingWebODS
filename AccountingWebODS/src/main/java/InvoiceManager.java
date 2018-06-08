import java.util.List;

public interface InvoiceManager {

    /**
     * Adds new invoice (expense or income) to the ods file.
     * @param invoice invoice to be created
     */
    void createInvoice(Invoice invoice);

    /**
     * Updates an invoice already saved in the ods file.
     * @param invoice invoice to be updated
     */
    void updateInvoice(Invoice invoice);

    /**
     * Deletes an invoice from the ods file.
     * @param invoice invoice to be deleted
     */
    void deleteInvoice(Invoice invoice);

    /**
     * Gets balance for current accounting year.
     * @return Double balance
     */
    Double getCurrentBalance();

    /**
     * Returns an invoice with given id.
     * @param id id of demanded invoice
     * @return invoice with given id
     */
    Invoice getInvoiceById(Long id);

    /**
     * Returns a list of all invoices.
     * @return list of all invoices
     */
    List<Invoice> findAllInvoices();

    /** Returns a list of all expenses.
     * @return list of all expenses
     */
    List<Invoice> findAllExpenses();

    /**
     * Returns a list of all incomes.
     * @return list of all incomes
     */
    List<Invoice> findAllIncomes();


    /**
     * Exports history of all invoices to a pdf file.
     */
    void exportToPdf();
}
