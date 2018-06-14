package servlets;

import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(ListInvoicesServlet.URL_MAPPING + "/*")
public class ListInvoicesServlet extends HttpServlet {

    private static final String LIST_JSP = "/viewInvoices.jsp";
    public static final String URL_MAPPING = "/viewInvoices";

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(ListInvoicesServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.info("GET ...");
        LOG.info("showing table of invoices");
        request.setAttribute("invoices", new String[]{"item1", "item2"});
        request.setAttribute("seznam", Arrays.asList("mléko", "rohlíky", "salám"));
        try {
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error("Cannot show invoices", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("error", request.getAttribute("firstname"));
        response.sendRedirect(request.getContextPath()+URL_MAPPING);

    }
}
