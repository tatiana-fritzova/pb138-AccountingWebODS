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
import java.util.HashMap;
import java.util.Map;


/**
 * Servlet for getting balances for accounting years.
 *
 * @author Tatiana Fritzova
 */
@WebServlet(BalancesServlet.URL_MAPPING + "/*")
public class BalancesServlet extends HttpServlet{

    private static final String BALANCES_JSP = "/balances.jsp";
    public static final String URL_MAPPING = "/balances";

    private final static Logger LOG = LoggerFactory.getLogger(BalancesServlet.class);


    private InvoiceManager getInvoiceManager() {
        return (InvoiceManager) getServletContext().getAttribute("invoiceManager");
    }

    private String fancyFormat(Double d){
        DecimalFormat df = new DecimalFormat("####0.00");
        return String.valueOf(df.format(d));
    }

    private Map<Integer, String> formatBalances() {
        Map<Integer, String> result = new HashMap<>();
        getInvoiceManager().getCurrentBalances().forEach((year, balance) -> result.put(year, fancyFormat(balance)));
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.debug("getting balances");
        request.setAttribute("balances", formatBalances());
        try {
            request.getRequestDispatcher(BALANCES_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error("could not get balances");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
