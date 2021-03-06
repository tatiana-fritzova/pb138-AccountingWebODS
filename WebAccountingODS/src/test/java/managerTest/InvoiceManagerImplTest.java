/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerTest;

import backend.Invoice;
import backend.InvoiceBuilder;
import backend.InvoiceManagerImpl;
import backend.InvoiceType;
import backend.Item;
import backend.Person;
import exceptions.IllegalEntityException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Katarina Matusova
 */
public class InvoiceManagerImplTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private final Person personOne = new Person("Katarina", "Botanicka 45, Brno");
    private final Person personTwo = new Person("Phillip Smith", "Ulica mieru 45, Ilava");
    private final Person owner = new Person("Katarina Matusova", "Pod hajom 1366, Dubnica");
    private final Person personEmpty = new Person("", "");
    private final InvoiceManagerImpl manager = new InvoiceManagerImpl(path());

    private final Item itemOne = new Item("tv", 200.0, true);
    private final Item itemTwo = new Item("pc", 300.0, true);

    private List<Item> itemList() {
        List<Item> list = new ArrayList();
        list.add(itemOne);
        list.add(itemTwo);
        return list;
    }

    private InvoiceBuilder sampleInvoiceWrongDates() {
        return new InvoiceBuilder()
                .billFrom(personOne)
                .billTo(personTwo)
                .dueDate(LocalDate.of(1800, Month.MARCH, 10))
                .issueDate(LocalDate.of(1902, Month.MARCH, 10))
                .type(InvoiceType.INCOME);

    }

    private InvoiceBuilder sampleInvoiceIncome() {
        return new InvoiceBuilder()
                .billFrom(personOne)
                .billTo(personTwo)
                .dueDate(LocalDate.of(1802, Month.MARCH, 10))
                .issueDate(LocalDate.of(1800, Month.MARCH, 10))
                .type(InvoiceType.INCOME);

    }

    private InvoiceBuilder sampleInvoiceExpense() {
        return new InvoiceBuilder()
                .billFrom(personOne)
                .billTo(personTwo)
                .dueDate(LocalDate.of(1802, Month.MARCH, 10))
                .issueDate(LocalDate.of(1800, Month.MARCH, 10))
                .type(InvoiceType.EXPENSE);

    }

    private String path() {
            String dir = System.getProperty("user.dir");
            String str =  dir + "/src/main/resources/evidenceTest.ods";
            return str;
    }

    @Test(expected = IllegalEntityException.class)
    public void createNullInvoice() throws Exception {
        manager.createInvoice(null);
    }

    @Test
    public void createIncome() throws Exception {
        Invoice invoice = sampleInvoiceIncome().billTo(null).build();
        invoice.setItems(itemList());
        manager.setOwner(owner);
        manager.createInvoice(invoice);

        Long invoiceId = invoice.getId();
        assertNotNull(invoiceId);

        assertThat(manager.getInvoiceById(invoiceId))
                .isEqualToComparingFieldByField(invoice);
    }

    @Test
    public void createExpenseWithNullBillFrom() throws Exception {
        Invoice invoice = sampleInvoiceExpense().billFrom(null).build();
        invoice.setItems(itemList());
        manager.setOwner(owner);
        manager.createInvoice(invoice);

        Long invoiceId = invoice.getId();
        assertNotNull(invoiceId);

        assertThat(manager.getInvoiceById(invoiceId))
                .isEqualToComparingFieldByField(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createExpenseWithNullBillTo() throws Exception {
        Invoice invoice = sampleInvoiceExpense().billTo(null).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createIncomeWithNullBillFrom() throws Exception {
        Invoice invoice = sampleInvoiceIncome().billFrom(null).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithNullIssueDate() throws Exception {
        Invoice invoice = sampleInvoiceIncome().issueDate(null).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithNullDueDate() throws Exception {
        Invoice invoice = sampleInvoiceIncome().dueDate(null).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithWrongDates() throws Exception {
        Invoice invoice = sampleInvoiceWrongDates().build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithNullPeople() throws Exception {
        Invoice invoice = sampleInvoiceIncome().billTo(null).billFrom(null).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithNulType() throws Exception {
        Invoice invoice = sampleInvoiceIncome().type(null).build();
        manager.createInvoice(invoice);
    }

}
