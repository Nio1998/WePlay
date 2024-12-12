package model.utente;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
    
    // Metodo per controllare se un'username esiste già nel database
    public boolean controlla_username_esistente(String username) {
        try {
            // Usa il DAO per verificare se l'username è gi�  presente nel database
            return utenteDAO.findByUsername(username) != null;
        } catch (SQLException e) {
            // Gestione dell'eccezione, ad esempio loggando l'errore
            e.printStackTrace(); // Puoi anche usare un logger al posto di printStackTrace
            return false; // Considera l'username come non esistente se c'è un errore (puoi gestire diversamente)
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

    
    
 // Metodo per registrare un nuovo utente con i seguenti dati (username, nome, cognome, email, pw, data_nascita)
    public boolean registra_utente(String username, String nome, String cognome, String email, String pw, String data_nascita) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate data_di_nascita = LocalDate.parse(data_nascita, formatter);

            UtenteBean utente = new UtenteBean();
            utente.setUsername(username);
            utente.setEmail(email);
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setPw(pw); // hash gi� effettuato nel controller
            utente.setDataDiNascita(data_di_nascita); // Passa l'oggetto LocalDate
			utenteDAO.save(utente); // Assumi che sollevi un'eccezione se fallisce
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Log per debugging
            return false;
        } catch (Exception e) {
            e.printStackTrace(); // Log di errori generici
            return false;
        }
    }    
    
    
    public void aggiornaTimeoutUtente(String username, boolean isTimeout, LocalDateTime dataOraFineTimeout) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username non pu� essere nullo o vuoto.");
        }
        if (dataOraFineTimeout == null) {
            throw new IllegalArgumentException("La data e ora di fine timeout non possono essere nulli.");
        }

        try {
            // Chiamata al DAO per aggiornare i campi nel database
            utenteDAO.aggiornaTimeout(username, isTimeout, dataOraFineTimeout);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'aggiornamento del timeout dell'utente: " + username, e);
        }
    }
    
    public void assegnaTimeout(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username non pu� essere nullo o vuoto.");
        }

        try {
            // Recupera i dati dell'utente dal database
            UtenteBean utente = utenteDAO.findByUsername(username);

            if (utente == null) {
                throw new IllegalArgumentException("Utente non trovato.");
            }

            // Ottieni il numero di timeout gi� ricevuti
            int numeroTimeout = utente.getNumTimeout();

            // Determina la durata del timeout (in ore) in base al numero di timeout ricevuti
            int durataTimeout;
            if (numeroTimeout == 0) {
                durataTimeout = 24; // Primo timeout: 24 ore
            } else if (numeroTimeout == 1) {
                durataTimeout = 48; // Secondo timeout: 48 ore
            } else if (numeroTimeout == 2) {
                durataTimeout = 72; // Terzo timeout: 72 ore
            } else if (numeroTimeout >=3 && numeroTimeout <=7){
                durataTimeout = 168; // Timeout successivi: 7 giorni (168 ore)
            }else {
            	durataTimeout = 876600; //Timeout 100 anni
            }

            // Calcola la data e ora di fine timeout
            LocalDateTime dataOraFineTimeout = LocalDateTime.now().plusHours(durataTimeout);

            // Aggiorna i dati dell'utente nel database
            utenteDAO.aggiornaTimeout(username, true, dataOraFineTimeout);

            // Aggiorna il numero di timeout incrementandolo
            utente.incrementaNumeroTimeout();

        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'assegnazione del timeout all'utente: " + username, e);
        }
    }

}
