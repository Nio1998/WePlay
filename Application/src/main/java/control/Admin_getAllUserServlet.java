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

    private UtenteService utenteService = new UtenteService();

    public Admin_getAllUserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Controlla se la sessione esiste e contiene un utente loggato
        if (session == null || session.getAttribute("username") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accesso non autorizzato.");
            return;
        }

        String username = (String) session.getAttribute("username");

        // Verifica se l'utente è un amministratore
        if (!utenteService.is_admin(username)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accesso negato. Solo gli amministratori possono visualizzare questa pagina.");
            return;
        }

        // Recupera tutti gli utenti tramite il servizio UtenteService
        List<UtenteBean> utenti = utenteService.allUtenti();

        // Verifica se ci sono errori nel recupero degli utenti
        if (utenti == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server (Recupero Utenti).");
            return;
        }

        // Imposta gli utenti come attributo della richiesta
        request.setAttribute("utenti", utenti);

        // Pagina verso cui effettuare il forward
        final String redirectedPage = "admin_users.jsp";
        
        // Forward della richiesta alla pagina JSP
        request.getRequestDispatcher(redirectedPage).forward(request, response);
    }
}
