package servlets;

import backend.Invoice;
import backend.InvoiceManagerImpl;
import backend.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    // LOGOVANIE NEFUGNUJE :( !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // DO GET NEZBEHNE HNED

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("invoiceManager", new InvoiceManagerImpl());
        request.setAttribute("seznam", Arrays.asList("mléko", "rohlíky", "salám"));
        try {
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath()+URL_MAPPING);
    }
}
