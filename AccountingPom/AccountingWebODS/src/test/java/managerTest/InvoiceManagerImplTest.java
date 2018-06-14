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
    private final Person owner = new Person("Katarina Matusova" , "Pod hajom 1366, Dubnica");
    private final Person personEmpty = new Person("", "");
    private final InvoiceManagerImpl manager = new InvoiceManagerImpl();

    private final Item itemOne = new Item("tv", 200.0);
    private final Item itemTwo = new Item("pc", 300.0);

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
                .type(InvoiceType.INCOME)
                .items(itemList());

    }

    private InvoiceBuilder sampleInvoiceOne() {
        return new InvoiceBuilder()
                .billFrom(personOne)
                .billTo(personTwo)
                .dueDate(LocalDate.of(1802, Month.MARCH, 10))
                .issueDate(LocalDate.of(1800, Month.MARCH, 10))
                .type(InvoiceType.INCOME)
                .items(itemList());

    }

    @Test(expected = IllegalEntityException.class)
    public void createNullInvoice() throws Exception {
        manager.createInvoice(null);
    }

    @Test
    public void createInvoice() throws Exception {
        Invoice invoice = sampleInvoiceOne().build();
        manager.setOwner(owner);
        manager.createInvoice(invoice);
        

        Long invoiceId = invoice.getId();
        assertNotNull(invoiceId);

        assertThat(manager.getInvoiceById(invoiceId))
                .isEqualToComparingFieldByField(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithNullBillTo() throws Exception {
        Invoice invoice = sampleInvoiceOne().billTo(null).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithNullBillFrom() throws Exception {
        Invoice invoice = sampleInvoiceOne().billFrom(null).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithNullIssueDate() throws Exception {
        Invoice invoice = sampleInvoiceOne().issueDate(null).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithNullDueDate() throws Exception {
        Invoice invoice = sampleInvoiceOne().dueDate(null).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithWrongDates() throws Exception {
        Invoice invoice = sampleInvoiceWrongDates().build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithEmptyNameFrom() throws Exception {
        Invoice invoice = sampleInvoiceOne().billFrom(personEmpty).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithEmptyNameTo() throws Exception {
        Invoice invoice = sampleInvoiceOne().billTo(personEmpty).build();
        manager.createInvoice(invoice);
    }

    @Test(expected = IllegalEntityException.class)
    public void createInvoiceWithNulType() throws Exception {
        Invoice invoice = sampleInvoiceOne().type(null).build();
        manager.createInvoice(invoice);
    }

}
