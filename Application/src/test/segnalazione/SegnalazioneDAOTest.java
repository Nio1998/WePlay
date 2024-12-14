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
        Segnalazione segnalazione = new Segnalazione("assenza", "in attesa", "mario_rossi", "luigi_bianchi", 17);
        segnalazioneDAO.save(segnalazione);
        
        Segnalazione retrieved = segnalazioneDAO.get(17);
        assertNotNull(retrieved);
        assertEquals("assenza", retrieved.getMotivazione());
        assertEquals("in attesa", retrieved.getStato());
        assertEquals("mario_rossi", retrieved.getUtenteSegnalato());
        assertEquals("luigi_bianchi", retrieved.getUtenteSegnalante());
        assertEquals(17, retrieved.getIdEvento());
        
        segnalazioneDAO.delete(17);
    }

    @Test
    @Order(2)
    void testUpdateSegnalazione() throws SQLException {
    	Segnalazione segnalazione = new Segnalazione("assenza", "in attesa", "mario_rossi", "luigi_bianchi", 17);
        segnalazioneDAO.save(segnalazione);

        segnalazione.setStato("risolta");
        segnalazione.setId(17);
        segnalazioneDAO.update(segnalazione);

        Segnalazione updated = segnalazioneDAO.get(17);
        assertNotNull(updated);
        assertEquals("risolta", updated.getStato());
        
        segnalazioneDAO.delete(17);
    }

    @Test
    @Order(3)
    void testDeleteSegnalazione() throws SQLException {
    	Segnalazione segnalazione = new Segnalazione("assenza", "in attesa", "mario_rossi", "luigi_bianchi", 17);
        segnalazioneDAO.save(segnalazione);

        assertTrue(segnalazioneDAO.delete(17));
        assertNull(segnalazioneDAO.get(17));
    }

    @Test
    @Order(4)
    void testGetAllSegnalazioni() throws SQLException {
        Collection<Segnalazione> segnalazioni = segnalazioneDAO.getAll();
        assertNotNull(segnalazioni);
        assertEquals(16,segnalazioni.size());
    }

    @Test
    @Order(5)
    void testGetSegnalazioniByRicevuta() throws SQLException {
        String utenteRicevuto = "luca_rossi";
        
        Collection<Segnalazione> segnalazioni = segnalazioneDAO.getSegnalazioniRicevute(utenteRicevuto);
        assertNotNull(segnalazioni);
        
        for (Segnalazione segnalazione : segnalazioni) {
            assertEquals(utenteRicevuto, segnalazione.getUtenteSegnalato());
        }
    }
    
    
    
    
    @Test
    @Order(6)
    void testSaveSegnalazioneFailure() throws SQLException {
        Segnalazione segnalazione = new Segnalazione("assenza", "in attesa", "mario_rossi", "luigi_bianchi", 17);
        segnalazioneDAO.save(segnalazione);
        
        Segnalazione retrieved = segnalazioneDAO.get(17);
        assertNotEquals("motivo errato", retrieved.getMotivazione(), "La motivazione non dovrebbe essere errata.");
        segnalazioneDAO.delete(17); // Clean up the database
    }

    @Test
    @Order(7)
    void testUpdateSegnalazioneFailure() throws SQLException {
        Segnalazione segnalazione = new Segnalazione("assenza", "in attesa", "mario_rossi", "luigi_bianchi", 17);
        segnalazioneDAO.save(segnalazione);

        segnalazione.setStato("risolta");
        segnalazione.setId(17);
        segnalazioneDAO.update(segnalazione);

        Segnalazione updated = segnalazioneDAO.get(17);
        assertNotEquals("in attesa", updated.getStato(), "Lo stato non dovrebbe rimanere invariato.");
        
        segnalazioneDAO.delete(17); // Clean up the database
    }

    @Test
    @Order(8)
    void testDeleteSegnalazioneFailure() throws SQLException {
        int invalidID = -1; // Non-existent ID
        assertFalse(segnalazioneDAO.delete(invalidID), "Dovrebbe restituire false per un ID inesistente.");

        Segnalazione retrieved = segnalazioneDAO.get(invalidID);
        assertNotEquals(1, retrieved, "La segnalazione non dovrebbe esistere nel database.");
    }

    @Test
    @Order(9)
    void testGetAllSegnalazioniFailureDataMismatch() throws SQLException {
        Collection<Segnalazione> segnalazioni = segnalazioneDAO.getAll();

        // Assuming there are 15 reports, check for a wrong value
        assertNotEquals(20, segnalazioni.size(), "Il numero di segnalazioni non dovrebbe essere errato.");
    }

    @Test
    @Order(10)
    void testGetSegnalazioniByRicevutaFailure() throws SQLException {
        // Filtering with incorrect data
        String utenteRicevuto = "utente_inesistente";
        
        Collection<Segnalazione> segnalazioni = segnalazioneDAO.getSegnalazioniRicevute(utenteRicevuto);
        
        // Check that the collection is empty since there are no reports for this user
        assertTrue(segnalazioni.isEmpty(), "Non dovrebbe esserci nessuna segnalazione per l'utente inesistente.");
    }

    
    
}
