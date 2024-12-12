package model.Valutazione;

import model.evento.*;
import model.utente.*;

import java.sql.SQLException;
import java.util.List;

public class ValutazioneService {

    private ValutazioneDAO valutazioneDAO;
    private EventoService eventoService;
    private UtenteService utenteService;


    public ValutazioneService() {
        this.valutazioneDAO = new ValutazioneDAO();
        this.eventoService = new EventoService();

    }

    // Metodo per inviare una valutazione
    public boolean inviaValutazione(String usernameValutante, String usernameValutato, int eventoId, int esito) throws IllegalArgumentException {
        try {
            // Validazione dell'esito
            if (esito < -1 || esito > 1) {
                throw new IllegalArgumentException("Valore di esito non valido");
            }

            // Verifica se l'evento � terminato
             if (eventoService.dettagli_evento(eventoId) == null || !"finito".equalsIgnoreCase(eventoService.dettagli_evento(eventoId).getStato())) {
                throw new IllegalArgumentException("Evento non valido o non terminato");
            }

            // Verifica se gli utenti esistono
            if (utenteService.findbyUsername(usernameValutante) == null || utenteService.findbyUsername(usernameValutato) == null) {
                throw new IllegalArgumentException("Utente non valido");
            }

            // Crea una nuova valutazione
            ValutazioneBean valutazione = new ValutazioneBean();
            valutazione.setUtenteValutante(usernameValutante);
            valutazione.setUtenteValutato(usernameValutato);
            valutazione.setIdEvento(eventoId);
            valutazione.setEsito(esito);

            // Inserisce la valutazione nel database
            valutazioneDAO.save(valutazione);


            return true;
        } catch (SQLException e) {
            // Gestione dell'eccezione
            e.printStackTrace();
            return false;
        }
    }
    
    public List<ValutazioneBean> calcola_valutazioni_da_utente(String usernameValutante, int eventoId) {
      
  
    	return valutazioneDAO.findByUsernameEvent(usernameValutante, eventoId);
    }
    
    
}

