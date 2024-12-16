package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.utente.UtenteBean;
import model.utente.UtenteService;
import model.Segnalazione.Segnalazione;
import model.Segnalazione.SegnalazioneService;

@WebServlet("/ProfiloServlet")
public class ProfiloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UtenteService utenteService = new UtenteService();
	private SegnalazioneService segnalazioneService = new SegnalazioneService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recupera la sessione esistente (se non esiste, ritorna null)
		HttpSession session = request.getSession(false);

		// Controlla se l'utente è autenticato
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect(request.getContextPath() + "/pages/login.jsp"); // Redirect alla pagina di login se
																					// non loggato
			return;
		}

		// Recupera lo username dalla sessione (utente loggato)
		String currentUsername = (String) session.getAttribute("username");

		// Controlla il parametro `putSegnalazioni`
		boolean putSegnalazioni = "true".equalsIgnoreCase(request.getParameter("putSegnalazioni"));

		String username; // Username del profilo da visualizzare
		if (putSegnalazioni) {
			// Se richiesto, visualizza il profilo di un altro utente (admin)
			username = request.getParameter("username");
			if (username == null || username.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/pages/ErrorPage.jsp"); // Se manca lo username,
																							// mostra errore
				return;
			}

			// Verifica che l'utente loggato sia un admin
			if (!utenteService.is_admin(currentUsername)) {
				response.sendRedirect(request.getContextPath() + "/pages/ErrorPage.jsp"); // Accesso non autorizzato
				return;
			}

			// Ottieni l'utente da visualizzare
			UtenteBean utente = utenteService.findbyUsername(username);
			if (utente == null) {
				response.sendRedirect(request.getContextPath() + "/pages/ErrorPage.jsp"); // Gestione errore (utente non
																							// trovato)
				return;
			}
			request.setAttribute("utente", utente);

			// Calcola le segnalazioni associate all'utente
			try {
				List<Segnalazione> segnalazioni = segnalazioneService.listaSegnalazioni(username);
				request.setAttribute("segnalazioni", segnalazioni);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errore", "Errore nel recupero delle segnalazioni.");
			}

		} else {
			// Visualizza il profilo dell'utente loggato
			username = currentUsername;

			// Ottieni i dati dell'utente loggato
			UtenteBean utente = utenteService.findbyUsername(username);
			if (utente == null) {
				response.sendRedirect(request.getContextPath() + "/pages/ErrorPage.jsp"); // Gestione errore (utente non
																							// trovato)
				return;
			}
			request.setAttribute("utente", utente);

			// Calcoliamo la reputazione per mandarla alla jsp come intero
			int reputazione = utenteService.calcola_reputazione(username);
			request.setAttribute("reputazione", reputazione);
		}

		// Mostra eventuali messaggi e rimuovili dalla sessione
		if (session != null) {
			request.setAttribute("errore", session.getAttribute("errore"));
			request.setAttribute("successo", session.getAttribute("successo"));

			session.removeAttribute("errore");
			session.removeAttribute("successo");
		}

		// Forward alla JSP del profilo
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profilo.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
