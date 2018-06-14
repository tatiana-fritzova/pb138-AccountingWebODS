package servlets;

import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ExportServlet.URL_MAPPING + "/*")
public class ExportServlet extends HttpServlet{


    private static final String LIST_JSP = "/export.jsp";
    public static final String URL_MAPPING = "/export";

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(ListInvoicesServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.info("GET ...");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("POST ...");
        LOG.info("exporting data");
    }
}

