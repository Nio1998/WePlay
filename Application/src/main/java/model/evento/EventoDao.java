package model.evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import model.ConDB;

public class EventoDao {

    /*
       Salva un nuovo evento nel database.
       @param e L'evento da salvare.
       @throws SQLException Se si verifica un errore durante l'interazione con il database.
     */
    public synchronized void save(Evento e) throws SQLException {
        Connection conn = null;
        try {
            conn = ConDB.getConnection();
            try (PreparedStatement query = conn.prepareStatement("INSERT INTO evento (data_inizio, ora_inizio, prezzo, sport, titolo, indirizzo, massimo_di_partecipanti, citta, stato) VALUES (?, ?, ?, ?,?,?,?,?,?)")) {
                query.setObject(1, e.getData_inizio());
                query.setObject(2, e.getOra_inizio());
                query.setDouble(3, e.getPrezzo());
                query.setString(4, e.getSport());
                query.setString(5, e.getTitolo());
                query.setString(6, e.getIndirizzo());
                query.setInt(7, e.getMassimo_di_partecipanti());
                query.setString(8, e.getCitta());
                query.setString(9, e.getStato());
                query.executeUpdate();        
            }
        } finally {
            ConDB.releaseConnection(conn);
        }
    }

    /*
       Aggiorna i dettagli di un evento esistente nel database.
       @param e L'evento con i nuovi dettagli da aggiornare.
       @throws SQLException Se si verifica un errore durante l'interazione con il database.
     */
    public synchronized void update(Evento e) throws SQLException {
        Connection conn = null;
        try {
            conn = ConDB.getConnection();
            try (PreparedStatement query = conn.prepareStatement("UPDATE evento SET data_inizio = ?, ora_inizio = ?, prezzo = ?, sport = ?, titolo = ?, indirizzo = ?, massimo_di_partecipanti = ?, citta = ?, stato = ? WHERE ID = ?")) {
                query.setObject(1, e.getData_inizio());
                query.setObject(2, e.getOra_inizio());
                query.setDouble(3, e.getPrezzo());
                query.setString(4, e.getSport());
                query.setString(5, e.getTitolo());
                query.setString(6, e.getIndirizzo());
                query.setInt(7, e.getMassimo_di_partecipanti());
                query.setString(8, e.getCitta());
                query.setString(9, e.getStato());
                query.setInt(10, e.getID());
                query.executeUpdate();        
            }
        } finally {
            ConDB.releaseConnection(conn);
        }
    }

    /*
       Elimina un evento dal database in base al suo identificatore.
       @param ID L'identificatore dell'evento da eliminare.
       @return true se l'evento e stato eliminato, false altrimenti.
       @throws SQLException Se si verifica un errore durante l'interazione con il database.
     */
    public synchronized boolean delete(int ID) throws SQLException {
        Connection conn = null;
        try {
            conn = ConDB.getConnection();
            try (PreparedStatement query = conn.prepareStatement("DELETE FROM evento WHERE ID = ?")) {
                query.setInt(1, ID);
                return query.executeUpdate() != 0;        
            }
        } finally {
            ConDB.releaseConnection(conn);
        }
    }

    /*
       Recupera un evento dal database in base al suo identificatore.
       @param ID L'identificatore dell'evento da recuperare.
       @return L'evento trovato o null se non esiste alcun evento con l'ID specificato.
       @throws SQLException Se si verifica un errore durante l'interazione con il database.
     */
    public synchronized Evento get(int ID) throws SQLException {
        Connection conn = null;
        try {
            conn = ConDB.getConnection();
            try (PreparedStatement query = conn.prepareStatement("SELECT * FROM evento WHERE ID = ?")) {
                query.setInt(1, ID);
                try (ResultSet rs = query.executeQuery()) {
                    if (rs.next()) {
                        return new Evento(rs);
                    } else {
                        return null;
                    }
                }                
            }
        } finally {
            ConDB.releaseConnection(conn);
        }
    }

    /*
       Recupera tutti gli eventi dal database.
       @return Una collezione di tutti gli eventi.
       @throws SQLException Se si verifica un errore durante l'interazione con il database.
     */
    public synchronized Collection<Evento> getAll() throws SQLException {
        Connection conn = null;
        try {
            conn = ConDB.getConnection();
            try (PreparedStatement query = conn.prepareStatement("SELECT * FROM evento")) {
                try (ResultSet rs = query.executeQuery()) {
                    ArrayList<Evento> prenotazioni = new ArrayList<>();
                    while (rs.next()) {
                        prenotazioni.add(new Evento(rs));
                    }
                    return prenotazioni;
                }                
            }
        } finally {
            ConDB.releaseConnection(conn);
        }
    }



	public synchronized Collection<Evento> getByFilter(LocalDate dataInizio, LocalDate dataFine, String sport, String citta) throws SQLException {
		if (dataInizio == null && dataFine == null && sport == null && citta == null)
			return getAll();

		Connection conn = null;
		try {
			conn = ConDB.getConnection();
			ArrayList<String> queryConcat = new ArrayList<String>();
			ArrayList<Object> filtri = new ArrayList<Object>();
			String queryString = "SELECT * FROM evento WHERE ";

			if (dataInizio != null) {
				queryConcat.add("data_inizio > ?");
				filtri.add(dataInizio);
			}
			if (dataFine != null) {
				queryConcat.add("data_fine < ?");
				filtri.add(dataFine);
			}
			if (sport != null) {
				queryConcat.add("sport = ?");
				filtri.add(sport);
			}
			if (citta != null) {
				queryConcat.add("citta = ?");
				filtri.add(citta);
			}
			
			queryString += queryConcat.get(0);
			for (int i = 1; i < queryConcat.size(); i++) {
				queryString += " AND " + queryConcat.get(i);
			}

			try (PreparedStatement query = conn.prepareStatement(queryString)) {
				for (int i = 0; i < filtri.size(); i++) {
					if (filtri.get(i) instanceof String) {
						query.setString(i, (String)filtri.get(i));
					} else {
						query.setObject(i, filtri.get(i));
					}
				}
				try (ResultSet rs = query.executeQuery()) {
					ArrayList<Evento> eventiFiltrati = new ArrayList<>();
					while (rs.next()) {
						eventiFiltrati.add(new Evento(rs));
					}
					return eventiFiltrati;
				}
			}
		} finally {
			ConDB.releaseConnection(conn);
		}
		
	}
	
    
    
}