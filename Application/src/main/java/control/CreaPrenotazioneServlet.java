package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PrenotazioneService;  // Importa la classe del service

@WebServlet("/CreaPrenotazione")
public class CreaPrenotazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PrenotazioneService prenotazioneService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        prenotazioneService = new PrenotazioneService(); // Inizializzazione della service
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try { // Otteniamo i parametri della prenotazione dalla richiesta
        String utenteUsername = request.getParameter("utenteUsername");
        int eventoID = Integer.parseInt(request.getParameter("eventoID"));
        String stato = request.getParameter("stato");
        int posizioneInCoda = Integer.parseInt(request.getParameter("posizioneInCoda"));

       
            // Controlliamo se l'utente è registrato
            if (!PrenotazioneService.is_utente_registrato(utenteUsername)) {
                request.setAttribute("errore", "l'utente non è registrato.");
                request.getRequestDispatcher("dettaglioEvento.jsp").forward(request, response);
                return;
            }
            // Utilizziamo il service per prenotare l'evento
            boolean prenotazioneSuccesso = prenotazioneService.prenota_evento(utenteUsername, eventoID); // Chiamata al metodo prenota_evento

            // Se la prenotazione è riuscita, facciamo il reindirizzamento alla pagina di successo
            if (prenotazioneSuccesso) {
              // Forward alla pagina di dettaglio
              request.setAttribute("successo", "Prenotazione effettuatata con successo.");
              request.getRequestDispatcher("dettaglioEvento.jsp").forward(request, response);   
            } else {
                request.setAttribute("errore");
                request.getRequestDispatcher("dettaglioEvento.jsp").forward(request, response);
            }
       } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errore.jsp");  // Reindirizza alla pagina di errore in caso di eccezione
        }
    }
}
