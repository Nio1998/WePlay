package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.utente.UtenteService;

@WebServlet("/ApplicaBan")
public class BanServlet extends HttpServlet {

	private static final String ERROR_PAGE = "/pages/ErrorPage.jsp";
	
    private static final long serialVersionUID = 1L;
    private UtenteService utenteService;

    @Override
    public void init() throws ServletException {
        this.utenteService = new UtenteService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        if (username == null || username.isEmpty()) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            return;
        }

        try {
            if(utenteService.assegnaBan(username)) {
            request.setAttribute("successMessage", "Ban permanente assegnato con successo all'utente: " + username);
            request.getRequestDispatcher("/Admin_getAllUser").forward(request, response);
            } else {
            	request.setAttribute("failureMessage", "L'utente " + username + " è già in stato di ban");
                request.getRequestDispatcher("/Admin_getAllUser").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + ERROR_PAGE);
        }

    }

}
