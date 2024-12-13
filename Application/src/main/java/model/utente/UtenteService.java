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
    

    // Metodo per controllare se un'email esiste giÃ  nel database
    public boolean controlla_email_esistente(String email) {
        try {
            // Usa il DAO per verificare se l'email Ã¨ giÃ  presente nel database
            return utenteDAO.findByEmail(email) != null;
        } catch (SQLException e) {
            // Gestione dell'eccezione, ad esempio loggando l'errore
            e.printStackTrace(); // Puoi anche usare un logger al posto di printStackTrace
            return false; // Considera l'email come non esistente se c'Ã¨ un errore (puoi gestire diversamente)
        }
    }
    
    // Metodo per controllare se un'username esiste giÃ  nel database
    public boolean controlla_username_esistente(String username) {
        try {
            // Usa il DAO per verificare se l'username Ã¨ giï¿½  presente nel database
            return utenteDAO.findByUsername(username) != null;
        } catch (SQLException e) {
            // Gestione dell'eccezione, ad esempio loggando l'errore
            e.printStackTrace(); // Puoi anche usare un logger al posto di printStackTrace
            return false; // Considera l'username come non esistente se c'Ã¨ un errore (puoi gestire diversamente)
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
    
    public boolean is_TimeOut(String username) {
        UtenteBean utente;
        try {
          utente = utenteDAO.findByUsername(username);
          return utente != null && utente.isTimeout();
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
            utente.setPw(pw); // hash giï¿½ effettuato nel controller
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

    
    
    public void aggiornaTimeoutUtente(String username, boolean isTimeout, LocalDateTime dataOraFineTimeout) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username non può essere nullo o vuoto.");
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
            throw new IllegalArgumentException("Username non può essere nullo o vuoto.");
        }

        try {
            // Recupera i dati dell'utente dal database
            UtenteBean utente = utenteDAO.findByUsername(username);

            if (utente == null) {
                throw new IllegalArgumentException("Utente non trovato.");
            }

            // Ottieni il numero di timeout già ricevuti
            int numeroTimeout = utente.getNumTimeout();

            // Determina la durata del timeout (in ore) in base al numero di timeout ricevuti
            int durataTimeout = 0;
            if (numeroTimeout == 0) {
                durataTimeout = 24; // Primo timeout: 24 ore
            } else if (numeroTimeout == 1) {
                durataTimeout = 48; // Secondo timeout: 48 ore
            } else if (numeroTimeout == 2) {
                durataTimeout = 72; // Terzo timeout: 72 ore
            } else if (numeroTimeout >=3 && numeroTimeout <=7){
                durataTimeout = 168; // Timeout successivi: 7 giorni (168 ore)
            }else {
            	assegnaBan(username);
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
    
    public void assegnaBan(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username non può essere nullo o vuoto.");
        }

        try {
            // Recupera i dati dell'utente dal database
            UtenteBean utente = utenteDAO.findByUsername(username);

            if (utente == null) {
                throw new IllegalArgumentException("Utente non trovato.");
            }

            // Imposta lo stato di timeout come equivalente al ban
            utente.setTimeout(true);

            // Imposta dataOraFineTimeout a null per indicare un ban permanente
            utente.setDataOraFineTimeout(null);

            // Aggiorna i dati dell'utente nel database
            utenteDAO.aggiornaTimeout(username, utente.isTimeout(), utente.getDataOraFineTimeout());

            // Facoltativo: Log dell'azione di ban
            System.out.println("Utente " + username + " bannato con successo (ban permanente).");

        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'assegnazione del ban all'utente: " + username, e);
        }
    }


}
