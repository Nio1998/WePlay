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
        // Imposta l'encoding UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Recupera i parametri dal form
        String email = request.getParameter("email");
        String descrizione = request.getParameter("descrizione");
        String oggetto = request.getParameter("oggetto");

        // Log dei parametri ricevuti
        System.out.println("Parametri ricevuti: email=" + email + ", descrizione=" + descrizione + ", oggetto=" + oggetto);

        if (email != null && !email.isEmpty() && descrizione != null && !descrizione.isEmpty() && oggetto != null && !oggetto.isEmpty()) {
            try {
                // Invia l'email di assistenza
                System.out.println("Tentativo di invio email...");
                inviaEmail(email, descrizione, oggetto);
                System.out.println("Email inviata con successo.");

                // Messaggio di successo
                request.setAttribute("message", "Richiesta inviata con successo! Ti risponderemo al pi� presto.");
            } catch (MessagingException e) {
                e.printStackTrace();
                // Messaggio di errore
                request.setAttribute("error", "Si � verificato un errore durante l'invio della richiesta. Controlla la configurazione SMTP.");
            }
        } else {
            // Messaggio di errore per parametri mancanti
            request.setAttribute("error", "Tutti i campi sono obbligatori!");
            System.out.println("Errore: uno o pi� campi obbligatori sono vuoti.");
        }

        // Torna alla pagina di invio richiesta
        request.getRequestDispatcher("/pages/assistenza.jsp").forward(request, response);
    }

    private void inviaEmail(String userEmail, String descrizione, String oggetto) throws MessagingException {
        // Configura le propriet� del server SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Server SMTP di Gmail
        props.put("mail.smtp.port", "587"); // Porta TLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Abilita TLS
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

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
                + "Email: " + userEmail + "\n"
                + "Descrizione del Problema:\n" + descrizione);

        // Log prima dell'invio
        System.out.println("Email pronta per l'invio: " + message.toString());

        // Invia l'email
        Transport.send(message);
        System.out.println("Email inviata con successo.");
    }
}
