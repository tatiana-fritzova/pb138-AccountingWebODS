package servlets;

//import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for adding new Invoices.
 *
 * @author Tatiana Fritzova
 */
@WebServlet(AddInvoiceServlet.URL_MAPPING + "/*")
public class AddInvoiceServlet extends HttpServlet {

    private static final String ADD_JSP = "/newInvoice.jsp";
    public static final String URL_MAPPING = "/newInvoice";

//    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(AddInvoiceServlet.class);

    private boolean validateParams(String params) {
        return params != null && params.length() != 0;
    }

    private void defineInvoice(Object Invoice, String params) {
    }

    /**
     * Gets Invoice Manager from ServletContext.
     *
     * @return MemberManager instance
     */
    private Object getInvoiceManager() {
        return (Object) getServletContext().getAttribute("invoiceManager");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
//        LOG.debug("GET ...");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        String action = request.getPathInfo();
//        LOG.info("POST ... " + request.getQueryString());
//        LOG.debug("POST ... {}",action);
            // TO DO: handle add
            String params = request.getParameter("jhkhkjh");
            if (!validateParams(params)) {
                request.setAttribute("error",
                        "Adding was not successful. Fill in all fields, please!" + action);
//                LOG.debug("invalid data in form");
                return;
            }
            //add invoice
            request.getRequestDispatcher(ADD_JSP).forward(request, response);

    }
}