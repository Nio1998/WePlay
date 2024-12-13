package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.evento.EventoService;
import model.utente.UtenteService;

@WebServlet("/CancellaEventoServlet")
public class CancellaEventoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		HttpSession session = request.getSession();
        String utenteLoggato = (String) session.getAttribute("username");

        if (utenteLoggato == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int eventoId = Integer.parseInt(request.getParameter("evento_id"));

            EventoService eventoService = new EventoService();
            UtenteService utenteService = new UtenteService();

            if (!eventoService.esiste_evento(eventoId)) {
                request.setAttribute("errore", "L'evento specificato non esiste.");
                request.getRequestDispatcher("errore.jsp").forward(request, response);
                return;
            }

            boolean isAdmin = utenteService.is_admin(utenteLoggato);
            boolean isOrganizzatore = utenteService.is_organizzatore(utenteLoggato, eventoId);

            if (!(isAdmin || isOrganizzatore)) {
                request.setAttribute("errore", "Non sei autorizzato a eliminare questo evento.");
                request.getRequestDispatcher("errore.jsp").forward(request, response);
                return;
            }

            boolean eliminato = eventoService.elimina_evento(eventoId);

            if (eliminato) {
                if (isOrganizzatore) {
                    response.sendRedirect("eventiCreati.jsp");
                } else if (isAdmin) {
                    response.sendRedirect("listaEventi.jsp");
                }
            } else {
                request.setAttribute("errore", "Errore durante l'eliminazione dell'evento. Riprova più tardi.");
                
                if (isOrganizzatore) {
                	request.getRequestDispatcher("dettaglioEventoCreato.jsp").forward(request, response);
                } else if (isAdmin) {
                	request.getRequestDispatcher("dettaglioEventoAdmin.jsp").forward(request, response);
                }
                
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errore", "ID evento non valido.");
            request.getRequestDispatcher("errore.jsp").forward(request, response);
        }
    }
}
