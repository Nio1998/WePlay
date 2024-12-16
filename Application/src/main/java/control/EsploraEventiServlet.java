package control;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.evento.Evento;
import model.evento.EventoService;

@WebServlet("/EsploraEventiServlet")
public class EsploraEventiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private EventoService eventoService = new EventoService(); 

    public EsploraEventiServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    
    	
        String dataInizio = request.getParameter("dataInizio");
        String dataFine = request.getParameter("dataFine");
        String sport = request.getParameter("sport");
        String citta = request.getParameter("citta");
        String attributo = request.getParameter("attributo");  // parametro per determinare cosa filtrare
        
        // Recuperiamo l'username dalla sessione
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null || username.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username mancante nella sessione.");
            return;
        }
        
        String redirectedPage = "/pages/eventiSottoscritti.jsp";  // Default page
        
       
        if (attributo != null) {
            // Gestiamo i vari casi dell'attributo
            switch (attributo) {
                case "sottoscritto":
                	Collection<Evento> eventiSottoscritti = eventoService.visualizza_eventi_sottoscritti(username);
                	if (eventiSottoscritti == null) {
                	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero degli eventi sottoscritti.");
                	    return;
                	}

                	// Ordina e raccogli in una lista
                	List<Evento> listaEventiSottoscritti = eventiSottoscritti.stream()
                	    .sorted(Comparator.comparing(Evento::getData_inizio))
                	    .collect(Collectors.toList());

                	request.setAttribute("eventi", listaEventiSottoscritti);
                	redirectedPage = "/pages/eventiSottoscritti.jsp";  // Per gli eventi sottoscritti
                	break;
                    
                case "esploraEventi":
                    // Carichiamo gli eventi attivi (non finiti)
                    List<Evento> eventiFiltrati = (List<Evento>) eventoService.filtra_eventi(dataInizio, dataFine, sport, citta, "", "", "", "", "", "", "non finito");
                    if (eventiFiltrati == null) {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno nel recupero degli eventi attivi.");
                        return;
                    }
                    request.setAttribute("eventi", eventiFiltrati);
                    redirectedPage = "/pages/esploraEventi.jsp";  // Per gli eventi attivi
                    break;
                    
                case "organizzatore":
                	
                	System.out.println("enter1dfd");
                    //Carichiamo gli eventi creati dall'utente
                    Collection<Evento> eventiCreati = eventoService.visualizza_eventi_creati(username);
                    System.out.println("after");
                    
                    if (eventiCreati == null) {
                    	System.out.println("err1");
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero degli eventi creati.");
                        return;
                    }
                    request.setAttribute("eventi", eventiCreati);
                    System.out.println("success");
                    redirectedPage = "/pages/eventiCreati.jsp";  // Per gli eventi creati dall'organizzatore
                    break;
                    
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Attributo non valido.");
                    
                    return;
            }
        }
        
        // Reindirizziamo alla pagina appropriata
        request.getRequestDispatcher(redirectedPage).forward(request, response);
    }
}
