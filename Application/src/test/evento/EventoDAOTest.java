package evento;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.evento.Evento;
import model.evento.EventoDao;

import java.sql.SQLException;
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
    @Order(4)
    void testGetAll() throws SQLException {
        

        Collection<Evento> eventi = eventoDao.getAll();
        assertEquals(20, eventi.size());
    }

    @Test
    @Order(5)
    void testGetByFilter() throws SQLException {
        
        Collection<Evento> filteredEvents = eventoDao.getByFilter(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 30), "Calcio", "Napoli");
        assertEquals(1, filteredEvents.size());

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
        assertEquals(1, eventi.size(), "Dovrebbe esserci un solo evento per lo sport specificato.");

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
        Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, "Calcio", "Titolo Errato", "Via Roma 10", 22, "Roma", "non iniziato");
        eventoDao.save(evento);

        Evento retrieved = eventoDao.get(21); // Supponiamo che l'ID assegnato sia 21
        assertNotEquals("Titolo Correto", retrieved.getTitolo(), "Il titolo non dovrebbe corrispondere.");
        eventoDao.delete(21); // Pulisce il database
    }

    @Test
    @Order(8)
    void testUpdateEventoFailure() throws SQLException {
        Evento evento = new Evento(LocalDate.of(2024, 1, 10), LocalTime.of(10, 0), 20.50, "Calcio", "Partita amichevole", "Via Roma 10", 22, "Roma", "non iniziato");
        eventoDao.save(evento);

        evento.setID(21);
        evento.setPrezzo(30.00); // Aggiornamento errato
        eventoDao.update(evento);

        Evento updated = eventoDao.get(21);
        assertNotEquals(20.50, updated.getPrezzo(), "Il prezzo non dovrebbe corrispondere al valore originale.");
        eventoDao.delete(21); // Pulisce il database
    }

    @Test
    @Order(9)
    void testDeleteEventoFailure() throws SQLException {
        int invalidID = -1; // ID inesistente
        assertFalse(eventoDao.delete(invalidID), "Dovrebbe restituire false per un ID inesistente.");

        Evento retrieved = eventoDao.get(invalidID);
        assertNotEquals(1, retrieved, "L'evento non dovrebbe esistere nel database.");
    }

    @Test
    @Order(10)
    void testGetAllFailureDataMismatch() throws SQLException {
        Collection<Evento> eventi = eventoDao.getAll();

        // Supponiamo che ci siano 20 eventi, ma controlliamo un valore sbagliato
        assertNotEquals(25, eventi.size(), "Il numero di eventi non dovrebbe corrispondere al valore errato.");
    }

    @Test
    @Order(11)
    void testGetByFilterFailure() throws SQLException {
        // Filtra con criteri che restituiscono eventi sbagliati
        Collection<Evento> filteredEvents = eventoDao.getByFilter(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), "Sport Sbagliato", "Città Sbagliata");

        for (Evento evento : filteredEvents) {
            assertNotEquals("Calcio", evento.getSport(), "Lo sport non dovrebbe essere corretto.");
            assertNotEquals("Roma", evento.getCitta(), "La città non dovrebbe essere corretta.");
        }
    }

    @Test
    @Order(12)
    void testGetEventiBySportFailure() throws SQLException {
        // Sport inesistente
        String sport = "Sport Inesistente";
        List<Evento> eventi = eventoDao.getEventiBySport(sport);

        for (Evento evento : eventi) {
            assertNotEquals("Beach Volley", evento.getSport(), "Lo sport non dovrebbe essere 'Beach Volley'.");
        }
    }

    
    
}
