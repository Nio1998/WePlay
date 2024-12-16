package model.prenotazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.ConDB;
import model.utente.UtenteBean;

public class PrenotazioneDAO {
	public synchronized void save(PrenotazioneBean p) throws SQLException {
		Connection conn = null;
		try {
			conn = ConDB.getConnection();
			try (PreparedStatement query = conn.prepareStatement("INSERT INTO prenotazione (username_utente, ID_evento, stato, posizione_in_coda) VALUES (?, ?, ?, ?)")) {
				query.setString(1, p.getUtenteUsername());
				query.setInt(2, p.getEventoID());
				query.setString(3, p.getStato());
				query.setInt(4, p.getPosizioneInCoda());
				
				query.executeUpdate();	
				
			}
		} finally {
			ConDB.releaseConnection(conn);
		}
	}

	public synchronized void update(PrenotazioneBean p) throws SQLException {
		Connection conn = null;
		try {
			conn = ConDB.getConnection();
			try (PreparedStatement query = conn.prepareStatement("UPDATE prenotazione SET stato = ?, posizione_in_coda = ? WHERE username_utente = ? AND ID_evento = ?")) {
				query.setString(1, p.getStato());
				query.setInt(2, p.getPosizioneInCoda());
				query.setString(3, p.getUtenteUsername());
				query.setInt(4, p.getEventoID());
				
				query.executeUpdate();		
			}
		} finally {
			ConDB.releaseConnection(conn);
		}
	}

	public synchronized boolean delete(String utenteUsername, int eventoID) throws SQLException {
		Connection conn = null;
		try {
			
			conn = ConDB.getConnection();
			try (PreparedStatement query = conn.prepareStatement("DELETE FROM prenotazione WHERE username_utente = ? AND ID_evento = ?")) {
				query.setString(1, utenteUsername);
				query.setInt(2, eventoID);
				
			 return (query.executeUpdate() != 0);	
			}
		} finally {
			ConDB.releaseConnection(conn);
		}
	}

	public synchronized PrenotazioneBean get(String utenteUsername, int eventoID) throws SQLException {
		Connection conn = null;
		try {
			conn = ConDB.getConnection();
			try (PreparedStatement query = conn.prepareStatement("SELECT * FROM prenotazione WHERE username_utente = ? AND ID_evento = ?")) {
				query.setString(1, utenteUsername);
				query.setInt(2, eventoID);
				try (ResultSet rs = query.executeQuery()) {
					rs.next();
					return new PrenotazioneBean(rs);
				}
				catch(SQLException e) {
			        throw new SQLException("Errore durante il recupero dei dati dell' utente che si è prenotato per l'evento con ID " + eventoID, e);
				}
			}
		} finally {
			ConDB.releaseConnection(conn);
		}
	}
	
	public synchronized List<PrenotazioneBean> getAll() throws SQLException {
		Connection conn = null;
		try {
			conn = ConDB.getConnection();
			try (PreparedStatement query = conn.prepareStatement("SELECT * FROM prenotazione")) {
				try (ResultSet rs = query.executeQuery()) {
					
					List<PrenotazioneBean> prenotazioni = new ArrayList<>();
					while (rs.next()) {
						prenotazioni.add(new PrenotazioneBean(rs));
					}
					return prenotazioni;
				}				
			}
		} finally {
			ConDB.releaseConnection(conn);
		}
	}





	public synchronized List<UtenteBean> getAllWhereEvento(int idEvento) throws SQLException {
	    Connection conn = null;
	    List<UtenteBean> utenti = new ArrayList<>();

	    try {
	        conn = ConDB.getConnection();

	        String sql = "SELECT u.username, u.cognome, u.nome, u.email, u.data_di_nascita "
	            +"FROM prenotazione p "
	            +"JOIN utente u ON p.username_utente = u.username "
	            +"WHERE p.ID_evento = ?";

	        try (PreparedStatement query = conn.prepareStatement(sql)) {
	            query.setInt(1, idEvento);

	            try (ResultSet rs = query.executeQuery()) {
	                while (rs.next()) {
	                    UtenteBean utente = new UtenteBean();
	                    utente.setUsername(rs.getString("username"));
	                    utente.setCognome(rs.getString("cognome"));
	                    utente.setNome(rs.getString("nome"));
	                    utente.setEmail(rs.getString("email"));
	                    utente.setDataDiNascita(rs.getDate("data_di_nascita").toLocalDate());
	                    utenti.add(utente);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        throw new SQLException("Errore durante il recupero degli utenti per l'evento con ID " + idEvento, e);
	    } finally {
	        if (conn != null) {
	            ConDB.releaseConnection(conn);
	        }
	    }

	    return utenti;
	}
	
	public int newPosInCoda(int evento_Id) {
	    int newPos = 1;  // Default a 1 se non ci sono prenotazioni in coda
	    
	    Connection conn = null;
	    
	    try {
	    	conn = ConDB.getConnection();
	        // SQL per ottenere la posizione massima in coda per l'evento
	        String sql = "SELECT MAX(posizione_in_coda) AS max_posizione " +
	                     "FROM prenotazione " +
	                     "WHERE ID_evento = ? AND stato = 'in coda'";

	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, evento_Id);

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    newPos = rs.getInt("max_posizione");
	                    if (rs.wasNull()) {
	                        newPos = 1; // Se non ci sono prenotazioni in coda, la posizione è 1
	                    } else {
	                        newPos++; // Altrimenti la nuova posizione è la massima + 1
	                    }
	                }
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();  // Gestire gli errori (es. log o rilanciare eccezione)
	    }finally {
	        if (conn != null) {
	            ConDB.releaseConnection(conn);
	        }
	    }

	    return newPos;  // Restituisce la nuova posizione in coda
	}
	
	
	
	
	public List<PrenotazioneBean> findPrenotazioniByEvento(int eventoID) {
		
		 
	    List<PrenotazioneBean> prenotazioni = new ArrayList<>();
	    String query = "SELECT * FROM prenotazione WHERE ID_evento = ?";
	    Connection conn = null;

	    
	    	
	   
	    try {
	        // Ottieni la connessione
	        conn = ConDB.getConnection();
	      
	        
	        // Prepara la query
	        try (PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setInt(1, eventoID);

	            // Esegui la query e ottieni il risultato
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                	
	                    PrenotazioneBean prenotazione = new PrenotazioneBean();
	                    prenotazione.setEventoID(rs.getInt("ID_evento"));
	                    prenotazione.setUtenteUsername(rs.getString("username_utente"));
	                    prenotazioni.add(prenotazione);
	                }
	            }
	        }

	    } catch (SQLException e) {
	        // Log dell'errore in caso di problemi con la query o la connessione
	        e.printStackTrace();
	    } finally {
	        // Rilascia la connessione al DB
	        ConDB.releaseConnection(conn);
	    }
	    
	   
	    return prenotazioni;
	}

	
	
	public String findOrganizzatoreByEventoID(int eventoID) {
		
		 
        String organizzatore = null;
       
        String query = "SELECT username_utente FROM prenotazione WHERE ID_evento = ? AND stato = 'organizzatore'";

	Connection conn = null;
        try{
        	
        	 
		 conn = ConDB.getConnection();
		 
		
             try(PreparedStatement stmt = conn.prepareStatement(query)) {
            	 
            

            stmt.setInt(1, eventoID);
            
            try (ResultSet rs = stmt.executeQuery()) {
            	
            	 System.out.println("exec??");
                if (rs.next()) {
                	
                    organizzatore = rs.getString("username_utente");
                   
                    
           	     }
          	  }
	     }

        } catch (Exception e) {
        	
            e.printStackTrace();
        }finally {
			ConDB.releaseConnection(conn);
		}
        
        System.out.println("null??");
        return organizzatore;
    }
	
	
}

