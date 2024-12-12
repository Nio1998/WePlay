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
        String usernameValutante = request.getParameter("username_valutante");
        String usernameValutato = request.getParameter("username_valutato");
        String eventoIdParam = request.getParameter("evento_id");
        String esitoParam = request.getParameter("esito");

        try {
            // Validazione parametri
            if (usernameValutante == null || usernameValutato == null || eventoIdParam == null || esitoParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti o non validi");
                return;
            }

            int eventoId = Integer.parseInt(eventoIdParam);
            int esito = Integer.parseInt(esitoParam);

            // Invoca il servizio per inviare la valutazione
            boolean successo = valutazioneService.inviaValutazione(usernameValutante, usernameValutato, eventoId, esito);
            
         // Aggiorna le statistiche dell'utente valutato
            if (esito == 1) {
                utenteService.incrementaValutazioniPositive(usernameValutato);
            } else if (esito == 0) {
                utenteService.incrementaValutazioniNeutre(usernameValutato);
            } else if (esito == -1) {
                utenteService.incrementaValutazioniNegative(usernameValutato);
            }

            if (successo) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Valutazione inviata con successo");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'invio della valutazione");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato numerico non valido");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore del server: " + e.getMessage());
        }
    }
}