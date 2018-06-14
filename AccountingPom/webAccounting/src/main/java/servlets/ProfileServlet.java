package servlets;

import backend.InvoiceManager;
import backend.Person;
import exceptions.IllegalEntityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Tatiana Fritzova
 */
@WebServlet(ProfileServlet.URL_MAPPING + "/*")
public class ProfileServlet extends HttpServlet {

    private static final String PROFILE_JSP = "/myProfile.jsp";
    public static final String URL_MAPPING = "/myProfile";

    private InvoiceManager getInvoiceManager() {
        return (InvoiceManager) getServletContext().getAttribute("invoiceManager");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("currentName", getInvoiceManager().getOwner().getName());
        request.setAttribute("currentAddress", getInvoiceManager().getOwner().getAddress());
        try {
            request.getRequestDispatcher(PROFILE_JSP).forward(request, response);
        } catch (ServletException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("ownerFullName");
        String address = request.getParameter("ownerAddress");
        if (name == null || name.length()==0 || address == null || address.length()==0) {
            request.setAttribute("failure", "Fill in all fields, please.");
            request.getRequestDispatcher(PROFILE_JSP).forward(request, response);
            return;
        }
        Person newOwner = new Person(name, address);
        getInvoiceManager().setOwner(newOwner);
        request.setAttribute("currentName", getInvoiceManager().getOwner().getName());
        request.setAttribute("currentAddress", getInvoiceManager().getOwner().getAddress());
        request.setAttribute("success", "Owner information was updated.");
        request.getRequestDispatcher(PROFILE_JSP).forward(request, response);
    }
}
