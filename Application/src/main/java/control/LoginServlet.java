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

//import model.OrderModel;
import model.utente.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UtenteDAO utenteDAO;
    public LoginServlet() {
        super();
        utenteDAO= new UtenteDAO();
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
        	List<UtenteBean> lista= utenteDAO.getAllUtenti();
            for(UtenteBean u: lista) {
            	
                if (username.compareTo(u.getUsername()) == 0) {
                    String pw = checkPsw(password);
                    if (pw.compareTo(u.getPw()) == 0) {
                        control = true;
                        UtenteBean registeredUser = new UtenteBean(u.getUsername(), u.getCognome(),u.getNome(),u.getDataDiNascita(),u.getEmail(),u.getPw(),u.getNumTimeout(),u.isTimeout(),u.isAdmin(),u.getDataOraFineTimeout(),u.getNumValutazioniNeutre(),u.getNumValutazioniNegative(),u.getNumValutazioniPositive());
                        request.getSession().setAttribute("registeredUser", registeredUser);
                        request.getSession().setAttribute("role", registeredUser.isAdmin());
                        request.getSession().setAttribute("username", u.getUsername());                   		
                        
                        redirectedPage = "/index.jsp";
                   
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
