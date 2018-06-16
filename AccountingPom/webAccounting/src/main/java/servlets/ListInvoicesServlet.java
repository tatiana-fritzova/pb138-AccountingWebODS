package servlets;

import backend.Invoice;
import backend.InvoiceManager;
import backend.InvoiceManagerImpl;
import backend.Item;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Tatiana Fritzova
 */
@WebServlet(ListInvoicesServlet.URL_MAPPING + "/*")
public class ListInvoicesServlet extends HttpServlet {

    private static final String LIST_JSP = "/viewInvoices.jsp";
    public static final String URL_MAPPING = "/viewInvoices";

    private InvoiceManager getInvoiceManager() {
        return (InvoiceManager) getServletContext().getAttribute("invoiceManager");
    }

    private String fancyFormat(Double d){
        DecimalFormat df = new DecimalFormat("####0.00");
        return String.valueOf(df.format(d));
    }

    private Map<Integer, String> twoDecimalPlaces() {
        Map<Integer, String> result = new HashMap<>();
        getInvoiceManager().getCurrentBalances().forEach((year, balance) -> result.put(year, fancyFormat(balance)));
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("invoices", getInvoiceManager().findAllInvoices());

        request.setAttribute("balances", twoDecimalPlaces());

        try {
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
