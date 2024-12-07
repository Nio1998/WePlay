package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SegnalazioneDAO {
	
	public void save(Segnalazione segnalazione) throws SQLException {
	    
	    try (Connection connection = ConDB.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(
	                 "INSERT INTO segnalazione (motivazione, stato, utente_segnalato, utente_segnalante, ID_evento) VALUES (?, ?, ?, ?, ?)")) {

	        // Imposta i parametri della query
	        preparedStatement.setString(1, segnalazione.getMotivazione()); // Colonna motivazione
	        preparedStatement.setString(2, "in attesa"); // Stato predefinito
	        preparedStatement.setString(3, segnalazione.getUtenteSegnalato()); // Colonna utente_segnalato
	        preparedStatement.setString(4, segnalazione.getUtenteSegnalante()); // Colonna utente_segnalante
	        preparedStatement.setInt(5, segnalazione.getIdEvento()); // Colonna ID_evento

	        // Esegui la query di inserimento
	        preparedStatement.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e; // Propaga l'eccezione
	    }
	}

	
	public void update(Segnalazione segnalazione) throws SQLException {
	    
	    try (Connection connection = ConDB.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(
	                 "UPDATE segnalazione SET motivazione = ?, stato = ?, utente_segnalato = ?, utente_segnalante = ? WHERE ID = ?")) {

	        // Imposta i parametri della query
	        preparedStatement.setString(1, segnalazione.getMotivazione());
	        preparedStatement.setString(2, segnalazione.getStato());
	        preparedStatement.setString(3, segnalazione.getUtenteSegnalato());
	        preparedStatement.setString(4, segnalazione.getUtenteSegnalante());
	        preparedStatement.setInt(5, segnalazione.getId());

	        // Esegui la query di aggiornamento
	        preparedStatement.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e; //Propaga l'eccezione
	    }
	}

	
	public boolean delete(Segnalazione segnalazione) throws SQLException {
		
		try(Connection connection = ConDB.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(
	                 "DELETE FROM segnalazione WHERE ID = ?")){
			
			//Imposta i parametri della query
			preparedStatement.setInt(1, segnalazione.getId());
			
			//Esegui la query di cancellazione
			int rows_affected = preparedStatement.executeUpdate();
			
			//Se la cancellazione è avventua con successo allora rows_affected sarà maggiore di 0
			return rows_affected > 0;
			
		} catch (SQLException e) {
			e.printStackTrace(); 
			throw e; //Propaga l'eccezione
		}
	}
	
	public Segnalazione get(int id) throws SQLException {
	    Segnalazione segnalazione = null;

	    String query = "SELECT * FROM segnalazione WHERE ID = ?";

	    try (Connection connection = ConDB.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        // Imposta il parametro dell'ID nella query
	        preparedStatement.setInt(1, id);

	        // Esegui la query e ottieni il ResultSet
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {

	            // Se ci sono risultati prende i dati nella segnalazione
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
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;  // Propaga l'eccezione
	    }

	    // Restituisce l'oggetto Segnalazione, oppure null se non trovato
	    return segnalazione;
	}
	
	public List<Segnalazione> getAll () throws SQLException {
		// Usa ArrayList per istanziare una lista di segnalazioni
		List <Segnalazione> segnalazioni = new ArrayList<>();
		Segnalazione segnalazione = null;
		
		String query = "SELECT * FROM segnalazione";
		try (Connection connection = ConDB.getConnection();
		         PreparedStatement preparedStatement = connection.prepareStatement(query)){
			
			//Esegui la query ed ottieni il ResultSet
			try (ResultSet resultSet = preparedStatement.executeQuery()){
				
				//Se ci sono risultati prende i dati nella tabella segnalazione
				while(resultSet.next()) {
					int segnalazioneId = resultSet.getInt("ID");
	                String motivazione = resultSet.getString("motivazione");
	                String stato = resultSet.getString("stato");
	                String utenteSegnalato = resultSet.getString("utente_segnalato");
	                String utenteSegnalante = resultSet.getString("utente_segnalante");
	                int idEvento = resultSet.getInt("ID_evento");

	                // Crea l'oggetto Segnalazione con i dati ottenuti
	                segnalazione = new Segnalazione(segnalazioneId, motivazione, stato, utenteSegnalato, utenteSegnalante, idEvento);
	               
	                //Aggiunge la segnalazione alla lista 
	                segnalazioni.add(segnalazione);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
	        throw e;  // Propaga l'eccezione
		}
		
		//Restituisce la lista delle segnalazioni
		return segnalazioni;
	}

}	

