package control;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.evento.EventoService;

@WebServlet("/ModificaEventoServlet")
public class ModificaEventoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private EventoService eventoService; // Variabile di istanza per la service

    @Override
    public void init() throws ServletException {
        super.init();
        eventoService = new EventoService(); // Inizializzazione della service
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recupero parametri dal form
            int eventoId = Integer.parseInt(request.getParameter("eventoId"));
            String titolo = request.getParameter("titolo");
            LocalDate data = LocalDate.parse(request.getParameter("data"));
            LocalTime ora = LocalTime.parse(request.getParameter("ora"));
            String indirizzo = request.getParameter("indirizzo");
            String città = request.getParameter("citta");
            int maxPartecipanti = Integer.parseInt(request.getParameter("max_partecipanti"));

            // Validazione delle precondizioni
            if (eventoId <= 0 ||
                titolo == null || titolo.trim().isEmpty() || 
                data.isBefore(LocalDate.now().plusDays(1)) || 
                ora == null || 
                indirizzo == null || 
                città == null || 
                maxPartecipanti <= 0 ||
                !eventoService.esiste_evento(eventoId)) {
                
                request.setAttribute("errore", "Parametri non validi o evento non esistente.");
                request.getRequestDispatcher("modificaEvento.jsp").forward(request, response);
                return;
            }

            // Modifica evento
            boolean successo = eventoService.modifica_evento(
                eventoId, titolo, data, ora, indirizzo, città, maxPartecipanti
            );

            if (successo) {
                // Forward alla pagina di dettaglio
                request.setAttribute("successo", "Evento modificato con successo.");
                request.getRequestDispatcher("DettagliEvento?eventoId=" + eventoId).forward(request, response);
            } else {
                // Errore nella modifica
                request.setAttribute("errore", "Impossibile modificare l'evento. Riprova.");
                request.getRequestDispatcher("modificaEvento.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Gestione di eventuali errori imprevisti
            e.printStackTrace();
            request.setAttribute("errore", "Errore interno. Contatta l'amministratore.");
            request.getRequestDispatcher("Evento.jsp").forward(request, response);
        }
    }
}

