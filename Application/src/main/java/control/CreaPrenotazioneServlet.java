package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.prenotazione.PrenotazioneService;
import model.utente.UtenteService;
import model.evento.EventoService;

@WebServlet("/CreaPrenotazioneServlet")
public class CreaPrenotazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PrenotazioneService prenotazioneService;
    private UtenteService utenteService;
    private EventoService eventoService;

    @Override
    public void init() throws ServletException {
        super.init();
        prenotazioneService = new PrenotazioneService();
        utenteService = new UtenteService();  // Inizializza UtenteService
        eventoService = new EventoService();  // Inizializza EventoService
    }
  

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("enter 0");
    	
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
        
        
        String username = (String) session.getAttribute("username");  // Prendi l'username dalla sessione
       
        try {
        	
            int eventoID = Integer.parseInt(request.getParameter("evento")); //ma è l'id
            	
            
            // Precondizioni per la prenotazione
            if (username == null || username.isEmpty()) {
            	
            	
            	
                request.setAttribute("errore", "Username non valido.");
                request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
                return;
            }
            if (utenteService.findbyUsername(username)==null) {
            	
                request.setAttribute("errore", "Utente non registrato.");
                request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
                return;
            }
            if (!eventoService.esiste_evento(eventoID)) {
            	
                request.setAttribute("errore", "Evento non esistente.");
                request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
                return;
            }
            
            
            // Prenotazione evento
            boolean prenotazioneSuccesso = prenotazioneService.prenota_evento(username, eventoID);
                        
            System.out.println("enter 1");
            // Se la prenotazione ha successo, controlliamo la lista di attesa
            if (prenotazioneSuccesso) {
            	
                boolean inAttesa = !eventoService.evento_ha_posti_disponibili(eventoID);
                System.out.println("enter 2");
                
                
                if (inAttesa) {
                    request.setAttribute("successo", "Inserimento nella coda effettuato con successo.");
                } else {
                    request.setAttribute("successo", "Prenotazione effettuata con successo.");
                }
                // Forward alla pagina di dettaglio evento
                System.out.println("id crea p" + eventoID);
                
                request.setAttribute("eventoId", eventoID);
                request.setAttribute("attributo", attributo);
                
                
                request.getRequestDispatcher("/DettagliEvento").forward(request, response);
            } else {
                // Gestione errore in caso di prenotazione fallita
                request.setAttribute("errore", "Errore nella prenotazione dell'evento.");
                request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            // Gestione errore se il parametro eventoID non è valido
            e.printStackTrace();
            request.setAttribute("errore", "ID evento non valido.");
            request.getRequestDispatcher("/pages/ErrorPage.jsp").forward(request, response);
        }
    }
}
