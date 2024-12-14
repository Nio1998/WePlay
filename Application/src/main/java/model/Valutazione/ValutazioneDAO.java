package model.Valutazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import model.ConDB;

public class ValutazioneDAO {
	
	
	public synchronized void save(ValutazioneBean valutazione) throws SQLException {
	    Connection conn = null;
	    try {
	        conn = ConDB.getConnection();

	        // Recupera il massimo ID attuale
	        int nextId = 1; // Default se non ci sono righe
	        try (PreparedStatement queryMax = conn.prepareStatement("SELECT MAX(ID) AS max_id FROM valutazione")) {
	            try (ResultSet rs = queryMax.executeQuery()) {
	                if (rs.next()) {
	                    nextId = rs.getInt("max_id") + 1;
	                }
	            }
	        }

	        // Inserisce una nuova valutazione
	        try (PreparedStatement query = conn.prepareStatement(
	                "INSERT INTO valutazione (ID, esito, utente_valutato, utente_valutante, ID_evento) VALUES (?, ?, ?, ?, ?)")) {
	            query.setInt(1, nextId);
	            query.setInt(2, valutazione.getEsito());
	            query.setString(3, valutazione.getUtenteValutato());
	            query.setString(4, valutazione.getUtenteValutante());
	            query.setInt(5, valutazione.getIdEvento());

	            query.executeUpdate();
	        }
	    } finally {
	        if (conn != null) {
	            ConDB.releaseConnection(conn);
	        }
	    }
	}



    
    
    
    
    public synchronized boolean delete(int id) throws SQLException {
    	
    	Connection conn = null;
    	
    	try {
    		conn = ConDB.getConnection();

    		try (PreparedStatement query = conn.prepareStatement("DELETE FROM valutazione WHERE ID = ?")) {
            query.setInt(1, id);
            return query.executeUpdate() != 0; // Restituisce true se almeno una riga è stata eliminata
	        }
	    }finally {
		        ConDB.releaseConnection(conn);
		    }
    }

    
    
    
    public synchronized ValutazioneBean get(int id) throws SQLException {
    	
    	Connection conn = null;
    	
    	try {
    		conn = ConDB.getConnection();
    	
        try(PreparedStatement query = conn.prepareStatement("SELECT * FROM valutazione WHERE ID = ?")) {
            query.setInt(1, id);
            
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    return new ValutazioneBean(
                    	rs.getInt("ID"),
                        rs.getInt("esito"),
                        rs.getString("utente_valutato"),
                        rs.getString("utente_valutante"),
                        rs.getInt("ID_evento")
                    );
                } else {
                	return null;
                	}
            	}
        	}
    	}finally {
	        ConDB.releaseConnection(conn);
	    }
    }

    public synchronized List<ValutazioneBean> getAll() throws SQLException {
        
        Connection conn = null;
        
        try {
            // Ottieni la connessione dalla lista gestita
            conn = ConDB.getConnection();
            
            // Usa try-with-resources per PreparedStatement e ResultSet
            try (PreparedStatement query = conn.prepareStatement("SELECT * FROM valutazione");
                 ResultSet rs = query.executeQuery()) {
                
                List<ValutazioneBean> valutazioni = new ArrayList<>();
                while (rs.next()) {
                    valutazioni.add(new ValutazioneBean(
                    	rs.getInt("ID"),
                        rs.getInt("esito"),
                        rs.getString("utente_valutato"),
                        rs.getString("utente_valutante"),
                        rs.getInt("ID_evento")
                    ));
                }
                return valutazioni;
            }
        } finally {
            // Rilascia la connessione se necessario
            ConDB.releaseConnection(conn);
        }
    }
    
    public List<ValutazioneBean> findByUsernameEvent(String usernameValutante, int eventoId) {
        List<ValutazioneBean> valutazioni = new ArrayList<>();
        String query = "SELECT * FROM valutazione WHERE utente_valutante = ? AND ID_evento = ?";
        Connection conn = null;

        try {
            conn = ConDB.getConnection(); // Ottieni la connessione
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, usernameValutante);
                ps.setInt(2, eventoId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ValutazioneBean valutazione = new ValutazioneBean();
                        valutazione.setId(rs.getInt("ID"));
                        valutazione.setEsito(rs.getInt("esito"));
                        valutazione.setUtenteValutato(rs.getString("utente_valutato"));
                        valutazione.setUtenteValutante(rs.getString("utente_valutante"));
                        valutazione.setIdEvento(rs.getInt("ID_evento"));

                        valutazioni.add(valutazione);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gestisci l'errore in modo appropriato
        } finally {
            // Rilascia sempre la connessione
            if (conn != null) {
                ConDB.releaseConnection(conn);
            }
        }

        return valutazioni; // Ritorna la lista, anche se vuota
    }

    
    
}

