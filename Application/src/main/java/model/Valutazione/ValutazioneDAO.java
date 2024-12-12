package model.Valutazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.ArrayList;

import model.ConDB;

public class ValutazioneDAO {
	
	
    public synchronized void save(ValutazioneBean valutazione) throws SQLException {
    	
    	Connection conn = null;
        try {
        	conn = ConDB.getConnection();
        	
        	try (PreparedStatement query = conn.prepareStatement("INSERT INTO valutazione (esito, utente_valutato, utente_valutante, ID_evento) VALUES (?, ?, ?, ?)")) {
            query.setInt(1, valutazione.getEsito());
            query.setString(2, valutazione.getUtenteValutato());
            query.setString(3, valutazione.getUtenteValutante());
            query.setInt(4, valutazione.getIdEvento());
            
            query.executeUpdate();
             
             }
        } finally {
            ConDB.releaseConnection(conn);
        }
    }

    public synchronized void update(ValutazioneBean valutazione) throws SQLException {
    	
    	Connection conn = null;
    	
        try {
        	conn = ConDB.getConnection();
        	
        	try(PreparedStatement query = conn.prepareStatement("UPDATE valutazione SET esito = ?, utente_valutato = ?, utente_valutante = ?, ID_evento = ? WHERE ID = ?")) {
            query.setInt(1, valutazione.getEsito());
            query.setString(2, valutazione.getUtenteValutato());
            query.setString(3, valutazione.getUtenteValutante());
            query.setInt(4, valutazione.getIdEvento());
            query.setInt(5, valutazione.getId());
            
            query.executeUpdate();
	        }
	    }finally {
	        ConDB.releaseConnection(conn);
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
                    throw new SQLException("Nessun risultato trovato per l'ID fornito.");
                	}
            	}
        	}
    	}finally {
	        ConDB.releaseConnection(conn);
	    }
    }

    public synchronized Collection<ValutazioneBean> getAll() throws SQLException {
        
        Connection conn = null;
        
        try {
            // Ottieni la connessione dalla lista gestita
            conn = ConDB.getConnection();
            
            // Usa try-with-resources per PreparedStatement e ResultSet
            try (PreparedStatement query = conn.prepareStatement("SELECT * FROM valutazione");
                 ResultSet rs = query.executeQuery()) {
                
                Collection<ValutazioneBean> valutazioni = new ArrayList<>();
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
}

