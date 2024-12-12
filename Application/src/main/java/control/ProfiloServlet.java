package control;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.utente.UtenteBean;
import model.utente.UtenteService;

@WebServlet("/ProfiloServlet")
public class ProfiloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    UtenteService utenteService=new UtenteService();
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera la sessione esistente (se non esiste, ritorna null)
        HttpSession session = request.getSession(false);

        // Controlla se l'utente è autenticato
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp"); // Redirect alla pagina di login se non loggato
            return;
        }

        // Recupera lo username dalla sessione
        String username = (String) session.getAttribute("username");

        // Usa il servizio per ottenere i dati dell'utente
        UtenteBean utente = utenteService.findbyUsername(username);

        // Controlla se l'utente è stato trovato
        if (utente == null) {
            response.sendRedirect("errore.jsp"); // Gestione errore (utente non trovato)
            return;
        }

        // Imposta l'utente come attributo della richiesta
        request.setAttribute("utente", utente);

        // Forward alla JSP del profilo
        RequestDispatcher dispatcher = request.getRequestDispatcher("profilo.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Per questa servlet, il metodo POST potrebbe non essere necessario.
        // Se non previsto, è possibile semplicemente reindirizzare a doGet.
        doGet(request, response);
    }
}
