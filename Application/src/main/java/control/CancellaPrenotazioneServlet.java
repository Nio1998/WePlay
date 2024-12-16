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

@WebServlet("/CancellaPrenotazioneServlet")
public class CancellaPrenotazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	String attributo = (String) request.getParameter("attributo");
    	if (attributo == null || attributo.isEmpty()) {
        	request.setAttribute("errore", "Attributo non valido.");
            request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
            return;
        }
    	
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utente non autenticato.");
            return;
        }

        String username = (String) session.getAttribute("username");  // Effettua il cast a String
        
        String eventoIDStr = request.getParameter("evento"); //ma è l'id
        if (eventoIDStr == null || eventoIDStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID evento mancante o non valido.");
            return;
        }
        
       

        try {
            int eventoID = Integer.parseInt(eventoIDStr);
            PrenotazioneService prenotazioneService = new PrenotazioneService();
            boolean success = prenotazioneService.cancella_prenotazione(username, eventoID);
            
            

            if (success) {
                request.setAttribute("successo", "Prenotazione cancellata con successo.");
            } else {
                request.setAttribute("errore", "Errore nella cancellazione della prenotazione.");
            }

            // Forward to the event detail page with the event ID
            request.setAttribute("eventoId", eventoID);
            request.setAttribute("attributo", attributo);
            request.getRequestDispatcher("/DettagliEvento").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID evento non valido.");
        }
    }
}
