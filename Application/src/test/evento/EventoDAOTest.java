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
}
