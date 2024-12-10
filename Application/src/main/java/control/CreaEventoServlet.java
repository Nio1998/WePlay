package control;

import java.io.IOException;
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

    public CreaEventoServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        try {
            Evento nuovoEvento = new Evento();
            nuovoEvento.setTitolo(titolo);
            nuovoEvento.setData_inizio(LocalDate.parse(dataInizio));
            nuovoEvento.setOra_inizio(LocalTime.parse(oraInizio));
            nuovoEvento.setCitta(citta);
            nuovoEvento.setIndirizzo(indirizzo);
            nuovoEvento.setMassimo_di_partecipanti(maxPartecipanti);
            nuovoEvento.setSport(sport);
            nuovoEvento.setStato(stato);
            nuovoEvento.setPrezzo(prezzo);

            EventoDao eventoDao = new EventoDao();
            eventoDao.save(nuovoEvento);

            redirectedPage = "/EventiCreati.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore nella creazione dell'evento. Riprova.");
        }

        response.sendRedirect(request.getContextPath() + redirectedPage);
    }
}
