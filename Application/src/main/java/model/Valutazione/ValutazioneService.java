package model.Valutazione;

import model.evento.*;
import model.utente.*;

import java.sql.SQLException;
import java.util.List;

public class ValutazioneService {

    private ValutazioneDAO valutazioneDAO;

    public ValutazioneService() {
        this.valutazioneDAO = new ValutazioneDAO();
    }

    // Metodo per inviare una valutazione
    public boolean inviaValutazione(String usernameValutante, String usernameValutato, int eventoId, int esito) throws IllegalArgumentException {
        try {
            // Inizializzo i servizi di supporto all'interno della funzione
            EventoService eventoService = new EventoService();
            UtenteService utenteService = new UtenteService();

            // Validazione dell'esito
            if (esito < -1 || esito > 1) {
                throw new IllegalArgumentException("Valore di esito non valido");
            }

            // Verifica se l'evento è terminato
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
            e.printStackTrace();
            return false;
        }
    }
    
    // Metodo per calcolare le valutazioni ricevute da un utente per un evento specifico
    public List<ValutazioneBean> calcola_valutazioni_da_utente(String usernameValutante, int eventoId) {
        return valutazioneDAO.findByUsernameEvent(usernameValutante, eventoId);
    }
}
