package control;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.utente.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UtenteDAO utenteDAO;

    public LoginServlet() {
        super();
        utenteDAO = new UtenteDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String redirectedPage = "/WePlay/pages/login.jsp"; // Pagina in caso di errore
        boolean control = false;

        try {
            List<UtenteBean> lista = utenteDAO.getAllUtenti();

            for (UtenteBean u : lista) {
                if (username.equals(u.getUsername())) {
                    String pw = checkPsw(password);
                    if (pw.equals(u.getPw())) {
                        control = true;

                        // Salva informazioni nella sessione
                        request.getSession().setAttribute("utente", u);
                        request.getSession().setAttribute("isLoggedIn", true);
                        request.getSession().setAttribute("username", u.getUsername());

                        redirectedPage = "/WePlay/pages/registrationSuccess.jsp"; // Modifica per destinazione principale
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!control) {
            request.getSession().setAttribute("login-error", true);
        } else {
            request.getSession().removeAttribute("login-error");
        }

        response.sendRedirect(redirectedPage);
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

        while (hashtext.length() < 64) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}
