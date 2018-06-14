package servlets;

//import org.slf4j.LoggerFactory;

import backend.*;

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

    private boolean validateParams(String params) {
        return params != null && params.length() != 0;
    }

    private void defineInvoice(Invoice invoice, String params) {
    }

    private Object getInvoiceManager() {
        return getServletContext().getAttribute("invoiceManager");
    }

    private List<Item> getItems(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] itemNames = parameterMap.get("itemName");
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
        return type.equals("expense") ? InvoiceType.EXPENSE :InvoiceType.INCOME;
    }

    private Person getPerson(HttpServletRequest request) {
        String fromTo = request.getParameter("fromToPerson");
        String address = request.getParameter("addressPerson");
        return new Person(fromTo, address);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("text", request.getQueryString());
        PrintWriter out = response.getWriter();
        try {
            request.getRequestDispatcher(ADD_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        String action = request.getPathInfo();
        if (!action.equals("/add")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
            return;
        }

        Invoice invoice = new Invoice();
        invoice.setItems(getItems(request));
        invoice.setType(getType(request));
        invoice.setBillTo(invoice.getType().equals(InvoiceType.EXPENSE) ? getPerson(request) : null);
        invoice.setBillFrom(invoice.getType().equals(InvoiceType.INCOME) ? getPerson(request) : null);
        invoice.setIssueDate(LocalDate.parse( request.getParameter("issueDate")));
        invoice.setDueDate(LocalDate.parse(request.getParameter("dueDate")));

        //total ... to je v invoice co, count?
        Double total = Double.parseDouble( request.getParameter("total"));

        request.setAttribute("path", invoice.toString());

        request.getRequestDispatcher(ADD_JSP).forward(request, response);

    }
}