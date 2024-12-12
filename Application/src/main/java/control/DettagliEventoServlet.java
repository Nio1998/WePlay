package control;

import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.evento.*;
import model.utente.*;
import model.Valutazione.*;
import model.prenotazione.*;

@WebServlet("/DettagliEvento")
public class DettagliEventoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EventoService eventoService = new EventoService(); 
    private PrenotazioneService prenotazioneService = new PrenotazioneService();
    private ValutazioneService valutazioneService = new ValutazioneService();
    private UtenteService utenteService = new UtenteService();

    public DettagliEventoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recupera i parametri dalla richiesta
            String idEventoParam = request.getParameter("id");
            String putValutazioneParam = request.getParameter("putValutazione");

            // Validazione dei parametri
            if (idEventoParam == null || idEventoParam.isEmpty()) {
                throw new IllegalArgumentException("ID evento non fornito o non valido.");
            }

            int idEvento;
            try {
                idEvento = Integer.parseInt(idEventoParam);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID evento non valido.");
            }

            if (idEvento <= 0) {
                throw new IllegalArgumentException("ID evento deve essere maggiore di 0.");
            }

            // Controlla l'esistenza dell'evento
            if (!eventoService.esiste_evento(idEvento)) {
                throw new IllegalArgumentException("Evento non esistente.");
            }

            // Recupera i dettagli dell'evento
            Evento e = eventoService.dettagli_evento(idEventoParam);
            if (e == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server (Dettagli Evento).");
                return;
            }

            // Recupera i partecipanti
            Collection<UtenteBean> utenti = prenotazioneService.calcola_partecipanti(idEvento);
            if (utenti == null) {
                utenti = new ArrayList<>();
            }

            request.setAttribute("evento", e);

            // Controlla se è richiesto di gestire le valutazioni
            boolean putValutazione = putValutazioneParam != null && Boolean.parseBoolean(putValutazioneParam);

            if (putValutazione) {
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("username") == null) {
                    throw new IllegalArgumentException("Utente non autenticato.");
                }

                String usernameValutante = (String) session.getAttribute("username");

                if (!utenteService.controlla_username_esistente(usernameValutante)) {
                    throw new IllegalArgumentException("Utente valutante non esistente.");
                }

                // Recupera le valutazioni effettuate dall'utente loggato
                List<ValutazioneBean> valutazioni = valutazioneService.calcola_valutazioni_da_utente(usernameValutante, idEvento);

                // Mappa per associare ogni utente con la sua valutazione (se presente)
                Map<UtenteBean, Integer> utentiValutazioni = new HashMap<>();

                for (UtenteBean u : utenti) {
                    int esito = -1; // Default: nessuna valutazione
                    for (ValutazioneBean v : valutazioni) {
                        if (v.getUtenteValutato().equals(u.getUsername())) {
                            esito = v.getEsito();
                            break;
                        }
                    }
                    utentiValutazioni.put(u, esito);
                }

                // Imposta la mappa come attributo della richiesta
                request.setAttribute("utentiValutazioni", utentiValutazioni);
            } else {
                // Imposta solo la lista degli utenti come attributo della richiesta
                request.setAttribute("utenti", utenti);
            }

            // Forward alla JSP dei dettagli evento
            RequestDispatcher dispatcher = request.getRequestDispatcher("dettagliEvento.jsp");
            dispatcher.forward(request, response);

        } catch (IllegalArgumentException e) {
            // Gestione degli errori di validazione
            request.setAttribute("errore", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("errore.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // Gestione di eventuali altri errori
            e.printStackTrace();
            request.setAttribute("errore", "Si è verificato un errore inatteso.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("errore.jsp");
            dispatcher.forward(request, response);
        }
    }
}
