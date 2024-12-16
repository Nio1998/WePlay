package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.utente.UtenteBean;
import model.utente.UtenteDAO;
import model.utente.UtenteService;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteService utenteService;

    public RegisterServlet() {
        super();
        utenteService = new UtenteService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("checkEmail".equals(action)) {
            checkEmail(request, response);
        } else if ("checkUsername".equals(action)) {
            checkUsername(request, response);
        } else {
            registerUser(request, response);
        }
    }

    private void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        boolean emailExists = utenteService.controlla_email_esistente(email);

        response.setContentType("application/json");
        response.getWriter().write("{\"emailExists\": " + emailExists + "}");
    }

    private void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        boolean usernameExists = utenteService.controlla_username_esistente(username);

        response.setContentType("application/json");
        response.getWriter().write("{\"usernameExists\": " + usernameExists + "}");
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Invalida la sessione corrente se esiste
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        // Crea una nuova sessione
        HttpSession session = request.getSession(true);

        // Recupera i parametri di registrazione
        String username = request.getParameter("username");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String pw = utenteService.hash(request.getParameter("password"));
        String data_nascita = request.getParameter("data_nascita");

        // Effettua la registrazione
        boolean registrationSuccess = utenteService.registra_utente(username, nome, cognome, email, pw, data_nascita);

        if (registrationSuccess) {
        	request.setAttribute("successo", "Registrazione effettuata con successo!");
            session.setAttribute("successo", "Registrazione effettuata con successo!");
            session.removeAttribute("errore"); // Rimuovi eventuali errori precedenti

            // Salva il login nella sessione
            UtenteBean u = utenteService.findbyUsername(username);
            session.setAttribute("utente", u);
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("username", u.getUsername());
            
         // Redirect alla servlet del profilo
            response.sendRedirect(request.getContextPath() + "/ProfiloServlet");
        } else {
        	request.setAttribute("errore", "Registrazione fallita. Riprova.");
            session.setAttribute("errore", "Registrazione fallita. Riprova.");
            session.removeAttribute("successo"); // Rimuovi eventuali successi precedenti
            try {
				request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
			} catch (ServletException e) {
				response.sendRedirect(request.getContextPath() + "/pages/ErrorPage.jsp");
				e.printStackTrace();
			} catch (IOException e) {
				response.sendRedirect(request.getContextPath() + "/pages/ErrorPage.jsp");
				e.printStackTrace();
			}
        }
        
    }

}
