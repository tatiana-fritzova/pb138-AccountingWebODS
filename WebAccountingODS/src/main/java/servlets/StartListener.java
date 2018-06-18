package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import backend.Invoice;
import backend.InvoiceManager;
import backend.InvoiceManagerImpl;
import backend.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * Initializes context of the web application.
 *
 * @author Tatiana Fritzova
 */
@WebListener
public class StartListener implements ServletContextListener {

    private final static Logger LOG = LoggerFactory.getLogger(StartListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.debug("initializing web application");
        ServletContext servletContext = servletContextEvent.getServletContext();
      //  String path = servletContext.getRealPath("/WEB-INF/evidence.ods");
        InvoiceManager invoiceManager = new InvoiceManagerImpl();
        servletContext.setAttribute("invoiceManager", invoiceManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.debug("terminating application");
    }
}
