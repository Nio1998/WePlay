package control;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.evento.Evento;
import model.evento.EventoService;
import model.prenotazione.PrenotazioneService;
import model.utente.UtenteBean;
import model.utente.UtenteService;
import model.Valutazione.*;

@WebServlet("/DettagliEvento")
public class DettagliEventoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EventoService eventoService = new EventoService(); 
    private PrenotazioneService prenotazioneService = new PrenotazioneService();

    public DettagliEventoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {	
        	
        	
        	// Recupera l'attributo "attributo" dalla richiesta
            String attributo = null;
          
            
            
         // 1. Controlla se esiste un attributo impostato
            if (request.getAttribute("attributo") != null) {
            	
            	attributo = (String) request.getAttribute("attributo");
            	
            } else if (request.getParameter("attributo") != null) {
            	
            	attributo = request.getParameter("attributo");
            	
            	 if (attributo.isEmpty()) {
                 	
                     throw new IllegalArgumentException("Attributo non fornito o non valido.");
                 }
	          }
            
           

         // Verifica se l'attributo è non null e se è uguale a "esploraEventi"
            boolean esploraEventi = attributo != null && attributo.equals("esploraEventi");
        	
            // Recupero ID evento
            //String idEventoParam =  null;
            
        	int idEvento = 0;
        	String idEventoParam = null;
            
            // 1. Controlla se esiste un attributo impostato
            if (request.getAttribute("eventoId") != null) {
            	
            	idEvento = (int) request.getAttribute("eventoId");
            	
            } else if (request.getParameter("eventoId") != null) {
            	
            	idEventoParam = request.getParameter("eventoId");
            	
            	 if (idEventoParam.isEmpty()) {
                 	
                     throw new IllegalArgumentException("ID evento non fornito o non valido.");
                 }
            	
            	idEvento = Integer.parseInt(idEventoParam);
            	
	            }
           
            if (!eventoService.esiste_evento(idEvento)) {
                throw new IllegalArgumentException("Evento non esistente.");
            }
           

            // Recupera dettagli evento
            Evento evento = eventoService.dettagli_evento(idEvento);
            if (evento == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Evento non trovato.");
                return;
            }
            request.setAttribute("evento", evento);
            
            
            // Recupera lista partecipanti
            Collection<UtenteBean> partecipanti = prenotazioneService.calcola_partecipanti(idEvento);
           
            
           
            
            // Controlla stato utente corrente
            HttpSession session = request.getSession(false);
           
            String currentUser = (session != null) ? (String) session.getAttribute("username") : null;
            
           
            boolean isPartecipante = false;
            boolean isOrganizzatore = false;
            
            if (currentUser != null) {
            	
                isPartecipante = partecipanti.stream().anyMatch(p -> p.getUsername().equals(currentUser));
                
                String organizzatore = prenotazioneService.findOrganizzatoreByEventoID(evento.getID());
                
                //questo perchè per gli inserimenti attuali alcuni eventi non hannno organizzatori
                if(organizzatore !=null)isOrganizzatore = organizzatore.equals(currentUser);
                
               
            }
            
           
           
            request.setAttribute("isPartecipante", isPartecipante);
            request.setAttribute("isOrganizzatore", isOrganizzatore);
            

          
            // Calcola stato dell'evento
            boolean isTerminato = "finito".equals(evento.getStato());
            request.setAttribute("isTerminato", isTerminato);

            long currentTimeMillis = System.currentTimeMillis();
            boolean isLessThan24HoursBeforeStart = false;
            boolean isWithin24HoursAfterEnd = false;

            if (evento.getData_inizio() != null) {
                long eventStartMillis = evento.getData_inizio()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

                // Aggiungi 2 ore all'orario di inizio per determinare la fine dell'evento
                long eventEndMillis = eventStartMillis + (2 * 60 * 60 * 1000); // 2 ore in millisecondi

                // Verifica se l'evento inizia entro le prossime 24 ore
                isLessThan24HoursBeforeStart = eventStartMillis - currentTimeMillis <= 24 * 60 * 60 * 1000;

                // Verifica se siamo entro le 24 ore dalla fine dell'evento
                isWithin24HoursAfterEnd = currentTimeMillis - eventEndMillis <= 24 * 60 * 60 * 1000;
            }
            
            
            request.setAttribute("isLessThan24HoursBeforeStart", isLessThan24HoursBeforeStart);
            request.setAttribute("isWithin24HoursAfterEnd", isWithin24HoursAfterEnd);
            
         // Controlla se è richiesto di gestire le valutazioni
            
            
            
            
            if (!esploraEventi) {
            	
            	
            	UtenteService utenteService = new UtenteService();
            	ValutazioneService valutazioneService = new ValutazioneService();

               
				if (!utenteService.controlla_username_esistente(currentUser)) {
                    throw new IllegalArgumentException("Utente valutante non esistente.");
                }

                // Recupera le valutazioni effettuate dall'utente loggato
                List<ValutazioneBean> valutazioni = null;
				
                //non so perchè il try catch, mi dava errore
                try {
					valutazioni = valutazioneService.calcola_valutazioni_da_utente(currentUser, idEvento);
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                // Mappa per associare ogni utente con la sua valutazione (se presente)
                Map<UtenteBean, Integer> utentiValutazioni = new HashMap<>();

                for (UtenteBean u : partecipanti) {
                    int esito = -2; // Default: nessuna valutazione
                    for (ValutazioneBean v : valutazioni) {
                        if (v.getUtenteValutato().equals(u.getUsername())) {
                            esito = v.getEsito();
                            break;
                        }
                    }
                    
                   
                    utentiValutazioni.put(u, esito);
                }
                
                
                // Imposta la mappa come attributo della richiesta
                request.setAttribute("utentiValutazione", utentiValutazioni);
                
            } else {
                // Imposta solo la lista degli utenti come attributo della richiesta
                
               
            }
            
           
            // Forward alla JSP
            request.setAttribute("attributo", attributo);
            request.setAttribute("partecipanti", partecipanti);
           
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/DettaglioEvento.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errore", "Errore: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
        } 
        
    }
    
    // Metodo POST che richiama il metodo GET
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chiamata al metodo doGet per trattare la richiesta POST come una GET
        doGet(request, response);
    }
}
