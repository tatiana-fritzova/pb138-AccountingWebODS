package servlets;

import backend.InvoiceManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for exporting to PDF.
 *
 * @author Tatiana Fritzova
 */
@WebServlet(ExportServlet.URL_MAPPING + "/*")
public class ExportServlet extends HttpServlet{


    private static final String EXPORT_JSP = "/export.jsp";
    public static final String URL_MAPPING = "/export";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("doget", "TOTO JE DOGET");
        try {
            request.getRequestDispatcher(EXPORT_JSP).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("dopost", "TOTO JE DOPOST");
        InvoiceManager invoiceManager = (InvoiceManager) getServletContext().getAttribute("invoiceManager");
        invoiceManager.exportAllToPfd();
        //export by mohol vracat File file = new File("volaco.pdf");

        try {
            request.getRequestDispatcher(EXPORT_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

