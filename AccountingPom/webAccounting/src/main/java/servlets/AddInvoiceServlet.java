package servlets;

//import org.slf4j.LoggerFactory;

import backend.*;
import exceptions.IllegalEntityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    private void defineInvoice(Invoice invoice, String params) {
    }

    private InvoiceManager getInvoiceManager() {
        return (InvoiceManager) getServletContext().getAttribute("invoiceManager");
    }

    private List<Item> getItems(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] itemNames = parameterMap.get("itemName");
        if (itemNames == null) return null;
        List<Item> items = new ArrayList<>();
        for (String itemName : itemNames) {
            Double price = Double.parseDouble((String) request.getParameter(itemName+"Price"));
            price *= Integer.parseInt((String) request.getParameter(itemName+"Pieces"));
            items.add(new Item(itemName, price));
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
        request.setAttribute("invoices", getInvoiceManager().findAllInvoices().toString());
        try {
            request.getRequestDispatcher(ADD_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (getInvoiceManager().getOwner() == null) {
            request.setAttribute("noowner", "No owner is set, please fill in required information.");
            request.getRequestDispatcher("/myProfile").forward(request, response);
        }

        request.setCharacterEncoding("utf-8");
        String action = request.getPathInfo();

        if (!action.equals("/add")) {
            request.setAttribute("failure", "Unknown action.");
            request.getRequestDispatcher(ADD_JSP).forward(request, response);
            return;
        }

        Invoice invoice = new Invoice();
        List<Item> items = getItems(request);
        InvoiceType type = getType(request);
        Person person = getPerson(request);
        Person owner = (Person) getServletContext().getAttribute("owner");
        String total = request.getParameter("total");
        String issueDate = request.getParameter("issueDate");
        String dueDate = request.getParameter("dueDate");
        if (items == null || type == null || person == null || total == null ||
                issueDate == null || dueDate == null) {
            request.setAttribute("failure", "No items were submitted.");
            request.getRequestDispatcher(ADD_JSP).forward(request, response);
            return;
        }
        invoice.setItems(items);
        invoice.setType(type);
        invoice.setBillTo(invoice.getType().equals(InvoiceType.EXPENSE) ? person : owner);
        invoice.setBillFrom(invoice.getType().equals(InvoiceType.INCOME) ? person : owner);
        invoice.setIssueDate(LocalDate.parse(issueDate));
        invoice.setDueDate(LocalDate.parse(dueDate));
        request.setAttribute("invoiceToString", getInvoiceManager().findAllInvoices().toString());
        try {
            getInvoiceManager().createInvoice(invoice);
            request.setAttribute("success", "Invoice was successfully added.");
        } catch (IllegalEntityException e) {
            request.setAttribute("failure", "Failed to add new invoice." +e.getMessage());
        }
        request.getRequestDispatcher(ADD_JSP).forward(request, response);

    }
}
