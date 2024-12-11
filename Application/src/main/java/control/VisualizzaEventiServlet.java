package control;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConDB;
import model.evento.Evento;
import model.evento.EventoService;
//import model.OrderModel;
import model.utente.*;

@WebServlet("/VisualizzaEventi")
public class VisualizzaEventiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private EventoService eventoService = new EventoService(); 

    public VisualizzaEventiServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dataInizio = request.getParameter("dataInizio");
        String dataFine = request.getParameter("dataFine");
        String sport = request.getParameter("sport");
        String citta = request.getParameter("citta");
        
        final String redirectedPage = "eventi.jsp";
        
        Collection<Evento> eventiFiltrati = eventoService.filtra_eventi(dataInizio, dataFine, sport, citta);
        
        if (eventiFiltrati == null) {
        	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server (Visualizza Eventi).");
        	return;
        }
        
        request.setAttribute("eventi", eventiFiltrati);
        request.getRequestDispatcher(redirectedPage).forward(request, response);
    }
        
}
