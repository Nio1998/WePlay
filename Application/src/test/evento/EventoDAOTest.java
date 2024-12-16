package evento;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.evento.Evento;
import model.evento.EventoDao;
import model.utente.UtenteBean;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
class EventoDAOTest {

    private static EventoDao eventoDao;

    @BeforeAll
    static void setupDatabase() {
        eventoDao = new EventoDao();
    }

    @Test
    @Order(1)
    void testSaveEvento() throws SQLException {
        Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, "Calcio", "Partita amichevole", "Via Roma 10", 22, "Roma", "non iniziato");
        eventoDao.save(evento);

        Evento retrieved = eventoDao.get(21); 
        assertNotNull(retrieved);
        assertEquals(LocalDate.of(2024, 1, 10), retrieved.getData_inizio());
        assertEquals(LocalTime.of(10, 0), retrieved.getOra_inizio());
        assertEquals(20.50, retrieved.getPrezzo());
        assertEquals("Calcio", retrieved.getSport());
        assertEquals("Partita amichevole", retrieved.getTitolo());
        assertEquals("Calcio", retrieved.getSport());
        assertEquals("Via Roma 10", retrieved.getIndirizzo());
        assertEquals(22, retrieved.getMassimo_di_partecipanti());
        assertEquals("Roma", retrieved.getCitta());
        assertEquals("non iniziato", retrieved.getStato());
        eventoDao.delete(21);
    }

    @Test
    @Order(2)
    void testUpdateEvento() throws SQLException {
        Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, "Calcio", "Partita amichevole", "Via Roma 10", 22, "Roma", "non iniziato");
        eventoDao.save(evento);

        evento.setID(21);
        evento.setPrezzo(25.00);
        evento.setTitolo("Partita di calcetto");
        eventoDao.update(evento);

        Evento updated = eventoDao.get(21);
        assertNotNull(updated);
        assertEquals(LocalDate.of(2024, 1, 10), updated.getData_inizio());
        assertEquals(LocalTime.of(10, 0), updated.getOra_inizio());
        assertEquals(25.00, updated.getPrezzo());
        assertEquals("Partita di calcetto", updated.getTitolo());
        assertEquals("Calcio", updated.getSport());

        assertEquals("Via Roma 10", updated.getIndirizzo());
        assertEquals(22, updated.getMassimo_di_partecipanti());
        assertEquals("Roma", updated.getCitta());
        assertEquals("non iniziato", updated.getStato());
        eventoDao.delete(21);
    }

    @Test
    @Order(3)
    void testDeleteEvento() throws SQLException {
        Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, "Calcio", "Partita amichevole", "Via Roma 10", 22, "Roma", "non iniziato");
        eventoDao.save(evento);

        assertTrue(eventoDao.delete(21));
        assertNull(eventoDao.get(21));
    }

  

    @Test
    @Order(5)
    void testGetByFilter() throws SQLException {

        // Esegui la query con i nuovi parametri di filtro
        Collection<Evento> filteredEvents = eventoDao.getByFilter(
            LocalDate.of(2024, 11, 1),   // dataInizio
            LocalDate.of(2024, 11, 30),  // dataFine
            LocalTime.of(10, 0),         // oraInizio
            4.0,                         // prezzoMin
            6.0,                         // prezzoMax
            "Calcio",                   // sport
            "Partita",                  // titolo
            "Campetto",                 // indirizzo
            10,                          // massimoPartecipanti
            "Napoli",                   // citta
            "finito"                    // stato
        );

        // Verifica che ci sia esattamente un evento corrispondente ai criteri
        assertEquals(1, filteredEvents.size());

        // Verifica i dettagli dell'evento filtrato
        Evento filtered = filteredEvents.iterator().next();
        assertEquals("Partita tra amici", filtered.getTitolo());
        assertEquals(LocalDate.of(2024, 11, 30), filtered.getData_inizio());
        assertEquals(LocalTime.of(10, 0), filtered.getOra_inizio());
        assertEquals(5.00, filtered.getPrezzo());
        assertEquals("Calcio", filtered.getSport());
        assertEquals("Campetto San Paolo", filtered.getIndirizzo());
        assertEquals(10, filtered.getMassimo_di_partecipanti());
        assertEquals("Napoli", filtered.getCitta());
        assertEquals("finito", filtered.getStato());
    }

    @Test
    @Order(6)
    void testGetEventiBySport() throws SQLException {
        // Arrange: prepara i dati necessari per il test
        String sport = "Beach Volley";

        // Agisci: chiama il metodo da testare
        List<Evento> eventi = eventoDao.getEventiBySport(sport);

        // Asserisci: verifica i risultati
        assertEquals(2, eventi.size(), "Dovrebbe esserci un solo evento per lo sport specificato.");

        Evento evento = eventi.get(0);
        assertEquals("Partita sulla spiaggia", evento.getTitolo(), "Il titolo dell'evento non corrisponde.");
        assertEquals(LocalDate.of(2024, 12, 11), evento.getData_inizio(), "La data di inizio dell'evento non corrisponde.");
        assertEquals(LocalTime.of(14, 0), evento.getOra_inizio(), "L'ora di inizio dell'evento non corrisponde.");
        assertEquals(15.00, evento.getPrezzo(), "Il prezzo dell'evento non corrisponde.");
        assertEquals("Beach Volley", evento.getSport(), "Lo sport dell'evento non corrisponde.");
        assertEquals("Spiaggia Centrale", evento.getIndirizzo(), "L'indirizzo dell'evento non corrisponde.");
        assertEquals(10, evento.getMassimo_di_partecipanti(), "Il numero massimo di partecipanti non corrisponde.");
        assertEquals("Rimini", evento.getCitta(), "La città dell'evento non corrisponde.");
        assertEquals("non iniziato", evento.getStato(), "Lo stato dell'evento non corrisponde.");
    }

    @Test
    @Order(7)
    void testSaveEventoFailure() throws SQLException {
        Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, "Calc", "Titolo Errato", "Via Roma 10", 22, "Roma", "non iniziato");
       
        assertThrows(SQLException.class, () -> {
            eventoDao.save(evento);
        });
    } 
    
    
    @Test
    @Order(8)
    void testSaveEventoAttributiMancanti() {
    	 Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, null, null, "Via Roma 10", 22, "Roma", "non iniziato");

        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            eventoDao.save(evento);
        });
    }

    

    @Test
    @Order(9)
    void testUpdateEventoFailure() throws SQLException {
        Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, "Calcio", "Partita amichevole", "Via Roma 10", 22, "Roma", "non iniziato");
        eventoDao.save(evento);

        evento.setID(21);
        evento.setSport("Calc"); // Aggiornamento errato
        
        
        assertThrows(SQLException.class, () -> {
        	 eventoDao.update(evento);
        });
        eventoDao.delete(21); // Pulisce il database
    }
    @Test
    @Order(11)
    void testUpdateEventoFailureNonPresente() throws SQLException {
        // Creazione di un evento che non viene mai salvato
        Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, "Calcio", "Partita amichevole", "Via Roma 10", 100, "Roma", "non iniziato");
        
        // Tentativo di aggiornare un evento che non esiste nel database
        evento.setID(100); // ID che non esiste nel database
        
        assertThrows(SQLException.class, () -> {
            eventoDao.update(evento); // Dovrebbe fallire perch� l'evento non esiste
        });
    }


    
    @Test
    @Order(10)
    void updateEventoFailureAttributiMancanti() throws SQLException {
        // Creazione di un evento con parametri incompleti
        Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, "Calcio", "Partita amichevole", "Via Roma 10", 22, "Roma", "non iniziato");
        evento.setID(21);
        eventoDao.save(evento);
        
        // Modifica dell'evento (mancano alcuni parametri necessari)
        evento.setTitolo(null); // Attributo mancante
        
        // Verifica che venga lanciata una SQLException a causa del parametro mancante
        assertThrows(SQLException.class, () -> {
            eventoDao.update(evento); // Dovrebbe fallire a causa del parametro mancante
        });
        
        eventoDao.delete(21); // Pulisce il database
    }

    
    
    @Test
    @Order(10)
    void testDeleteEventoFailure() {
        int invalidID = 100; // ID inesistente

        // Verifica che venga lanciata un'eccezione quando si tenta di eliminare un evento con un ID non valido
        assertThrows(SQLException.class, () -> {
            eventoDao.delete(invalidID);
        }, "Il metodo dovrebbe lanciare una SQLException per un ID inesistente.");
    }

    
    @Test
    @Order(11)
    void testGetByFilterFailure() {
        assertThrows(SQLException.class, () -> {
            // Prova ad eseguire la query con criteri che generano un'eccezione
            eventoDao.getByFilter(
                LocalDate.of(2024, 1, 1),    // dataInizio
                LocalDate.of(2024, 12, 31), // dataFine
                null,                        // oraInizio
                null,                        // prezzoMin
                null,                        // prezzoMax
                "Sport Sbagliato",           // sport non valido
                null,                        // titolo
                null,                        // indirizzo
                null,                        // massimoPartecipanti
                "Taranto",           // citta
                null                         // stato
            );
        }, "Il metodo dovrebbe lanciare una SQLException per sport non valido.");
    }


    @Test
    @Order(12)
    void testGetEventiBySportFailure() {
        // Sport inesistente
        String sport = "Sport Inesistente";

        assertThrows(SQLException.class, () -> {
            // Esegui il metodo con lo sport inesistente
            eventoDao.getEventiBySport(sport);
        }, "Il metodo dovrebbe lanciare una SQLException per uno sport inesistente.");
    }
    
    
}
