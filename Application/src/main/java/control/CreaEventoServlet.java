package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.evento.*;

@WebServlet("/CreaEventoServlet")
public class CreaEventoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EventoService eventoService;

    @Override
    public void init() throws ServletException{
        super.init();
        eventoService = new EventoService();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        try {

        String titolo = request.getParameter("titolo");
        String dataInizio = request.getParameter("data_inizio");
        String oraInizio = request.getParameter("ora_inizio");
        String citta = request.getParameter("citta");
        String indirizzo = request.getParameter("indirizzo");
        int maxPartecipanti = Integer.parseInt(request.getParameter("massimo_di_partecipanti"));
        String sport = request.getParameter("sport");
        String stato = request.getParameter("stato");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));

        String redirectedPage = "/creaEvento.jsp";
        boolean eventoSuccesso = eventoService.crea_evento(titolo, data_inizio, ora_inizio, citta, indirizzo, massimo_di_partecipanti, sport, stato, prezzo);
        if (eventoSuccesso){
            request.setAttribute("successo", "evento creato con successo.");
            request.getRequestDispatcher("EventiCreati.jsp").forward(request, response);
        } else{
            request.setAttribute("errore");
            request.getRequestDispatcher("errore.jsp").forward(request, response);
        }
    }catch (SQLException e) {
        e.printStackTrace();
        response.sendRedirect("errore.jsp");
    }
       
   }
}
