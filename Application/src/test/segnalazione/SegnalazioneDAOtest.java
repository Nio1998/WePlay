package segnalazione;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import model.Segnalazione.Segnalazione;
import model.Segnalazione.SegnalazioneDAO;
import java.sql.SQLException;
import java.util.Collection;

@TestMethodOrder(OrderAnnotation.class)
class SegnalazioneDAOTest {

    private static SegnalazioneDAO segnalazioneDAO;

    @BeforeAll
    static void setupDatabase() {
        segnalazioneDAO = new SegnalazioneDAO();
    }

    @Test
    @Order(1)
    void testSaveSegnalazione() throws SQLException {
        Segnalazione segnalazione = new Segnalazione("MotivazioneTest", "In sospeso", "UtenteSegnalatoTest", "UtenteSegnalanteTest", 1);
        segnalazioneDAO.save(segnalazione);
        
        Segnalazione retrieved = segnalazioneDAO.get(segnalazione.getId());
        assertNotNull(retrieved);
        assertEquals("MotivazioneTest", retrieved.getMotivazione());
        assertEquals("In sospeso", retrieved.getStato());
        assertEquals("UtenteSegnalatoTest", retrieved.getUtenteSegnalato());
        assertEquals("UtenteSegnalanteTest", retrieved.getUtenteSegnalante());
        assertEquals(1, retrieved.getIdEvento());
        
        segnalazioneDAO.delete(segnalazione.getId());
    }

    @Test
    @Order(2)
    void testUpdateSegnalazione() throws SQLException {
        Segnalazione segnalazione = new Segnalazione("MotivazioneTest", "In sospeso", "UtenteSegnalatoTest", "UtenteSegnalanteTest", 1);
        segnalazioneDAO.save(segnalazione);

        segnalazione.setStato("Risolto");
        segnalazioneDAO.update(segnalazione);

        Segnalazione updated = segnalazioneDAO.get(segnalazione.getId());
        assertNotNull(updated);
        assertEquals("Risolto", updated.getStato());
        
        segnalazioneDAO.delete(segnalazione.getId());
    }

    @Test
    @Order(3)
    void testDeleteSegnalazione() throws SQLException {
        Segnalazione segnalazione = new Segnalazione("MotivazioneTest", "In sospeso", "UtenteSegnalatoTest", "UtenteSegnalanteTest", 1);
        segnalazioneDAO.save(segnalazione);

        assertTrue(segnalazioneDAO.delete(segnalazione.getId()));
        assertNull(segnalazioneDAO.get(segnalazione.getId()));
    }

    @Test
    @Order(4)
    void testGetAllSegnalazioni() throws SQLException {
        Collection<Segnalazione> segnalazioni = segnalazioneDAO.getAll();
        assertNotNull(segnalazioni);
        assertTrue(segnalazioni.size() > 0);
    }

    @Test
    @Order(5)
    void testGetSegnalazioniByRicevuta() throws SQLException {
        String utenteRicevuto = "UtenteSegnalatoTest";
        
        Collection<Segnalazione> segnalazioni = segnalazioneDAO.getSegnalazioniRicevute(utenteRicevuto);
        assertNotNull(segnalazioni);
        
        for (Segnalazione segnalazione : segnalazioni) {
            assertEquals(utenteRicevuto, segnalazione.getUtenteSegnalato());
        }
    }
}
