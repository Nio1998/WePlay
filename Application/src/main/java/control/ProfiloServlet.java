package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.utente.UtenteBean;
import model.utente.UtenteService;
import model.Segnalazione.Segnalazione;
import model.Segnalazione.SegnalazioneService;

@WebServlet("/ProfiloServlet")
public class ProfiloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UtenteService utenteService = new UtenteService();
    private SegnalazioneService segnalazioneService = new SegnalazioneService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Controlla se l'utente è autenticato
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }

        String currentUsername = (String) session.getAttribute("username");
        boolean isAdmin = utenteService.is_admin(currentUsername);

        // Recupera l'username del profilo da visualizzare
        String username = request.getParameter("username");
        if (username == null || username.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/pages/ErrorPage.jsp");
            return;
        }

        // Recupera l'utente selezionato
        UtenteBean utente = utenteService.findbyUsername(username);
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/pages/ErrorPage.jsp");
            return;
        }

        request.setAttribute("utente", utente);

        try {
            // Calcola la reputazione dell'utente visualizzato
            int reputazione = utenteService.calcola_reputazione(username);
            request.setAttribute("reputazione", reputazione);

            // Se chi visualizza è admin, recupera le segnalazioni
            if (isAdmin) {
                List<Segnalazione> segnalazioni = segnalazioneService.listaSegnalazioni(username);
                request.setAttribute("segnalazioni", segnalazioni);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore nel recupero dei dati del profilo.");
        }

        // Forward alla JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profilo.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
