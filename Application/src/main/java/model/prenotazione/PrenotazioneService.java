package model.prenotazione;

import java.sql.SQLException;
import java.util.Collection;


import model.utente.UtenteBean;

public class PrenotazioneService {

    private PrenotazioneDAO prenotazioneDAO;

    public PrenotazioneService() {
        this.prenotazioneDAO = new PrenotazioneDAO();
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
    
    
    
    public boolean cancella_prenotazione(String usernameUtente, int eventoID) {
        if (usernameUtente == null || usernameUtente.isEmpty()) {
            throw new IllegalArgumentException("Il nome utente non può essere nullo o vuoto.");
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
