package control;

import model.Segnalazione.Segnalazione;
import model.Segnalazione.SegnalazioneService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/VisualizzaSegnalazioni")
public class VisualizzaSegnalazioniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Boolean isAdmin = (Boolean) session.getAttribute("is_admin");

        // Controllo preliminare dei permessi
        if (username == null || isAdmin == null || !isAdmin) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accesso negato.");
            return;
        }

        SegnalazioneService segnalazioneService = new SegnalazioneService();

        try {
            // Recupera la lista delle segnalazioni per l'utente
            List<Segnalazione> segnalazioni = segnalazioneService.listaSegnalazioni(username);

            // Imposta la lista come attributo della richiesta
            request.setAttribute("segnalazioni", segnalazioni);

            // Reindirizza alla JSP
            request.getRequestDispatcher("listaSegnalazioni.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Errore nel recupero delle segnalazioni", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
