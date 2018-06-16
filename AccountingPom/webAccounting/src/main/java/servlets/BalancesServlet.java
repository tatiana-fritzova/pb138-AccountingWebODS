package servlets;

import backend.InvoiceManager;

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
 * @author Tatiana Fritzova
 */
@WebServlet(BalancesServlet.URL_MAPPING + "/*")
public class BalancesServlet extends HttpServlet{

    private static final String BALANCES_JSP = "/balances.jsp";
    public static final String URL_MAPPING = "/balances";

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
        request.setAttribute("balances", twoDecimalPlaces());
        try {
            request.getRequestDispatcher(BALANCES_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
