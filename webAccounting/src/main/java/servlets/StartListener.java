package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tatiana Fritzova
 */
@WebListener
public class StartListener implements ServletContextListener {

//    private final static Logger LOG = LoggerFactory.getLogger(StartListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        LOG.info("Initializing web application.");
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("invoiceManager", new Object());
//        LOG.info("Invoice manager created and stored into servletContext");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        LOG.info("Application terminated.");
    }
}
