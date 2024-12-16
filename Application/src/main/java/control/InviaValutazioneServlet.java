package control;

import model.Valutazione.ValutazioneService;
import model.utente.UtenteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/inviaValutazione")
public class InviaValutazioneServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ValutazioneService valutazioneService;
    private UtenteService utenteService;

    @Override
    public void init() throws ServletException {
        this.valutazioneService = new ValutazioneService();
        this.utenteService = new UtenteService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("enter123");
    	String usernameValutante = (String) request.getSession().getAttribute("username");
        String usernameValutato = request.getParameter("utente_valutato");
        String eventoIdParam = request.getParameter("evento"); //ma è l'id
        String esitoParam = request.getParameter("esito");
        String attributoParam = request.getParameter("attributo");
        
        System.out.println("after param1" + "username, valutato, id, esito, attributo" + usernameValutante + usernameValutato + eventoIdParam + esitoParam + attributoParam );
        
        
        try {
            // Validazione parametri
            if (usernameValutante == null || usernameValutato == null || eventoIdParam == null || esitoParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti o non validi");
                return;
            }

            int eventoId = Integer.parseInt(eventoIdParam);
            int esito = Integer.parseInt(esitoParam);
            	
            System.out.println("pre succ123");
            // Invoca il servizio per inviare la valutazione
            boolean successo = valutazioneService.inviaValutazione(usernameValutante, usernameValutato, eventoId, esito);
            
            System.out.println(" succ123" + successo);
            if (successo) {
                
                request.setAttribute("successo", "Valutazione inviata con successo.");
            } else {
                
                request.setAttribute("errore", "Errore durante l'invio della valutazione.");
            }

            // Aggiorna le statistiche dell'utente valutato
            if (esito == 1) {
                utenteService.incrementaValutazioniPositive(usernameValutato);
            } else if (esito == 0) {
                utenteService.incrementaValutazioniNeutre(usernameValutato);
            } else if (esito == -1) {
                utenteService.incrementaValutazioniNegative(usernameValutato);
            }

            // Setta gli attributi aggiuntivi richiesti
            request.setAttribute("attributo", attributoParam);
            request.setAttribute("eventoId", eventoId);
            
            System.out.println(" fine IV" + successo);
            // Inoltra la richiesta alla servlet DettagliEventoServlet
            request.getRequestDispatcher("/DettagliEvento").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato numerico non valido");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore del server: " + e.getMessage());
        }
    }
}
