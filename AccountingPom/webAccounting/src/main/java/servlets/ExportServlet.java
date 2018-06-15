package servlets;

import backend.InvoiceManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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

    private InvoiceManager getInvoiceManager() {
        return (InvoiceManager) getServletContext().getAttribute("invoiceManager");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("years", getInvoiceManager().getYears());

        try {
            request.getRequestDispatcher(EXPORT_JSP).forward(request, response);
        } catch (ServletException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        Integer year = Integer.parseInt(request.getParameter("year"));
//        if (!getInvoiceManager().getYears().contains(year)) {
//            //set error
//        }
//        if (year < 0) {
//            getInvoiceManager().exportAllToPfd();
//        } else {
//            getInvoiceManager().exportToPdf(year);
//        }
////
//        File file = getInvoiceManager().getFile(year);
//        response.setContentType("application/pdf");

        try {
            request.getRequestDispatcher(EXPORT_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

