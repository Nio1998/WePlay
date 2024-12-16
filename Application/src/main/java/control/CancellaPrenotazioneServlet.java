package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.evento.EventoService;
import model.prenotazione.PrenotazioneDAO;
import model.prenotazione.PrenotazioneService;
import model.utente.UtenteBean;
import model.prenotazione.*;

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
            EventoService eventoService = new EventoService();
            boolean success = prenotazioneService.cancella_prenotazione(username, eventoID);
            
            if(prenotazioneService.calcola_partecipanti(eventoID) == (eventoService.dettagli_evento(eventoID).getMassimo_di_partecipanti-1)) {
            	
            	PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            	List<PrenotazioneBean> prenotazioni = prenotazioneDAO.findPrenotazioniByEvento(eventoID);

                // Trova l'utente con la posizione in coda minima
                Optional<String> utenteConMinimaPosizione = prenotazioni.stream()
                    .filter(p -> p.getEventoID() == eventoID) // Filtra solo per l'evento specifico
                    .min((p1, p2) -> Integer.compare(p1.getPosizioneInCoda(), p2.getPosizioneInCoda())) // Trova il minimo
                    .map(PrenotazioneBean::getUtenteUsername); // Mappa l'utente associato

                // Stampa il risultato
                if (utenteConMinimaPosizione.isPresent()) {
                    System.out.println("Utente con posizione minima: " + utenteConMinimaPosizione.get());
                    prenotazioneService.prenota_evento(username, eventoID);
                } else {
                    System.out.println("Nessun utente trovato per l'evento " + eventoID);
                }
              	
            }
            	
            
            
            

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
