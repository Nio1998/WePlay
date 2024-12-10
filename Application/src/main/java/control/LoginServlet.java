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
import model.utente.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String redirectedPage = "/login.jsp";
        Boolean control = false;
        try {
            Connection con = ConDB.getConnection();
            String sql = "SELECT * FROM utente";
            
            PreparedStatement s = con.prepareStatement(sql);
            ResultSet rs = s.executeQuery();
            
            while (rs.next()) {
                if (username.compareTo(rs.getString(1)) == 0) {
                    String pw = checkPsw(password);
                    if (pw.compareTo(rs.getString("pw")) == 0) {
                        control = true;
                        UtenteBean registeredUser = new UtenteBean(rs.getString("username"), rs.getString("cognome"),rs.getString("nome"),rs.getDate("dataDiNascita"),rs.getString("email"),rs.getString("pw"),rs.getInt("numTimeout"),rs.getBoolean("isTimeout"),rs.getBoolean("isAdmin"),rs.getDate("dataOraFineTimeout"),rs.getInt("numValutazioniNeutre"),rs.getInt("numValutazioniNegative"),rs.getInt("numValutazioniPositive"));
                        request.getSession().setAttribute("registeredUser", registeredUser);
                        request.getSession().setAttribute("role", registeredUser.isAdmin());
                        request.getSession().setAttribute("username", rs.getString("username"));                   		
                        
                        redirectedPage = "/index.jsp";
                        ConDB.releaseConnection(con);
                    }
                }
            }
        }
        catch (Exception e) {
            redirectedPage = "/login.jsp";
        }
        if (!control) {
            request.getSession().setAttribute("login-error", true);
        } else {
            request.getSession().setAttribute("login-error", false);
        }
        response.sendRedirect(request.getContextPath() + redirectedPage);
    }
        
    private String checkPsw(String psw) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(psw.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        
        // Padding leading zeros to make it 32 characters long (256 bits)
        while (hashtext.length() < 64) {
            hashtext = "0" + hashtext;
        }
        
        return hashtext;
    }
}
