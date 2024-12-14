package prenotazione;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import model.Valutazione.ValutazioneBean;
import model.prenotazione.PrenotazioneBean;
import model.prenotazione.PrenotazioneDAO;
import model.utente.UtenteBean;

class PrenotazioneDaoTEST {

	private static PrenotazioneDAO prenotazioneDao;

    @BeforeAll
    static void setupDatabase() {
    	prenotazioneDao = new PrenotazioneDAO();
    }

    @Test
    @Order(1)
    void testSavePrenotazione() throws SQLException {
        PrenotazioneBean prenotazione1 = new PrenotazioneBean();
        prenotazione1.setUtenteUsername("giovanni_roma");
        prenotazione1.setEventoID(3);
        prenotazione1.setStato("attiva");
        prenotazione1.setPosizioneInCoda(0);
        prenotazioneDao.save(prenotazione1);

        PrenotazioneBean retrieved = prenotazioneDao.get("giovanni_roma", 3);
        assertNotNull(retrieved);
        assertEquals("giovanni_roma", retrieved.getUtenteUsername());
        assertEquals(3, retrieved.getEventoID());
        assertEquals("attiva", retrieved.getStato());
        assertEquals(0, retrieved.getPosizioneInCoda());

       prenotazioneDao.delete("giovanni_roma", 3);
    }

    @Test
    @Order(2)
    void testSavePrenotazioneFailure() throws SQLException {
        PrenotazioneBean prenotazione1 = new PrenotazioneBean();
        prenotazione1.setUtenteUsername("giovanni_roma");
        prenotazione1.setEventoID(3);
        prenotazione1.setStato("attiva");
        prenotazione1.setPosizioneInCoda(0);
        prenotazioneDao.save(prenotazione1);

        PrenotazioneBean retrieved = prenotazioneDao.get("giovanni_roma", 3);
        assertNotNull(retrieved);
        assertEquals("giovanni_roma", retrieved.getUtenteUsername());
        assertNotEquals(4, retrieved.getEventoID());
        assertEquals("attiva", retrieved.getStato());
        assertEquals(0, retrieved.getPosizioneInCoda());

       prenotazioneDao.delete("giovanni_roma", 3);
    }
    @Test
    @Order(3)
    void testDeleteValutazione() throws SQLException {
    	PrenotazioneBean prenotazione1 = new PrenotazioneBean();
        prenotazione1.setUtenteUsername("giovanni_roma");
        prenotazione1.setEventoID(3);
        prenotazione1.setStato("attiva");
        prenotazione1.setPosizioneInCoda(0);
        prenotazioneDao.save(prenotazione1);

        assertTrue(prenotazioneDao.delete("giovanni_roma", 3));
        assertThrows(SQLException.class,() -> {prenotazioneDao.get("giovanni_roma",3);} );
    }
    @Test
    @Order(4)
    void testDeleteValutazioneFailure() throws SQLException {
    	PrenotazioneBean prenotazione1 = new PrenotazioneBean();
        prenotazione1.setUtenteUsername("giovanni_roma");
        prenotazione1.setEventoID(3);
        prenotazione1.setStato("attiva");
        prenotazione1.setPosizioneInCoda(0);
        prenotazioneDao.save(prenotazione1);

        assertFalse(prenotazioneDao.delete("marco_rossi", 3));
        prenotazioneDao.delete("giovanni_roma", 3);
        assertThrows(SQLException.class,() -> {prenotazioneDao.get("giovanni_roma",3);} );

    }
    @Test
    @Order(5)
    void testGetAllValutazione() throws SQLException {
        Collection<PrenotazioneBean> prenotazione = prenotazioneDao.getAll();
        assertNotNull(prenotazione);
        assertEquals(16,prenotazione.size());
    }
    @Test
    @Order(6)
    void testGetAllValutazioneFailure() throws SQLException {
        Collection<PrenotazioneBean> prenotazione = prenotazioneDao.getAll();
        assertNotNull(prenotazione);
        assertNotEquals(18,prenotazione.size());
    }
    @Test
    @Order(7)
    void testGetAllWhereEventoValutazione() throws SQLException {
    	List<UtenteBean> u = prenotazioneDao.getAllWhereEvento(3);
    	assertNotNull(u);
        assertEquals(1,u.size());

    }
    @Test
    @Order(8)
    void testGetAllWhereEventoValutazioneFailure() throws SQLException {
    	List<UtenteBean> u = prenotazioneDao.getAllWhereEvento(3);
    	assertNotNull(u);
        assertNotEquals(2,u.size());		
    }
    @Test
    @Order(9)
    void testnewPosInCodaValutazione() throws SQLException {
    	int posMax = prenotazioneDao.newPosInCoda(3);
    	assertEquals(2,posMax);

    }
    @Test
    @Order(10)
    void testnewPosInCodaValutazioneFailure() throws SQLException {
    	int posMax = prenotazioneDao.newPosInCoda(3);
    	assertNotEquals(1,posMax);

    }
}