package model.utente;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.ConDB;
import model.Segnalazione.Segnalazione;

/**
 * La classe `UtenteDAO` gestisce le operazioni di accesso al database per la tabella `utente`.
 * Fornisce metodi per salvare, aggiornare, cercare e recuperare utenti dal database.
 */
public class UtenteDAO {

	 /**
     * Salva un nuovo utente nel database.
     * I campi salvati sono: username, cognome, nome, data di nascita, email e password.
     *
     * @param utente l'oggetto Utente da salvare
     * @throws SQLException se si verifica un errore durante l'operazione
     */
	public void save(UtenteBean utente) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = ConDB.getConnection();

	        // Query con i campi forniti al momento della registrazione
	        String insertSQL = "INSERT INTO utente (username, cognome, nome, data_di_nascita, email, pw) " +
	                           "VALUES (?, ?, ?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(insertSQL);

	        preparedStatement.setString(1, utente.getUsername());
	        preparedStatement.setString(2, utente.getCognome());
	        preparedStatement.setString(3, utente.getNome());
	        preparedStatement.setDate(4, java.sql.Date.valueOf(utente.getDataDiNascita()));
	        preparedStatement.setString(5, utente.getEmail());
	        preparedStatement.setString(6, utente.getPw());

	        preparedStatement.executeUpdate();
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	        } finally {
	            if (connection != null) {
	                ConDB.releaseConnection(connection);
	            }
	        }
	    }
	}

	/**
     * Aggiorna le informazioni di un utente esistente nel database.
     * I campi aggiornati sono: email e, se fornita, la password.
     *
     * @param utente l'oggetto Utente con i dati aggiornati
     * @throws SQLException se si verifica un errore durante l'operazione o nessun utente viene aggiornato
     */
	public void update(UtenteBean utente) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = ConDB.getConnection();
	        boolean isChangingPw = false;

	        // Query base per aggiornare i campi
	        String updateSQL = "UPDATE utente SET email = ?";

	        // Aggiungi il campo password se necessario
	        if (utente.getPw() != null && !utente.getPw().isEmpty()) {
	            updateSQL += ", pw = ?";
	            isChangingPw = true;
	        }

	        updateSQL += " WHERE username = ?";
	        preparedStatement = connection.prepareStatement(updateSQL);

	        // Imposta i valori dei parametri
	        preparedStatement.setString(1, utente.getEmail());

	        int index = 2;
	        if (isChangingPw) {
	            preparedStatement.setString(index++, utente.getPw());
	        }
	        preparedStatement.setString(index, utente.getUsername());

	        // Esegue l'aggiornamento
	        int rowsUpdated = preparedStatement.executeUpdate();

	        // Verifica se � stato aggiornato almeno un record
	        if (rowsUpdated == 0) {
	            throw new SQLException("Update failed: no rows affected for username = " + utente.getUsername());
	        }
	    } finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	        } finally {
	            if (connection != null) {
	                ConDB.releaseConnection(connection);
	            }
	        }
	    }
	}
	
	
	public synchronized boolean delete(UtenteBean utente) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
        	// Ottieni la connessione al database
	        connection = ConDB.getConnection();

	        // Prepara la query di cancellazione
	        preparedStatement = connection.prepareStatement("DELETE FROM utente WHERE username = ?");
	        
	        // Imposta i parametri della query
	        preparedStatement.setString(1, utente.getUsername());

	        // Esegui la query di cancellazione
	        int rowsAffected = preparedStatement.executeUpdate();

	        // Restituisce true se la cancellazione ha avuto successo (rowsAffected == 1)
	        return rowsAffected > 0;
	    } finally {
	        // Chiudi il PreparedStatement se aperto
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
        	}
	        // Rilascia la connessione al pool se aperta
	        if (connection != null) {
	            ConDB.releaseConnection(connection);
	        }
	    }
	}


	/**
     * Trova un utente nel database in base al suo username.
     *
     * @param username lo username dell'utente da cercare
     * @return un oggetto `Utente` se trovato, altrimenti `null`
     * @throws SQLException se si verifica un errore durante l'operazione
     */
	public UtenteBean findByUsername(String username) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = ConDB.getConnection();
	        String selectSQL = "SELECT * FROM utente WHERE username = ?";
	        preparedStatement = connection.prepareStatement(selectSQL);
	        preparedStatement.setString(1, username);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	        	String data = resultSet.getString("data_di_nascita");
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        LocalDate data_di_nascita = LocalDate.parse(data, formatter);
		        LocalDateTime data_timeout= (LocalDateTime) resultSet.getObject("data_ora_fine_timeout");
		        
		        
		        // Costruzione dell'oggetto Utente dai risultati del database
		        return new UtenteBean(
		            resultSet.getString("username"),
		            resultSet.getString("cognome"),
		            resultSet.getString("nome"),
		            data_di_nascita,
		            resultSet.getString("email"),
		            resultSet.getString("pw"),
		            resultSet.getInt("num_timeout"),
		            resultSet.getBoolean("is_timeout"),
		            resultSet.getBoolean("is_admin"),
		            data_timeout,
		            resultSet.getInt("num_valutazioni_neutre"),
		            resultSet.getInt("num_valutazioni_negative"),
		            resultSet.getInt("num_valutazioni_positive")    
		        );
	        }
	    } finally {
	        // Chiude il ResultSet
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        // Chiude il PreparedStatement
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        // Rilascia la connessione
	        if (connection != null) {
	            ConDB.releaseConnection(connection);
	        }
	    }

	    // Restituisce null se non viene trovato alcun utente
	    return null;
	}	
	
	public UtenteBean findByEmail(String email) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = ConDB.getConnection();
	        String selectSQL = "SELECT * FROM utente WHERE email = ?";
	        preparedStatement = connection.prepareStatement(selectSQL);
	        preparedStatement.setString(1, email);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	        	String data = resultSet.getString("data_di_nascita");
	        			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            LocalDate data_di_nascita = LocalDate.parse(data, formatter);
	            LocalDateTime data_timeout= (LocalDateTime) resultSet.getObject("data_ora_fine_timeout");
	            
	            
	            // Costruzione dell'oggetto Utente dai risultati del database
	            return new UtenteBean(
	                resultSet.getString("username"),
	                resultSet.getString("cognome"),
	                resultSet.getString("nome"),
	                data_di_nascita,
	                resultSet.getString("email"),
	                resultSet.getString("pw"),
	                resultSet.getInt("num_timeout"),
	                resultSet.getBoolean("is_timeout"),
	                resultSet.getBoolean("is_admin"),
	                data_timeout,
	                resultSet.getInt("num_valutazioni_neutre"),
	                resultSet.getInt("num_valutazioni_negative"),
	                resultSet.getInt("num_valutazioni_positive")    
	            );
	        }
	    } finally {
	        // Chiude il ResultSet
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        // Chiude il PreparedStatement
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        // Rilascia la connessione
	        if (connection != null) {
	            ConDB.releaseConnection(connection);
	        }
	    }

	    // Restituisce null se non viene trovato alcun utente
	    return null;
	}
	
	
	
	
	

	/**
     * Recupera tutti gli utenti presenti nel database.
     *
     * @return una lista di oggetti `Utente` contenente tutti gli utenti
     * @throws SQLException se si verifica un errore durante l'operazione
     */
	public List<UtenteBean> getAllUtenti() throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<UtenteBean> utenti = new ArrayList<>();

	    try {
	        connection = ConDB.getConnection();
	        String selectSQL = "SELECT * FROM utente";
	        preparedStatement = connection.prepareStatement(selectSQL);
	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	        	String data = resultSet.getString("data_di_nascita");
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data_di_nascita = LocalDate.parse(data, formatter);
        LocalDateTime data_timeout= (LocalDateTime) resultSet.getObject("data_ora_fine_timeout");
        
        
        // Costruzione dell'oggetto Utente dai risultati del database
        UtenteBean utente = new UtenteBean(
            resultSet.getString("username"),
            resultSet.getString("cognome"),
            resultSet.getString("nome"),
            data_di_nascita,
            resultSet.getString("email"),
            resultSet.getString("pw"),
            resultSet.getInt("num_timeout"),
            resultSet.getBoolean("is_timeout"),
            resultSet.getBoolean("is_admin"),
            data_timeout,
            resultSet.getInt("num_valutazioni_neutre"),
            resultSet.getInt("num_valutazioni_negative"),
            resultSet.getInt("num_valutazioni_positive")    
        );
	            utenti.add(utente);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	        } finally {
	            if (connection != null) {
	                ConDB.releaseConnection(connection);
	            }
	        }
	    }
	    return utenti;
	}
	
	public void aggiornaTimeout(String username, boolean isTimeout, LocalDateTime dataOraFineTimeout) throws SQLException {
        String query = "UPDATE utente SET is_timeout = ?, data_ora_fine_timeout = ?, num_timeout = num_timeout + 1 WHERE username = ?";
        try (Connection conn = ConDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
        	
            stmt.setBoolean(1, isTimeout);
            stmt.setTimestamp(2, Timestamp.valueOf(dataOraFineTimeout));
            stmt.setString(3, username);

            stmt.executeUpdate();
        }
	}
	
}