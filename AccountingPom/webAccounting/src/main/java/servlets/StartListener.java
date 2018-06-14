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
 * @author Tatiana Fritzova
 */
@WebListener
public class StartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        InvoiceManager invoiceManager = new InvoiceManagerImpl();
        if (invoiceManager.getOwner() == null) {
            servletContext.setAttribute("noowner", true);
            invoiceManager.setOwner(new Person( "owner name", "owner address"));
        }
        servletContext.setAttribute("invoiceManager", invoiceManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
