package model.prenotazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import model.ConDB;

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
				
				return query.executeUpdate() != 0;		
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
			}
		} finally {
			ConDB.releaseConnection(conn);
		}
	}
	
	public synchronized Collection<PrenotazioneBean> getAll() throws SQLException {
		Connection conn = null;
		try {
			conn = ConDB.getConnection();
			try (PreparedStatement query = conn.prepareStatement("SELECT * FROM prenotazione")) {
				try (ResultSet rs = query.executeQuery()) {
					
					ArrayList<PrenotazioneBean> prenotazioni = new ArrayList<>();
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
}
