package servlets;

import backend.InvoiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Servlet for exporting to PDF.
 *
 * @author Tatiana Fritzova
 */
@WebServlet(ExportServlet.URL_MAPPING + "/*")
public class ExportServlet extends HttpServlet{


    private static final String EXPORT_JSP = "/export.jsp";
    public static final String URL_MAPPING = "/export";

    private final static Logger LOG = LoggerFactory.getLogger(ExportServlet.class);

    private InvoiceManager getInvoiceManager() {
        return (InvoiceManager) getServletContext().getAttribute("invoiceManager");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.debug("getting accounting years");
        request.setAttribute("years", getInvoiceManager().getYears());
        try {
            request.getRequestDispatcher(EXPORT_JSP).forward(request, response);
        } catch (ServletException e) {
            LOG.error("could not get accounting years");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("exporting to pdf");
        request.setCharacterEncoding("utf-8");
        request.setAttribute("years", getInvoiceManager().getYears());

        Integer year = Integer.parseInt(request.getParameter("year"));
        File file;

        // check if there is accounting for submitted year
        // -1 stands for all invoices
        if (year == -1) {
            file = getInvoiceManager().exportAllToPfd();
        } else if (getInvoiceManager().getYears().contains(year)) {
            file = getInvoiceManager().exportToPdf(year);
        } else {
            LOG.error("invalid year was submitted");
            request.setAttribute("failure", "Invalid year."+year);
            request.getRequestDispatcher(EXPORT_JSP).forward(request, response);
            return;
        }

        if (file == null) {
            LOG.error("null file returned after export");
            request.setAttribute("failure", "File not found.");
            request.getRequestDispatcher(EXPORT_JSP).forward(request, response);
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader( "Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int) file.length());

        FileInputStream fileInputStream = new FileInputStream(file);
        ServletOutputStream responseOutputStream = response.getOutputStream();
        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            responseOutputStream.write(bytes);
        }
        responseOutputStream.flush();
        responseOutputStream.close();
        fileInputStream.close();

        try {
            request.getRequestDispatcher(EXPORT_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error("could not export");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

