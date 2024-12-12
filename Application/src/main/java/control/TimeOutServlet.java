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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String azione = request.getParameter("azione"); // azione specifica: "assegna" o "aggiorna"

        if (username == null || username.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username non valido o mancante.");
            return;
        }

        try {
            if ("assegna".equalsIgnoreCase(azione)) {
                // Assegna timeout basato sul numero di timeout già ricevuti
                utenteService.assegnaTimeout(username);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Timeout assegnato con successo per l'utente: " + username);
            } else if ("aggiorna".equalsIgnoreCase(azione)) {
                // Aggiorna direttamente lo stato di timeout con parametri personalizzati
                String durataTimeoutStr = request.getParameter("durataTimeout");
                if (durataTimeoutStr == null || durataTimeoutStr.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Durata del timeout mancante o non valida.");
                    return;
                }
                int durataTimeout = Integer.parseInt(durataTimeoutStr);

                LocalDateTime dataOraFineTimeout = LocalDateTime.now().plusHours(durataTimeout);
                utenteService.aggiornaTimeoutUtente(username, true, dataOraFineTimeout);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Timeout aggiornato con successo per l'utente: " + username);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Azione non valida. Usa 'assegna' o 'aggiorna'.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Durata del timeout non valida.");
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante la gestione del timeout.");
        }
    }
}
