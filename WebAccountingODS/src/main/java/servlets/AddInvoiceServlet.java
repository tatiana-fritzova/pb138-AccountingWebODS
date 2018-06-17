package servlets;


import backend.*;
import exceptions.IllegalEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servlet for adding new Invoices.
 *
 * @author Tatiana Fritzova
 */
@WebServlet(AddInvoiceServlet.URL_MAPPING + "/*")
public class AddInvoiceServlet extends HttpServlet {

    private static final String ADD_JSP = "/newInvoice.jsp";
    public static final String URL_MAPPING = "/newInvoice";
    private final static Logger LOG = LoggerFactory.getLogger(AddInvoiceServlet.class);

    private InvoiceManager getInvoiceManager() {
        return (InvoiceManager) getServletContext().getAttribute("invoiceManager");
    }

    private Double fancyFormat(Double d){
        DecimalFormat df = new DecimalFormat("####0.00");
        return Double.parseDouble(String.valueOf(df.format(d)));
    }

    private List<Item> getItems(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] itemNames = parameterMap.get("itemName");
        if (itemNames == null) return null;
        List<Item> items = new ArrayList<>();
        for (String itemName : itemNames) {
            Double price = Double.parseDouble(request.getParameter(itemName+"Price"));
            price *= Integer.parseInt(request.getParameter(itemName+"Pieces"));
            items.add(new Item(itemName, fancyFormat(price)));
        }
        return items;
    }

    private InvoiceType getType(HttpServletRequest request) {
        String type = request.getParameter("type");
        if (type.equals("expense")) return InvoiceType.EXPENSE;
        if (type.equals("income")) return InvoiceType.INCOME;
        return null;
    }

    private Person getPerson(HttpServletRequest request) {
        String fromTo = request.getParameter("fromToPerson");
        String address = request.getParameter("addressPerson");
        return new Person(fromTo, address);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.debug("getting list of all invoices");
        request.setCharacterEncoding("utf-8");
        if (getInvoiceManager().getOwner() == null) {
            LOG.error("owner is not set");
            request.setAttribute("noowner", "No owner is set, please fill in required information.");
        }
        request.setAttribute("invoices", getInvoiceManager().findAllInvoices().toString());
        try {
            request.getRequestDispatcher(ADD_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error("cannot get list of invoices");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        LOG.debug("POST ... add new invoice");

        if (getInvoiceManager().getOwner() == null) {
            LOG.error("owner is not set");
            request.setAttribute("noowner", "No owner is set, please fill in required information.");
            request.getRequestDispatcher("/myProfile").forward(request, response);
            return;
        }

        // retrieve information from submitted form
        List<Item> items = getItems(request);
        InvoiceType type = getType(request);
        Person person = getPerson(request);
        Person owner = (Person) getServletContext().getAttribute("owner");
        String issueDate = request.getParameter("issueDate");
        String dueDate = request.getParameter("dueDate");

        // validate retrieved input and set fail message if needed
        if (items == null || type == null || person == null ||
                issueDate == null || dueDate == null) {
            LOG.error("invalid form input");
            if (items == null) {
                request.setAttribute("failure", "No items were submitted.");
            } else {
                request.setAttribute("failure", "Fill in all fields please.");
            }
            request.getRequestDispatcher(ADD_JSP).forward(request, response);
            return;
        }

        // create invoice
        Invoice invoice = new InvoiceBuilder().items(items)
                .type(type)
                .billFrom(type.equals(InvoiceType.INCOME) ? person : owner)
                .billTo(type.equals(InvoiceType.EXPENSE) ? person : owner)
                .dueDate(LocalDate.parse(dueDate))
                .issueDate(LocalDate.parse(issueDate))
                .build();

        // add invoice and inform the user
        try {
            getInvoiceManager().createInvoice(invoice);
            request.setAttribute("success", "Invoice was successfully added.");
        } catch (IllegalEntityException e) {
            LOG.error("failed to add invoice");
            request.setAttribute("failure", "Failed to add new invoice." +e.getMessage());
        } finally {
            request.getRequestDispatcher(ADD_JSP).forward(request, response);
        }

    }
}
