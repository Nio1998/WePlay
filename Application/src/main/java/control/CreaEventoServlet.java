package control;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConDB;
//import model.OrderModel;
import model.evento.*;

@WebServlet("/CreaEventoServlet")
public class CreaEventoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreaEventoServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titolo = request.getParameter("titolo");
        String data = request.getParameter("data");
        String ora = request.getParameter("ora");
        String citta = request.getParameter("citta");
        String indirizzo = request.getParameter("indirizzo");
        int maxPartecipanti = Integer.parseInt(request.getParameter("maxPartecipanti"));
        String sport = request.getParameter("sport");
        String stato = request.getParameter("stato");
        float prezzo = Float.parseFloat(request.getParameter("prezzo"));
        String redirectedPage = "/creaEvento.jsp";

        try {
            Connection con = ConDB.getConnection();
            String sql = "INSERT INTO evento (titolo, data, ora, citta, indirizzo, maxPartecipanti, sport, stato, prezzo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement s = con.prepareStatement(sql);
            s.setString(1, titolo);
            s.setDate(2, java.sql.Date.valueOf(data));
            s.setTime(3, java.sql.Time.valueOf(ora + ":00"));
            s.setString(4, citta);
            s.setString(5, indirizzo);
            s.setInt(6, maxPartecipanti);
            s.setString(7, sport);
            s.setString(8, stato);
            s.setFloat(9, prezzo);

            int result = s.executeUpdate();
            if (result > 0) {
                redirectedPage = "/esploraEventi.jsp";
            }
            ConDB.releaseConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + redirectedPage);
    }