package control;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.utente.UtenteService;

@WebServlet("/ApplicaTimeout")
public class TimeOutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UtenteService utenteService;

	@Override
	public void init() throws ServletException {
		this.utenteService = new UtenteService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = (String) request.getParameter("username");
		

		if (username == null || username.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username non valido o mancante.");
			return;
		}

		try {
			
				// Assegna timeout basato sul numero di timeout già  ricevuti
				if (utenteService.assegnaTimeout(username)) {
					request.setAttribute("timeoutMessage", "Timeout assegnato con successo all'utente: " + username);
					request.getRequestDispatcher("/Admin_getAllUser").forward(request, response);

				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Azione non valida. Usa 'assegna' o 'aggiorna'.");
				}
			
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Durata del timeout non valida.");
			e.printStackTrace();
		} catch (RuntimeException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante la gestione del timeout.");
			e.printStackTrace();
		}
	}
}
