package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.evento.EventoService;

@WebServlet("/CreaEventoServlet")
public class CreaEventoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EventoService eventoService;

    @Override
    public void init() throws ServletException {
        super.init();
        eventoService = new EventoService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recupera i parametri dalla richiesta
            String titolo = request.getParameter("titolo");
            String dataInizio = request.getParameter("data");
            String oraInizio = request.getParameter("ora");
            String citta = request.getParameter("citta");
            String indirizzo = request.getParameter("indirizzo");
            String sport = request.getParameter("sport");
            String stato = "non iniziato";
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            
            // Converte i parametri data e ora
            LocalDate dataInizioLocal = LocalDate.parse(dataInizio);
            LocalTime oraInizioLocal = LocalTime.parse(oraInizio);
            
         // Gestione del parametro maxPartecipanti
            int maxPartecipanti = 0;
            try {
                maxPartecipanti = Integer.parseInt(request.getParameter("partecipanti")); // Nome corretto
            } catch (NumberFormatException e) {
                System.out.println("Errore: parametro partecipanti non valido");
                request.setAttribute("errore", "Il numero massimo di partecipanti non è valido.");
                request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
                return;
            }

            // Chiamata al servizio per creare l'evento
            boolean eventoSuccesso = eventoService.crea_evento(titolo, dataInizioLocal, oraInizioLocal, indirizzo, citta, maxPartecipanti, sport, stato, prezzo);

            // Gestione del risultato
            if (eventoSuccesso) {
                request.setAttribute("successo", "Evento creato con successo.");
                request.getRequestDispatcher("/pages/index.jsp").forward(request, response);
            } else {
            	System.out.println("Cazzo 3");
                request.setAttribute("errore", "Errore nella creazione dell'evento.");
                request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
            }
        } catch (Exception e) {
        	System.out.println("Cazzo 4");
            // Gestione di altri errori generali
            e.printStackTrace();
            request.setAttribute("errore", "Si è verificato un errore imprevisto.");
            request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
        }
    }
}
