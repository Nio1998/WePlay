package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * La classe {@code ConDB} gestisce le connessioni al database MySQL per l'applicazione.
 * Implementa un pool di connessioni per ottimizzare l'accesso al database e ridurre 
 * i tempi di creazione delle connessioni.
 * 
 * <p>Il pool di connessioni è implementato utilizzando una lista di connessioni libere.
 * Quando viene richiesta una connessione, se ce n'è una disponibile, viene riutilizzata; 
 * altrimenti, ne viene creata una nuova.</p>
 * 
 * <p>La classe è progettata per essere thread-safe utilizzando metodi sincronizzati 
 * per gestire l'accesso al pool di connessioni.</p>
 * 
 * <p>Prima di utilizzare questa classe, assicurarsi che il driver MySQL JDBC sia incluso nel 
 * classpath del progetto.</p>
 * 
 * @author Carmine15
 * @version 1.0
 */
public class ConDB {

    /** Pool di connessioni libere al database. */
    private static List<Connection> freeDbConnections;

    /**
     * Blocco statico di inizializzazione.
     * <p>Inizializza il pool di connessioni e registra il driver JDBC.</p>
     */
    static {
        freeDbConnections = new LinkedList<Connection>();
        try {
            // Registra il driver JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("DB driver non trovato: " + e.getMessage());
        }
    }

    /**
     * Crea una nuova connessione al database.
     * 
     * @return una nuova connessione al database.
     * @throws SQLException se si verifica un errore durante la connessione.
     */
    private static synchronized Connection createDBConnection() throws SQLException {
        Connection newConnection;
        String ip = "127.0.0.2";
        String port = "3306";
        String db = "WePlay";
        String username = "root";
        String password = "Password01";

        // Crea una connessione al database con i parametri specificati
        newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, username, password);
        newConnection.setAutoCommit(false);
        return newConnection;
    }

    /**
     * Ottiene una connessione dal pool o crea una nuova connessione se il pool è vuoto.
     * 
     * @return una connessione disponibile al database.
     * @throws SQLException se si verifica un errore durante la connessione.
     */
    public static synchronized Connection getConnection() throws SQLException {
        Connection connection;

        if (!freeDbConnections.isEmpty()) {
            // Recupera una connessione dal pool
            connection = freeDbConnections.get(0);
            freeDbConnections.remove(0);

            try {
                // Verifica che la connessione non sia chiusa
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (SQLException e) {
                // Gestisce eventuali errori sulla connessione recuperata
                connection.close();
                connection = getConnection();
            }
        } else {
            // Crea una nuova connessione se il pool è vuoto
            connection = createDBConnection();
        }

        return connection;
    }

    /**
     * Rilascia una connessione e la restituisce al pool di connessioni.
     * 
     * @param connection la connessione da rilasciare.
     */
    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            freeDbConnections.add(connection);
        }
    }
}
