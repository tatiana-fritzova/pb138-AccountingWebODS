package servlets;

import backend.InvoiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Servlet for listing all invoices.
 *
 * @author Tatiana Fritzova
 */
@WebServlet(ListInvoicesServlet.URL_MAPPING + "/*")
public class ListInvoicesServlet extends HttpServlet {

    private static final String LIST_JSP = "/viewInvoices.jsp";
    public static final String URL_MAPPING = "/viewInvoices";

    private final static Logger LOG = LoggerFactory.getLogger(ListInvoicesServlet.class);


    private InvoiceManager getInvoiceManager() {
        return (InvoiceManager) getServletContext().getAttribute("invoiceManager");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.debug("Getting all invoices and");
        request.setAttribute("invoices", getInvoiceManager().findAllInvoices());

        try {
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
