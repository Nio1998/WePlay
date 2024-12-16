package utente;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.ConDB;
import model.utente.UtenteBean;
import model.utente.UtenteDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
class UtenteDAOTest {

    private static UtenteDAO utenteDao;
    private static Connection connection;
    
    UtenteBean get(String username) throws SQLException {
    	String selectSQL = "SELECT * FROM utente WHERE username = ?";
    	try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
    		preparedStatement.setString(1, username);
    		try (ResultSet resultSet = preparedStatement.executeQuery()) {
    			resultSet.next();
				// Costruzione dell'oggetto Utente dai risultati del database
				UtenteBean utente = new UtenteBean(
						resultSet.getString("username"),
						resultSet.getString("cognome"),
						resultSet.getString("nome"),
						resultSet.getDate("data_di_nascita").toLocalDate(),
						resultSet.getString("email"),
						resultSet.getString("pw"),
						resultSet.getInt("num_timeout"),
						resultSet.getBoolean("is_timeout"),
						resultSet.getBoolean("is_admin"),
						(LocalDateTime)resultSet.getObject("data_ora_fine_timeout"),
						resultSet.getInt("num_valutazioni_neutre"),
						resultSet.getInt("num_valutazioni_negative"),
						resultSet.getInt("num_valutazioni_positive")
				);
    				
				return utente;
    		}
	    } 
    }
    
    void delete(String username) throws SQLException {
    	String selectSQL = "DELETE FROM utente WHERE username = ?";
    	try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
    		preparedStatement.setString(1, username);
    		try {    			
    			preparedStatement.executeUpdate();
    		} catch (SQLException e) {
    			System.out.println("Utente non presente, vado avanti...");
			}
	    } 
    }
    
    void save() throws SQLException {
    	String insertSQL = "INSERT INTO utente (username, cognome, nome, data_di_nascita, email, pw, num_timeout, is_timeout, is_admin, data_ora_fine_timeout, num_valutazioni_neutre, num_valutazioni_negative, num_valutazioni_positive) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, "userTest");
			preparedStatement.setString(2, "Rossi");
			preparedStatement.setString(3, "Mario");
			preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.of(1990, 1, 1)));
			preparedStatement.setString(5, "mario.rossi@example.com");
			preparedStatement.setString(6, "password123");
			preparedStatement.setInt(7, 0);
			preparedStatement.setBoolean(8, false);
			preparedStatement.setBoolean(9, false);
			preparedStatement.setDate(10, null);
			preparedStatement.setInt(11, 0);
			preparedStatement.setInt(12, 0);
			preparedStatement.setInt(13, 0);
			
			preparedStatement.executeUpdate();
    	}
    }

    
    
    
    @BeforeAll
    static void setupDatabase() throws SQLException {
        utenteDao = new UtenteDAO();
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weplay", "root", ConDB.dbPassword);
        connection.setAutoCommit(true);
    }
    
    @AfterEach
    void removeUserTest() throws SQLException {
    	delete("userTest");
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
        
        UtenteBean retrieved = get("userTest");
        assertNotNull(retrieved);
        assertEquals("Mario", retrieved.getNome());
        assertEquals("Rossi", retrieved.getCognome());
        assertEquals(LocalDate.of(1990, 1, 1), retrieved.getDataDiNascita());
        assertEquals("mario.rossi@example.com", retrieved.getEmail());
        assertEquals("password123", retrieved.getPw());
        assertFalse(retrieved.isTimeout());
        assertFalse(retrieved.isAdmin());
        
        delete("userTest");
    }

    /**
     * Testa il comportamento quando si cerca di salvare un utente già presente nel database.
     * L'operazione deve generare una SQLException.
     */
    @Test
    @Order(2)
    void testSaveUtente_GiaNelDatabase() throws SQLException {
        save();
        
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
        
        delete("userTest");
    }

    /**
     * Testa il comportamento quando si tenta di salvare un utente con attributi mancanti.
     * L'operazione deve generare una SQLIntegrityConstraintViolationException.
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
     * L'operazione deve generare una NullPointerException.
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
     * Testa l'aggiornamento dei dati di un utente esistente nel database.
     */
    @Test
    @Order(5)
    void testUpdateUtente() throws SQLException {
        save();
        
        UtenteBean utente = get("userTest");
        assertNotNull(utente);

        utente.setEmail("mario.rossi_updated@example.com");
        utente.setPw("newpassword123");
        utenteDao.update(utente);

        UtenteBean updated = get("userTest");
        assertNotNull(updated);
        assertEquals("mario.rossi_updated@example.com", updated.getEmail());
        assertEquals("newpassword123", updated.getPw());
    }

    /**
     * Testa il comportamento quando si tenta di aggiornare un utente non presente nel database.
     * L'operazione deve generare una SQLException.
     */
    @Test
    @Order(6)
    void testUpdateUtenteNonPresente() throws SQLException {
    	UtenteBean utente = new UtenteBean();
    	
        utente.setUsername("userTestNonPresente");
        utente.setEmail("mario.rossi_updated@example.com");
        utente.setPw("newpassword123");
        
        assertThrows(SQLException.class, () -> {
            utenteDao.update(utente);
        });
    }

    /**
     * Testa la ricerca di un utente tramite username quando l'utente esiste.
     */
    @Test
    @Order(7)
    void testFindByUsername() throws SQLException {
        save();
        
        UtenteBean utente = utenteDao.findByUsername("userTest");
        assertNotNull(utente);
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
        assertEquals("mario.rossi@example.com", utente.getEmail());
    }

    /**
     * Testa la ricerca di un utente tramite username quando l'utente non esiste.
     */
    @Test
    @Order(8)
    void testFindByUsernameNonPresente() throws SQLException {
        UtenteBean utente = utenteDao.findByUsername("userTestNonPresente");
        assertNull(utente);
    }

    /**
     * Testa la ricerca di un utente tramite email quando l'utente esiste.
     */
    @Test
    @Order(9)
    void testFindByEmail_UtenteEsistente() throws SQLException {
        save();
        
        UtenteBean utente = utenteDao.findByEmail("mario.rossi@example.com");
        assertNotNull(utente);
        assertEquals("userTest", utente.getUsername());
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
    }

    /**
     * Testa la ricerca di un utente tramite email quando l'utente non esiste.
     */
    @Test
    @Order(10)
    void testFindByEmail_UtenteNonEsistente() throws SQLException {
        UtenteBean utenteNonEsistente = utenteDao.findByEmail("mario.rossi_nonesistente@example.com");
        assertNull(utenteNonEsistente);
    }

    /**
     * Testa l'aggiornamento dello stato di timeout di un utente nel database.
     */
    @Test
    @Order(11)
    void testAggiornaTimeout() throws SQLException {
        save();
        
        UtenteBean utente = get("userTest");
        assertNotNull(utente);

        // Verifica lo stato iniziale
        assertFalse(utente.isTimeout());

        // Imposta timeout
        utenteDao.aggiornaTimeout("userTest", true, LocalDateTime.of(2024, 1, 5, 12, 30));

        // Recupera e verifica
        UtenteBean updated = get("userTest");
        assertNotNull(updated);
        assertTrue(updated.isTimeout());
        assertEquals(LocalDateTime.of(2024, 1, 5, 12, 30), updated.getDataOraFineTimeout());
    }
    
    /**
     * Testa l'aggiornamento dello stato di timeout di un utente che non è presente nel database.
     */
    @Test
    @Order(13)
    void testAggiornaTimeoutUtenteNonPresente() throws SQLException {
        // Imposta timeout
    	assertThrows(SQLException.class, () -> {    		
    		utenteDao.aggiornaTimeout("userTestNonPresente", true, LocalDateTime.of(2024, 1, 5, 12, 30));
    	});
    }


}
