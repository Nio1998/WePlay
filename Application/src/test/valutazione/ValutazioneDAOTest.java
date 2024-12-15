package valutazione;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import model.Segnalazione.Segnalazione;
import model.Valutazione.ValutazioneBean;
import model.Valutazione.ValutazioneDAO;

class ValutazioneDAOTest {
	private static ValutazioneDAO valutazioneDao;

    @BeforeAll
    static void setupDatabase() {
    	valutazioneDao = new ValutazioneDAO();
    }

    @Test
    @Order(1)
    void testSaveValutazione() throws SQLException {
        ValutazioneBean valutazione = new ValutazioneBean();
        valutazione.setEsito(1);
        valutazione.setIdEvento(20);
        valutazione.setUtenteValutante("mario_rossi");
        valutazione.setUtenteValutato("giulia_verdi");
        valutazioneDao.save(valutazione);
        
        ValutazioneBean retrieved = valutazioneDao.get(17);
        assertNotNull(retrieved);
        assertEquals(1, retrieved.getEsito());
        assertEquals("mario_rossi", retrieved.getUtenteValutante());
        assertEquals("giulia_verdi", retrieved.getUtenteValutato());
        assertEquals(20, retrieved.getIdEvento());
        
       valutazioneDao.delete(17);
    }
    @Test
    @Order(2)
    void testSaveValutazioneFailure() throws SQLException {
        ValutazioneBean valutazione = new ValutazioneBean();
        valutazione.setEsito(1);
        valutazione.setIdEvento(2);
        valutazione.setUtenteValutante("anna_neri");
        valutazione.setUtenteValutato("giovanni_roma");
        assertThrows(java.sql.SQLIntegrityConstraintViolationException.class,() ->valutazioneDao.save(valutazione));
        valutazioneDao.delete(17);
    }
    @Test
    @Order(3)
    void testDeleteValutazione() throws SQLException {
    	ValutazioneBean valutazione = new ValutazioneBean(); 
    	valutazione.setEsito(1);
        valutazione.setIdEvento(20);
        valutazione.setUtenteValutante("mario_rossi");
        valutazione.setUtenteValutato("giulia_verdi");
        valutazioneDao.save(valutazione);

        assertTrue(valutazioneDao.delete(17));
        assertNull(valutazioneDao.get(17));
    }
    @Test
    @Order(4)
    void testDeleteValutazioneFailure() throws SQLException {
        int invalidID = -1; // ID inesistente
        assertFalse(valutazioneDao.delete(invalidID)); //"Dovrebbe restituire false per un ID inesistente."
    }
    @Test
    @Order(5)
    void testGetAllValutazione() throws SQLException {
        Collection<ValutazioneBean> valutazione = valutazioneDao.getAll();
        assertNotNull(valutazione);
        assertEquals(16,valutazione.size());
    }
    @Test
    @Order(6)
    void testGetAllValutazioneFailure() throws SQLException {
        Collection<ValutazioneBean> valutazione = valutazioneDao.getAll();
        assertNotNull(valutazione);
        assertNotEquals(17,valutazione.size());
    }
    @Test
    @Order(7)
    void testfindByUsernameEvent() throws SQLException{
    	String usernameValutante= "mario_rossi";
    	int eventoid = 20;
    	Collection<ValutazioneBean> valutazione = valutazioneDao.findByUsernameEvent(usernameValutante, eventoid);
    	assertNotNull(valutazione);
    	
    	for (ValutazioneBean valutazioni : valutazione) {
            assertEquals(usernameValutante, valutazioni.getUtenteValutante());
        }
    }
    @Test
    @Order(8)
    void testfindByUsernameEventFailure() throws SQLException{
    	String usernameValutante= "marioi";
    	int eventoid = 20;
    	Collection<ValutazioneBean> valutazione = valutazioneDao.findByUsernameEvent(usernameValutante, eventoid);
    	assertNotNull(valutazione);
    	
    	for (ValutazioneBean valutazioni : valutazione) {
            assertNotEquals(usernameValutante, valutazioni.getUtenteValutante());
        }
    }
}
