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
import javax.servlet.http.HttpSession;

import model.prenotazione.*;

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
        	// Recuperiamo l'username dalla sessione
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
        	
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
            int eventoID = eventoService.crea_evento(titolo, dataInizioLocal, oraInizioLocal, indirizzo, citta, maxPartecipanti, sport, stato, prezzo);

            // Gestione del risultato
            if (eventoID!=-1) {
                request.setAttribute("successo", "Evento creato con successo.");
            } else {
                request.setAttribute("errore", "Errore nella creazione dell'evento.");
                request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
            }
            
            PrenotazioneService prenotazioneService = new PrenotazioneService();
            boolean prenotazioneSuccesso = prenotazioneService.prenota_evento(username, eventoID);
            
         // Gestione del risultato
            if (!prenotazioneSuccesso) {
                request.setAttribute("successo", "Prenotato con successo.");
            } else {
                request.setAttribute("errore", "Errore nella prenotazione.");
                request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
            }
            
            PrenotazioneDAO prenotazioneDao = new PrenotazioneDAO();
            PrenotazioneBean prenotazione = prenotazioneDao.get(username, eventoID);
            prenotazione.setStato("organizzatore");
            prenotazioneDao.update(prenotazione);
            
            response.sendRedirect(request.getContextPath() + "/pages/EsploraEventiServlet?attributo=organizzatore");
        } catch (Exception e) {
            // Gestione di altri errori generali
            e.printStackTrace();
            request.setAttribute("errore", "Si è verificato un errore imprevisto.");
            request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
        }
    }
}
