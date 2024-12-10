package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.utente.UtenteService;

@WebServlet("/ModificaDatiServlet")
public class ModificaDatiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UtenteService utenteService = new UtenteService(); // Assumi che UtenteService sia la classe che gestisce la logica di business

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  // Chiama direttamente doPost, per mantenere il flusso comune
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp"); // Se non loggato, rimanda al login.
            return;
        }

        String nuovaEmail = request.getParameter("email");
        String nuovaPassword = request.getParameter("password");

        try {
            // Controllo se l'email è già esistente
            if (utenteService.controlla_email_esistente(nuovaEmail)) {
                request.setAttribute("errore", "L'email è già in uso da un altro utente.");
                request.getRequestDispatcher("profilo.jsp").forward(request, response);
                return;
            }

            // Aggiorna i dati dell'utente
            utenteService.modifica_dati(username, nuovaEmail, nuovaPassword);

            // Aggiorna la sessione con la nuova email
            session.setAttribute("email", nuovaEmail);

            request.setAttribute("messaggio", "Modifica effettuata con successo.");
            request.getRequestDispatcher("profilo.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno del server.");
        }
    }
}
