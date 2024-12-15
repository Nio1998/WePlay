package control;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConDB;
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
        } else if("checkUsername".equals(action)){
        	checkUsername(request, response);
        }
        else {
            registerUser(request, response);
        }
    }

    private void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	 String email = request.getParameter("email");
         boolean emailExists = false;
    
    
            utenteService.controlla_email_esistente(email);
             if(utenteService!=null) emailExists = true;
        
        response.setContentType("application/json");
        response.getWriter().write("{\"emailExists\": " + emailExists + "}");
    }
    
    
    private void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        boolean usernameExists = false;

       
        	utenteService.controlla_username_esistente(username);
            if(utenteService!=null) usernameExists = true;
       

        response.setContentType("application/json");
        response.getWriter().write("{\"usernameExists\": " + usernameExists + "}");
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String pw = utenteService.hash(request.getParameter("password"));
        String data_nascita = request.getParameter("data_nascita");
        String redirectedPage = "/pages/register.jsp";
        boolean registrationSuccess = false; 
        registrationSuccess=utenteService.registra_utente(username, nome, cognome, email, pw, data_nascita);
        if (registrationSuccess) {
            request.getSession().setAttribute("message", "Registrazione effettuata con successo!");
        } else {
            request.getSession().setAttribute("message", "Registrazione fallita. Riprova.");
            request.getSession().setAttribute("register-error", true);
            redirectedPage = "/pages/register.jsp";
        }
        
        response.sendRedirect(request.getContextPath() + redirectedPage);
    }
}
