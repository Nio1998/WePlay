package model.utente;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UtenteService {

    private UtenteDAO utenteDAO;

    public UtenteService() {
        utenteDAO = new UtenteDAO(); // Inizializza il DAO
    }
    
    private String hash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);

        while (hashtext.length() < 64) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
    

    // Metodo per controllare se un'email esiste già nel database
    public boolean controlla_email_esistente(String email) {
        try {
            // Usa il DAO per verificare se l'email è già presente nel database
            return utenteDAO.findByEmail(email) != null;
        } catch (SQLException e) {
            // Gestione dell'eccezione, ad esempio loggando l'errore
            e.printStackTrace(); // Puoi anche usare un logger al posto di printStackTrace
            return false; // Considera l'email come non esistente se c'è un errore (puoi gestire diversamente)
        }
    }
    

    
    // Metodo per aggiornare i dati dell'utente (email e password)
    public void modifica_dati(String username, String email, String password) {
        try {
            // Recupera l'utente dal database
            UtenteBean utente = utenteDAO.findByUsername(username);
            // Modifica i dati dell'utente
            utente.setEmail(email);
            utente.setPw(hash(password));
            // Aggiorna i dati nel database tramite il DAO
            utenteDAO.update(utente);
        } catch (SQLException e) {
            // Gestione dell'eccezione
            e.printStackTrace();
        }
    }
    
    public boolean is_admin(String username) {
        UtenteBean utente;
		try {
			utente = utenteDAO.findByUsername(username);
			return utente != null && utente.isAdmin();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
        
    }
    
    public boolean is_organizzatore(String username, int eventoId) {
        	
    		try {
				return  utenteDAO.is_organizzatore(username, eventoId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		return false;
    }
}
