package model.evento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.prenotazione.PrenotazioneService;  // Importa PrenotazioneService direttamente per il metodo

public class EventoService {

    private EventoDao eventoDAO;

    // Costruttore senza PrenotazioneService, che ora sarà creato nei metodi
    public EventoService() {
        this.eventoDAO = new EventoDao();  // Solo il DAO viene creato qui
    }

    public boolean crea_evento(String titolo, LocalDate data_inizio, LocalTime ora_inizio, String indirizzo, String citta, int massimo_di_partecipanti, String sport, String stato, double prezzo) {
        try{
            Evento evento = new Evento(data_inizio, ora_inizio, prezzo, sport, titolo, indirizzo, massimo_di_partecipanti, citta, stato);
            eventoDAO.save(evento);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
            e.printStackTrace();
        }
        
        return false; // Se l'evento non esiste
    }

    public boolean elimina_evento(int eventoId) {
        try {
            Evento evento = eventoDAO.get(eventoId);

            if (evento == null) {
                return false;
            }

            LocalDate oggi = LocalDate.now();
            LocalTime oraCorrente = LocalTime.now();

            // Calcolo della data e ora minima per la cancellazione
            LocalDate dataLimite = oggi.plusDays(1);

            // Verifica se la cancellazione è entro 24 ore dall'inizio dell'evento
            if (evento.getData_inizio().isBefore(dataLimite) || (evento.getData_inizio().isEqual(dataLimite) && evento.getOra_inizio().isBefore(oraCorrente))) {
                return false;
            }

            return eventoDAO.delete(eventoId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public Collection<Evento> filtra_eventi(String dataInizio, String dataFine, String oraInizio, String prezzoMin, String prezzoMax,
            String sport, String titolo, String indirizzo, String massimoPartecipanti,
            String citta, String stato) {
          // Conversione degli input
          LocalDate dataInizioDate = null;
          LocalDate dataFineDate = null;
          LocalTime oraInizioTime = null;
          Double prezzoMinValue = null;
          Double prezzoMaxValue = null;
          Integer massimoPartecipantiValue = null;

          // Parsing dei parametri
          if (dataInizio != null && !dataInizio.isEmpty()) dataInizioDate = LocalDate.parse(dataInizio);
          if (dataFine != null && !dataFine.isEmpty()) dataFineDate = LocalDate.parse(dataFine);
          if (oraInizio != null && !oraInizio.isEmpty()) oraInizioTime = LocalTime.parse(oraInizio);
          if (prezzoMin != null && !prezzoMin.isEmpty()) prezzoMinValue = Double.parseDouble(prezzoMin);
          if (prezzoMax != null && !prezzoMax.isEmpty()) prezzoMaxValue = Double.parseDouble(prezzoMax);
          if (massimoPartecipanti != null && !massimoPartecipanti.isEmpty()) massimoPartecipantiValue = Integer.parseInt(massimoPartecipanti);

          // Gestione dei valori vuoti
          if (sport != null && sport.isEmpty()) sport = null;
          if (titolo != null && titolo.isEmpty()) titolo = null;
          if (indirizzo != null && indirizzo.isEmpty()) indirizzo = null;
          if (citta != null && citta.isEmpty()) citta = null;
          if (stato != null && stato.isEmpty()) stato = null;

          try {
          // Chiamata al DAO con tutti i parametri
            return eventoDAO.getByFilter(dataInizioDate, dataFineDate, oraInizioTime, prezzoMinValue, prezzoMaxValue,
                    sport, titolo, indirizzo, massimoPartecipantiValue, citta, stato);
          } catch (SQLException e) {
            e.printStackTrace();
            return null;
          }
    }

    

    public Evento dettagli_evento(int id) {
        try {
            return eventoDAO.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
      
   // Restituisce tutti gli eventi
    public Collection<Evento> allEventi() throws SQLException {
        return filtra_eventi(
            "",  // dataInizio
            "",  // dataFine
            "",  // oraInizio
            "",  // prezzoMin
            "",  // prezzoMax
            "",  // sport
            "",  // titolo
            "",  // indirizzo
            "",  // massimoPartecipanti
            "",  // citta
            ""   // stato
        );
    }
    
    public boolean evento_ha_posti_disponibili(int evento_id) {
        // Creiamo l'istanza di PrenotazioneService direttamente qui
        PrenotazioneService prenotazioneService = new PrenotazioneService();

        Evento evento;
        try {
            evento = eventoDAO.get(evento_id);
            
            if (evento != null) {
                // Otteniamo il massimo numero di partecipanti per l'evento
                int maxPartecipanti = evento.getMassimo_di_partecipanti();

                // Contiamo il numero di prenotazioni attive per quell'evento
                // Si assume che calcola_partecipanti restituisca solo i partecipanti attivi
                int prenotazioniAttive = prenotazioneService.calcola_partecipanti(evento_id).size();  // Ottieni il conteggio direttamente, se possibile

                // Verifica se l'evento è pieno
                return prenotazioniAttive < maxPartecipanti;
            }
            return false;  // Evento non trovato, non pieno
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // In caso di errore, assumiamo che non ci siano posti disponibili
        }
    }

    public String statoEvento(int eventoId) {
        try {
            Evento evento = eventoDAO.get(eventoId);
            return evento.getStato();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // In caso di errore, restituiamo null
        }
    }
    
    // Funzione che richiama getEventiById e restituisce la lista degli eventi
    public List<Evento> visualizza_eventi_sottoscritti(String username) {
        // Chiamata al DAO per ottenere la lista degli eventi
        List<Evento> eventi = eventoDAO.getEventiById(username);
        
        // Restituisce la lista di eventi
        return eventi;
    }
    
    
    // Funzione che richiama getEventiById e restituisce la lista degli eventi
    public List<Evento> visualizza_eventi_creati(String username) {
    	
        // Chiamata al DAO per ottenere la lista di eventi associati all'utente
        List<Evento> eventi = eventoDAO.getEventiById(username);
        
        // Lista per contenere gli eventi filtrati con stato "organizzatore"
        List<Evento> eventiCreati = new ArrayList<>();
        
        // Filtriamo gli eventi per includere solo quelli dove lo stato è "organizzatore"
        for (Evento evento : eventi) {
            if ("organizzatore".equals(evento.getStato())) {
                eventiCreati.add(evento);
            }
        }
        
        // Restituiamo la lista di eventi creati
        return eventiCreati;
    }



}
