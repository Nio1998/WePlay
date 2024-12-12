package control;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Segnalazione.SegnalazioneService;


/**
 * Servlet implementation class SegnalazioneServlet
 */
@WebServlet("/SegnalazioneServlet")
public class InviaSegnalazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private SegnalazioneService  segnalazioneService = new SegnalazioneService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doPost(request, response);  // Chiama direttamente doPost, per mantenere il flusso comune
	    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        String usernameSegnalante = (String) session.getAttribute("username");
	        String usernameSegnalato = request.getParameter("username_segnalato");
	        String stato = request.getParameter("stato");
	        int eventoId = (int) 
	        		session.getAttribute("evento_id");
	        String motivo = request.getParameter("motivo");
	        try {
	        	if(segnalazioneService.segnalazioneGiaEffettuata(usernameSegnalante, usernameSegnalato, eventoId)) {
	        	 request.setAttribute("errore", "Hai già segnalato l'utente in questo evento");
	                request.getRequestDispatcher("dettagliEvento.jsp").forward(request, response);
	                return;
	            }
	        	else {
	            segnalazioneService.inviaSegnalazione(usernameSegnalante, usernameSegnalato ,eventoId, motivo, stato);
	            request.setAttribute("successo", "Hai segnalato l'utente");
	            request.getRequestDispatcher("dettagliEvento.jsp").forward(request, response);
	            return;
	        	}
	        }catch(Exception e){
	        	 e.printStackTrace();
	             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server.");
	        }
	}
}

