package utente;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.utente.UtenteBean;
import model.utente.UtenteDAO;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
class UtenteDAOTest {

    private static UtenteDAO utenteDao;

    @BeforeAll
    static void setupDatabase() {
        utenteDao = new UtenteDAO();
    }

    /**
     * Testa il salvataggio di un utente che non è ancora presente nel database.
     */
    @Test
    @Order(1)
    void testSaveUtente_NonPresenteNelDatabase() throws SQLException {
        UtenteBean utente = new UtenteBean(
            "userTest",
            "Rossi",
            "Mario",
            LocalDate.of(1990, 1, 1),
            "mario.rossi@example.com",
            "password123",
            0,
            false,
            false,
            null,
            0,
            0,
            0
        );

        utenteDao.save(utente);
        UtenteBean retrieved = utenteDao.findByUsername("userTest");
        assertNotNull(retrieved);
        assertEquals("Mario", retrieved.getNome());
        assertEquals("Rossi", retrieved.getCognome());
        assertEquals(LocalDate.of(1990, 1, 1), retrieved.getDataDiNascita());
        assertEquals("mario.rossi@example.com", retrieved.getEmail());
        assertEquals("password123", retrieved.getPw());
        assertFalse(retrieved.isTimeout());
        assertFalse(retrieved.isAdmin());
    }

    /**
     * Testa il comportamento quando si cerca di salvare un utente già presente nel database.
     */
    @Test
    @Order(2)
    void testSaveUtente_GiaNelDatabase() {
        UtenteBean utente = new UtenteBean(
            "userTest",
            "Rossi",
            "Mario",
            LocalDate.of(1990, 1, 1),
            "mario.rossi@example.com",
            "password123",
            0,
            false,
            false,
            null,
            0,
            0,
            0
        );

        assertThrows(SQLException.class, () -> {
            utenteDao.save(utente);
        });
    }

    /**
     * Testa il comportamento quando si tenta di salvare un utente con attributi mancanti.
     */
    @Test
    @Order(3)
    void testSaveUtente_AttributiMancanti() {
        UtenteBean utenteErrore = new UtenteBean();
        utenteErrore.setUsername("userTest2");
        utenteErrore.setNome("Luigi");
        utenteErrore.setEmail("luigi@example.com");
        utenteErrore.setDataDiNascita(LocalDate.now());

        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            utenteDao.save(utenteErrore);
        });
    }

    /**
     * Testa il comportamento quando si tenta di salvare un utente nullo.
     */
    @Test
    @Order(4)
    void testSaveUtente_UtenteNull() {
        UtenteBean utenteNull = null;
        assertThrows(NullPointerException.class, () -> {
            utenteDao.save(utenteNull);
        });
    }

    /**
     * Testa l'aggiornamento dei dati di un utente esistente.
     */
    @Test
    @Order(5)
    void testUpdateUtente() throws SQLException {
        UtenteBean utente = utenteDao.findByUsername("userTest");
        assertNotNull(utente);

        utente.setEmail("mario.rossi_updated@example.com");
        utente.setPw("newpassword123");
        utenteDao.update(utente);

        UtenteBean updated = utenteDao.findByUsername("userTest");
        assertNotNull(updated);
        assertEquals("mario.rossi_updated@example.com", updated.getEmail());
        assertEquals("newpassword123", updated.getPw());
    }

    /**
     * Testa la ricerca di un utente tramite username.
     */
    @Test
    @Order(6)
    void testFindByUsername() throws SQLException {
        UtenteBean utente = utenteDao.findByUsername("userTest");
        assertNotNull(utente);
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
        assertEquals("mario.rossi_updated@example.com", utente.getEmail());
    }

    /**
     * Testa il recupero di tutti gli utenti dal database.
     */
    @Test
    @Order(7)
    void testGetAllUtenti() throws SQLException {
        List<UtenteBean> utenti = utenteDao.getAllUtenti();
        assertNotNull(utenti);
        assertTrue(utenti.size() > 0);
    }

    /**
     * Testa la ricerca di un utente tramite email quando l'utente esiste.
     */
    @Test
    @Order(8)
    void testFindByEmail_UtenteEsistente() throws SQLException {
        UtenteBean utente = utenteDao.findByEmail("mario.rossi_updated@example.com");
        assertNotNull(utente);
        assertEquals("userTest", utente.getUsername());
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
    }

    /**
     * Testa la ricerca di un utente tramite email quando l'utente non esiste.
     */
    @Test
    @Order(9)
    void testFindByEmail_UtenteNonEsistente() throws SQLException {
        UtenteBean utenteNonEsistente = utenteDao.findByEmail("mario.rossi_nonesistente@example.com");
        assertNull(utenteNonEsistente);
    }

    /**
     * Testa l'aggiornamento dello stato di timeout di un utente.
     */
    @Test
    @Order(10)
    void testAggiornaTimeout() throws SQLException {
        UtenteBean utente = utenteDao.findByUsername("userTest");
        assertNotNull(utente);

        // Verifica lo stato iniziale
        assertFalse(utente.isTimeout());

        // Imposta timeout
        utenteDao.aggiornaTimeout("userTest", true, LocalDateTime.of(2024, 1, 5, 12, 30));

        // Recupera e verifica
        UtenteBean updated = utenteDao.findByUsername("userTest");
        assertNotNull(updated);
        assertTrue(updated.isTimeout());
        assertEquals(LocalDateTime.of(2024, 1, 5, 12, 30), updated.getDataOraFineTimeout());
    }

    /**
     * Testa la cancellazione di un utente presente nel database.
     */
    @Test
    @Order(11)
    void testDelete_UtentePresenteNelDatabase() throws SQLException {
        UtenteBean utente = new UtenteBean();
        utente.setUsername("userTest");
        assertTrue(utenteDao.delete(utente));
        assertNull(utenteDao.findByUsername("userTest"));
    }

    /**
     * Testa la cancellazione di un utente non presente nel database.
     */
    @Test
    @Order(12)
    void testDelete_UtenteNonPresenteNelDatabase() throws SQLException {
        UtenteBean utenteNonEsistente = new UtenteBean();
        utenteNonEsistente.setUsername("NOTEXISTINGUSER");
        assertFalse(utenteDao.delete(utenteNonEsistente));
    }
}
