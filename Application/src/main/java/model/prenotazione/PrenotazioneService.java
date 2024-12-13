package model.prenotazione;

import java.sql.SQLException;
import java.util.Collection;


import model.utente.UtenteBean;
import model.evento.EventoService;
import model.prenotazione.PrenotazioneBean;

public class PrenotazioneService {

    private PrenotazioneDAO prenotazioneDAO;
    private EventoService eventoService;

    public PrenotazioneService() {
        this.prenotazioneDAO = new PrenotazioneDAO();
        this.eventoService = new EventoService();
        
    }


    
    public Collection<UtenteBean> calcola_partecipanti(int evento_id){
    	try {
			return prenotazioneDAO.getAllWhereEvento(evento_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    
    

    public boolean prenota_evento(String utenteUsername, int eventoID) {
        try {
            // Creazione di una nuova prenotazione con stato iniziale e posizione in coda
            String statoPrenotazione;

            // Verifica se l'evento ha posti disponibili
            if (eventoService.evento_ha_posti_disponibili(eventoID)) {
                statoPrenotazione = "active";  // Stato attivo se ci sono posti disponibili
            } else {
                statoPrenotazione = "waiting";  // Stato "in coda" se l'evento è pieno
            }

            // Creazione della prenotazione
            PrenotazioneBean prenotazione;
            if (statoPrenotazione.equals("waiting")) {
                // Se l'evento è pieno, calcoliamo la posizione in coda
                int posizioneInCoda = prenotazioneDAO.newPosInCoda(eventoID);
                prenotazione = new PrenotazioneBean(utenteUsername, eventoID, statoPrenotazione, posizioneInCoda);
            } else {
                // Se l'evento ha posti disponibili, la posizione in coda è 0
                prenotazione = new PrenotazioneBean(utenteUsername, eventoID, statoPrenotazione, 0);
            }

            // Salvataggio della prenotazione nel database
            prenotazioneDAO.save(prenotazione);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    


 

    public boolean cancella_prenotazione(String usernameUtente, int eventoID) {
        if (usernameUtente == null || usernameUtente.isEmpty()) {
            throw new IllegalArgumentException("Il nome utente non pu� essere nullo o vuoto.");
        }

        if (eventoID <= 0) {	
            throw new IllegalArgumentException("L'ID dell'evento deve essere un valore positivo.");
        }
        try {
			return prenotazioneDAO.delete(usernameUtente, eventoID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
}
