package model.Segnalazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConDB;

public class SegnalazioneDAO {
	
	public synchronized void save(Segnalazione segnalazione) throws SQLException {
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        // Ottieni la connessione al database
	        conn = ConDB.getConnection();

	        // Prepara la query di inserimento
	        preparedStatement = conn.prepareStatement(
	                "INSERT INTO segnalazione (motivazione, stato, utente_segnalato, utente_segnalante, ID_evento) VALUES (?, ?, ?, ?, ?)");
	        
	        // Imposta i parametri della query
	        preparedStatement.setString(1, segnalazione.getMotivazione());  
	        preparedStatement.setString(2, segnalazione.getStato());       
	        preparedStatement.setString(3, segnalazione.getUtenteSegnalato());  
	        preparedStatement.setString(4, segnalazione.getUtenteSegnalante());
	        preparedStatement.setInt(5, segnalazione.getIdEvento());        

	        // Esegui la query di inserimento
	        preparedStatement.executeUpdate();

	    } finally {
	        // Chiudi il PreparedStatement se aperto
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        // Rilascia la connessione se aperta
	        if (conn != null) {
	            ConDB.releaseConnection(conn);
	        }
	    }
	}


	
	public synchronized void update(Segnalazione segnalazione) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        // Ottieni la connessione al database
	        connection = ConDB.getConnection();

	        // Prepara la query di aggiornamento
	        preparedStatement = connection.prepareStatement(
	                "UPDATE segnalazione SET motivazione = ?, stato = ?, utente_segnalato = ?, utente_segnalante = ? WHERE ID = ?");
	        
	        // Imposta i parametri della query
	        preparedStatement.setString(1, segnalazione.getMotivazione());  
	        preparedStatement.setString(2, segnalazione.getStato());         
	        preparedStatement.setString(3, segnalazione.getUtenteSegnalato());  
	        preparedStatement.setString(4, segnalazione.getUtenteSegnalante()); 
	        preparedStatement.setInt(5, segnalazione.getId());             
	        
	        // Esegui la query di aggiornamento
	        preparedStatement.executeUpdate();
	    } finally {
	        // Chiudi il PreparedStatement se aperto
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        // Rilascia la connessione se aperta
	        if (connection != null) {
	            ConDB.releaseConnection(connection);
	        }
	    }
	}


	
	public synchronized boolean delete(Segnalazione segnalazione) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        	// Ottieni la connessione al database
		        connection = ConDB.getConnection();
	
		        // Prepara la query di cancellazione
		        preparedStatement = connection.prepareStatement(
		                "DELETE FROM segnalazione WHERE ID = ?");
		        
		        // Imposta i parametri della query
		        preparedStatement.setInt(1, segnalazione.getId());
	
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
	        // Rilascia la connessione se aperta
	        if (connection != null) {
	            ConDB.releaseConnection(connection);
	        }
	    }
	}

	
	public synchronized Segnalazione get(int id) throws SQLException {
	    Segnalazione segnalazione = null;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        // Ottieni la connessione al database
	        connection = ConDB.getConnection();

	        // Prepara la query
	        String query = "SELECT * FROM segnalazione WHERE ID = ?";
	        preparedStatement = connection.prepareStatement(query);

	        // Imposta il parametro dell'ID nella query
	        preparedStatement.setInt(1, id);

	        // Esegui la query e ottieni il ResultSet
	        resultSet = preparedStatement.executeQuery();

	        // Se ci sono risultati, mappa i dati nella segnalazione
	        if (resultSet.next()) {
	            int segnalazioneId = resultSet.getInt("ID");
	            String motivazione = resultSet.getString("motivazione");
	            String stato = resultSet.getString("stato");
	            String utenteSegnalato = resultSet.getString("utente_segnalato");
	            String utenteSegnalante = resultSet.getString("utente_segnalante");
	            int idEvento = resultSet.getInt("ID_evento");

	            // Crea l'oggetto Segnalazione con i dati ottenuti
	            segnalazione = new Segnalazione(segnalazioneId, motivazione, stato, utenteSegnalato, utenteSegnalante, idEvento);
	        }

	    } finally {
	        // Chiudi il ResultSet se aperto
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        // Chiudi il PreparedStatement se aperto
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        // Rilascia la connessione se aperta
	        if (connection != null) {
	            ConDB.releaseConnection(connection);
	        }
	    }

	    // Restituisce l'oggetto Segnalazione, oppure null se non trovato
	    return segnalazione;
	}

	
	public synchronized List<Segnalazione> getAll() throws SQLException {
	    // Usa ArrayList per istanziare una lista di segnalazioni
	    List<Segnalazione> segnalazioni = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        // Ottieni la connessione al database
	        connection = ConDB.getConnection();

	        // Prepara la query
	        String query = "SELECT * FROM segnalazione";
	        preparedStatement = connection.prepareStatement(query);

	        // Esegui la query ed ottieni il ResultSet
	        resultSet = preparedStatement.executeQuery();

	        // Se ci sono risultati, prendi i dati nella tabella segnalazione
	        while (resultSet.next()) {
	            int segnalazioneId = resultSet.getInt("ID");
	            String motivazione = resultSet.getString("motivazione");
	            String stato = resultSet.getString("stato");
	            String utenteSegnalato = resultSet.getString("utente_segnalato");
	            String utenteSegnalante = resultSet.getString("utente_segnalante");
	            int idEvento = resultSet.getInt("ID_evento");

	            // Crea l'oggetto Segnalazione con i dati ottenuti
	            Segnalazione segnalazione = new Segnalazione(segnalazioneId, motivazione, stato, utenteSegnalato, utenteSegnalante, idEvento);

	            // Aggiungi la segnalazione alla lista
	            segnalazioni.add(segnalazione);
	        }
	    } finally {
	        // Chiudi il ResultSet se aperto
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        // Chiudi il PreparedStatement se aperto
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        // Rilascia la connessione se aperta
	        if (connection != null) {
	            ConDB.releaseConnection(connection);
	        }
	    }

	    // Restituisce la lista delle segnalazioni
	    return segnalazioni;
	}


}	

