package model.evento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventoService {

    private EventoDao eventoDAO;

    
    public EventoService() {
        this.eventoDAO = new EventoDao();  // Inizializza il DAO
    }

    
    public boolean esiste_evento(int eventoId) {
    	
        try {
            Evento evento = eventoDAO.get(eventoId);  // Recupera l'evento usando il DAO
            return evento != null;  // Se l'evento è null, non esiste, altrimenti esiste
        
        } catch (SQLException e) {
            e.printStackTrace();  // Stampa l'errore per il debug
            return false;  // Se c'è un errore nel database, considera che l'evento non esiste
        }
    }
    
    
    public boolean modifica_evento(int eventoId, String titolo, LocalDate data, LocalTime ora, String indirizzo, String citta, int maxPartecipanti) {
        // Recupero l'evento tramite l'ID
        Evento evento;
        
		try {
			evento = eventoDAO.get(eventoId);
			
			 if (evento != null) {
		            // Setto i nuovi valori nei campi dell'evento
		            evento.setTitolo(titolo);
		            evento.setData_inizio(data); 
		            evento.setOra_inizio(ora);   
		            evento.setIndirizzo(indirizzo);
		            evento.setCitta(citta);
		            evento.setMassimo_di_partecipanti(maxPartecipanti);

		            try {
		                // Chiamata al DAO per l'update
		                eventoDAO.update(evento);
		                return true; // Se l'update ha successo
		            } catch (SQLException e) {
		                e.printStackTrace(); // Gestisci l'errore, eventualmente con logging
		                return false; // Se c'è un errore nella query
		            }
		        }
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
        return false; // Se l'evento non esiste
    }
    
    public boolean elimina_evento(int eventoId) {
    	
        try {
            return eventoDAO.delete(eventoId);
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
            
        }
    }
    
    
}


