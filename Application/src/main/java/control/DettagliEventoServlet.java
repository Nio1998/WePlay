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

@WebServlet("/DettagliEvento")
public class DettagliEventoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private EventoService eventoService = new EventoService(); 

    public DettagliEventoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEvento = request.getParameter("id");	//ID Evento passato nell'url
        
        final String redirectedPage = "dettagliEvento.jsp";
        
        Evento e = eventoService.dettagli_evento(idEvento);
        
        if (e == null) {
        	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server (Dettagli Evento).");
        	return;
        }
        
        request.setAttribute("evento", e);
        request.getRequestDispatcher(redirectedPage).forward(request, response);
    }
        
}
