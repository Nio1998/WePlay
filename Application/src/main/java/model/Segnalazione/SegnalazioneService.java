package model.Segnalazione;

import java.sql.SQLException;
import java.util.List;

import model.Segnalazione.*;

public class SegnalazioneService {
	
	 private SegnalazioneDAO segnalazioneDao;
	 
	 public SegnalazioneService() {
	        segnalazioneDao = new SegnalazioneDAO(); // Inizializza il DAO
	    }
	 
    // Metodo per inviare una segnalazione
    public void inviaSegnalazione(String usernameSegnalante, String usernameSegnalato, int eventoId, String motivo, String stato) throws SQLException {
        // Verifica preliminare dei parametri
        if (usernameSegnalante == null || usernameSegnalante.isEmpty()) {
            throw new IllegalArgumentException("Il nome utente del segnalante non può essere nullo o vuoto.");
        }

        if (usernameSegnalato == null || usernameSegnalato.isEmpty()) {
            throw new IllegalArgumentException("Il nome utente segnalato non può essere nullo o vuoto.");
        }

        if (motivo == null || motivo.isEmpty()) {
            throw new IllegalArgumentException("Il motivo della segnalazione non può essere nullo o vuoto.");
        }

        // Creazione della segnalazione
        Segnalazione segnalazione = new Segnalazione(usernameSegnalante, usernameSegnalato, stato, motivo, eventoId);

        // Simula il salvataggio della segnalazione (ad esempio, in un database o log)
        segnalazioneDao.save(segnalazione);

        System.out.println("Segnalazione inviata correttamente: " + segnalazione);
    
 }
    public boolean segnalazioneGiaEffettuata(String usernameSegnalante, String usernameSegnalato, int eventoId ) throws SQLException {
			if(segnalazioneDao.get(usernameSegnalante, usernameSegnalato, eventoId)) {
    	return true;
			}
			return false;
    	
    }
    
    public List<Segnalazione> listaSegnalazioni(String username) throws SQLException {
        // Chiama il metodo DAO per ottenere le segnalazioni ricevute dall'utente
        return segnalazioneDao.getSegnalazioniRicevute(username);
    }
    
    
}

