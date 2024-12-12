package model.utente;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    
    public UtenteBean findbyUsername(String Username){
    	try {
	    return utenteDAO.findByUsername(Username);
    	} catch (SQLException e) {
            // Gestione dell'eccezione, ad esempio loggando l'errore
            e.printStackTrace(); 
            return null; 
        }
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
    
    // Metodo per incrementare le valutazioni positive di un utente
    public void incrementaValutazioniPositive(String username) {
        try {
            UtenteBean utente = utenteDAO.findByUsername(username);
            if (utente != null) {
                utente.setNumValutazioniPositive(utente.getNumValutazioniPositive() + 1);
                utenteDAO.update(utente);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestisci l'errore in modo appropriato
        }
    }

    // Metodo per incrementare le valutazioni neutre di un utente
    public void incrementaValutazioniNeutre(String username) {
        try {
            UtenteBean utente = utenteDAO.findByUsername(username);
            if (utente != null) {
                utente.setNumValutazioniNeutre(utente.getNumValutazioniNeutre() + 1);
                utenteDAO.update(utente);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestisci l'errore in modo appropriato
        }
    }

    // Metodo per incrementare le valutazioni negative di un utente
    public void incrementaValutazioniNegative(String username) {
        try {
            UtenteBean utente = utenteDAO.findByUsername(username);
            if (utente != null) {
                utente.setNumValutazioniNegative(utente.getNumValutazioniNegative() + 1);
                utenteDAO.update(utente);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestisci l'errore in modo appropriato
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
    
    //Restituisce tutti gli Utenti
    public List<UtenteBean> allUtenti() {
        try {
            return utenteDAO.getAllUtenti();
        } catch (SQLException e) {
        	e.printStackTrace(); // Log per debugging
            return new ArrayList<>();
        } catch (Exception e) {
        	e.printStackTrace(); // Log per debugging
            return new ArrayList<>();
        }
    }

    

}
