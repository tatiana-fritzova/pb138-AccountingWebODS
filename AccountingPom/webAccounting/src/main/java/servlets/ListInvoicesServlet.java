package servlets;

import backend.Invoice;
import backend.InvoiceManager;
import backend.InvoiceManagerImpl;
import backend.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("invoices", getInvoiceManager().findAllInvoices());
        DecimalFormat df = new DecimalFormat("#.##");
        df.format(getInvoiceManager().getCurrentBalance());
        Math.round(getInvoiceManager().getCurrentBalance());
        request.setAttribute("balance", getInvoiceManager().getCurrentBalance());
        request.setAttribute("string", getInvoiceManager().findAllInvoices().toString());
        try {
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
