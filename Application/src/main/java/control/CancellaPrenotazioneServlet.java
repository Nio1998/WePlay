package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.prenotazione.PrenotazioneDAO;
import model.prenotazione.PrenotazioneService;
import model.utente.UtenteBean;

@WebServlet("/CancellaPrenotazione")
public class CancellaPrenotazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utente non autenticato.");
            return;
        }

        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        String username = utente.getUsername();
        
        String eventoIDStr = request.getParameter("eventoID");
        if (eventoIDStr == null || eventoIDStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID evento mancante o non valido.");
            return;
        }

        try {
            int eventoID = Integer.parseInt(eventoIDStr);
            PrenotazioneService prenotazioneService = new PrenotazioneService();
            boolean success = prenotazioneService.cancella_prenotazione(username, eventoID);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Prenotazione cancellata con successo.");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nella cancellazione della prenotazione.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID evento non valido.");
        }
    }
}
