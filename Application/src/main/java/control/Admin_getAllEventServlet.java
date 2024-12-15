package control;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.evento.Evento;
import model.evento.EventoService;
import model.utente.UtenteService;

@WebServlet("/Admin_getAllEvent")
public class Admin_getAllEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EventoService eventoService = new EventoService();
    private UtenteService utenteService = new UtenteService();
    
    public Admin_getAllEventServlet() {
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

        // Verifica se l'utente � un amministratore
        if (!utenteService.is_admin(username)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accesso negato. Solo gli amministratori possono visualizzare questa pagina.");
            return;
        }

        try {
            // Recupera tutti gli eventi tramite il servizio EventoService
            Collection<Evento> eventi = eventoService.allEventi();

            // Verifica se ci sono errori nel recupero degli eventi
            if (eventi == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server (Recupero Eventi).");
                return;
            }

            // Imposta gli eventi come attributo della richiesta
            request.setAttribute("eventi", eventi);

            // Pagina verso cui effettuare il forward
            final String redirectedPage = "/pages/ListaEventi.jsp";

            // Forward della richiesta alla pagina JSP
            request.getRequestDispatcher(redirectedPage).forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server durante il recupero degli eventi.");
            e.printStackTrace();
        }
    }
}
