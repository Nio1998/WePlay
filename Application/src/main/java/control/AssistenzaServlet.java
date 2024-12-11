package control;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/GestioneAssistenza")
public class AssistenzaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Recupera i parametri dal form
        String email = request.getParameter("email");
        String descrizione = request.getParameter("descrizione");
        String oggetto = request.getParameter("oggetto");

        if (email != null && !email.isEmpty() && descrizione != null && !descrizione.isEmpty()) {
            try {
                // Invia l'email di assistenza
                inviaEmail(email, descrizione, oggetto);

                // Mostra un messaggio di successo
                request.setAttribute("message", "Richiesta inviata con successo! Ti risponderemo al più presto.");
            } catch (Exception e) {
                e.printStackTrace();
                // Gestione errori
                request.setAttribute("error", "Si è verificato un errore durante l'invio della richiesta.");
            }
        } else {
            // Messaggio di errore per parametri mancanti
            request.setAttribute("error", "Email e descrizione sono obbligatorie!");
        }

        // Torna alla pagina di invio richiesta
        request.getRequestDispatcher("/pages/Assistenza.jsp").forward(request, response);
    }

    private void inviaEmail(String userEmail, String descrizione, String oggetto) throws MessagingException {
        // Configura le proprietà del server SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Server SMTP di Gmail
        props.put("mail.smtp.port", "587"); // Porta TLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Abilita TLS

        // Crea una sessione con autenticazione
        javax.mail.Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("weplay.assistenza@gmail.com", "ihuq taep thmh hjzo"); 
            }
        });

        // Componi l'email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("weplay.assistenza@gmail.com")); // Mittente
        message.setRecipients(
            Message.RecipientType.TO, 
            InternetAddress.parse("weplay.assistenza@gmail.com") // Destinatario
        );
        message.setSubject(oggetto);
        message.setText("Hai ricevuto una nuova richiesta di assistenza.\n\n"
                + "Email Utente: " + userEmail + "\n"
                + "Descrizione del Problema:\n" + descrizione);

        // Invia l'email
        Transport.send(message);
    }
}
