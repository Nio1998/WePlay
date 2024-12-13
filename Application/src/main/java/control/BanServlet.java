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

	private static final String ERROR_PAGE = "errore.jsp";
	
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
        	response.forward(request.getRequestDispatcher(ERROR_PAGE).forward(request, response));
            return;
        }

        try {
            // Assegna un ban permanente all'utente
            utenteService.assegnaBan(username);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Ban permanente assegnato con successo all'utente: " + username);
        } catch (IllegalArgumentException e) {
        	response.forward(request.getRequestDispatcher(ERROR_PAGE).forward(request, response));
        } catch (RuntimeException e) {
        	response.sendRedirect(request.getContextPath() + ERROR_PAGE);
        }
    }
}
