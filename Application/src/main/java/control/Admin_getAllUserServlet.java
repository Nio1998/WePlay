package control;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.utente.UtenteBean;
import model.utente.UtenteService;

@WebServlet("/Admin_getAllUser")
public class Admin_getAllUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String ACCESSO_VIETATO_PAGE = "/AccessoVietato/AccessoVietato.jsp";
    private static final String ERROR_PAGE = "/ErrorPage.jsp";
    private static final String ADMIN_USERS_PAGE = "admin_users.jsp";

    private UtenteService utenteService = new UtenteService();

    public Admin_getAllUserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Controlla se la sessione esiste e contiene un utente loggato
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + ERROR_PAGE);
            return;
        }

        String username = (String) session.getAttribute("username");

        // Verifica se l'utente è un amministratore
        if (!utenteService.is_admin(username)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendRedirect(request.getContextPath() + ACCESSO_VIETATO_PAGE);
            return;
        }

        try {
            // Recupera tutti gli utenti tramite il servizio UtenteService
            List<UtenteBean> utenti = utenteService.allUtenti();

            // Verifica se ci sono errori nel recupero degli utenti
            if (utenti == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.sendRedirect(request.getContextPath() + ERROR_PAGE);
                return;
            }

            // Imposta gli utenti come attributo della richiesta
            request.setAttribute("utenti", utenti);

            // Forward alla pagina JSP
            request.getRequestDispatcher(ADMIN_USERS_PAGE).forward(request, response);
        } catch (Exception e) {
            // Log dell'errore per debug
            System.err.println("Errore durante il recupero degli utenti: " + e.getMessage());
            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect(request.getContextPath() + ERROR_PAGE);
        }
    }
}
