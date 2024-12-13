/*import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import model.evento.Evento;
import model.evento.EventoDao;

public class EventoDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private EventoDao eventoDao;

    @BeforeEach
    public void setUp() throws SQLException {
        // Inizializza i mock
        MockitoAnnotations.openMocks(this);

        // Simula il comportamento della connessione
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        // Crea un'istanza del DAO
        eventoDao = new EventoDao();

        // Imposta il mock per il ResultSet
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void testGetEvento() throws SQLException {
        // Dati fittizi per il test
        int eventoId = 21;
        String titolo = "Evento di Test";
        LocalDate dataInizio = LocalDate.of(2024, 12, 15);
        LocalTime oraInizio = LocalTime.of(10, 00, 00);
        Double prezzo = 25.0;
        String sport = "Calcio";
        String indirizzo = "Via vattelaP esca 10";
        int massimo_di_partecipanti = 22;
        String citta = "BHo";
        String stato = "non iniziato";
        
        

        // Imposta il comportamento del ResultSet
        when(mockResultSet.next()).thenReturn(true); // Simula che ci sia un record
        when(mockResultSet.getInt("ID")).thenReturn(eventoId);
        when(mockResultSet.getString("titolo")).thenReturn(titolo);
        when(mockResultSet.getObject("data_inizio", LocalDate.class)).thenReturn(dataInizio);
        when(mockResultSet.getObject("ora_inizio", LocalTime.class)).thenReturn(oraInizio);
        when(mockResultSet.getDouble("prezzo")).thenReturn(prezzo);
        when(mockResultSet.getString("sport")).thenReturn(sport);
        when(mockResultSet.getString("indirizzo")).thenReturn(indirizzo);
        when(mockResultSet.getInt("massimo_di_partecipanti")).thenReturn(massimo_di_partecipanti);
        when(mockResultSet.getString("citta")).thenReturn(citta);
        when(mockResultSet.getString("stato")).thenReturn(stato);
        // Esegui il test
        Evento evento = new Evento (eventoId,dataInizio, oraInizio, prezzo, sport, titolo, indirizzo, massimo_di_partecipanti, citta, stato );
        eventoDao.save(evento);

        // Verifica che i dati recuperati siano corretti
        assertNotNull(evento);
        assertEquals(eventoId, evento.getID());
        assertEquals(titolo, evento.getTitolo());
        assertEquals(dataInizio, evento.getData_inizio());
        assertEquals(oraInizio, evento.getOra_inizio());
        assertEquals(prezzo, evento.getPrezzo());
        assertEquals(sport, evento.getSport()); 
        assertEquals(indirizzo, evento.getIndirizzo());
        assertEquals(massimo_di_partecipanti, evento.getMassimo_di_partecipanti());
        assertEquals(citta, evento.getCitta());
        assertEquals(stato, evento.getStato());
    }
}*/
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import model.evento.Evento;
import model.evento.EventoDao;
import model.ConDB;

public class EventoDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private EventoDao eventoDao;

    @BeforeEach
    public void setUp() throws SQLException {
        // Inizializza i mock
        MockitoAnnotations.openMocks(this);

        // Mock del metodo statico ConDB.getConnection()
        try (MockedStatic<ConDB> mockedConDB = mockStatic(ConDB.class)) {
            mockedConDB.when(ConDB::getConnection).thenReturn(mockConnection);

            // Simula il comportamento della connessione
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

            // Crea un'istanza del DAO
            eventoDao = new EventoDao();

            // Imposta il mock per il ResultSet
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        }

        
    }
    @Test
    public void testSaveEvento() throws SQLException {
        // Dati fittizi per il test
        LocalDate dataInizio = LocalDate.of(2024, 12, 15);
        LocalTime oraInizio = LocalTime.of(10, 00, 00);
        Double prezzo = 25.0;
        String sport = "Calcio";
        String titolo = "Evento di Test";
        String indirizzo = "Via vattelaP esca 10";
        int massimo_di_partecipanti = 22;
        String citta = "BHo";
        String stato = "non iniziato";

        // Crea l'evento
        Evento evento = new Evento(0, dataInizio, oraInizio, prezzo, sport, titolo, indirizzo, massimo_di_partecipanti, citta, stato);

        // Esegui il test (invoca il metodo save)
        eventoDao.save(evento);

        // Verifica che il metodo executeUpdate sia stato chiamato
        verify(mockStatement, times(1)).executeUpdate();

        // Verifica che i parametri siano stati impostati correttamente nel PreparedStatement
        verify(mockStatement).setDate(1, java.sql.Date.valueOf(dataInizio));
        verify(mockStatement).setTime(2, java.sql.Time.valueOf(oraInizio));
        verify(mockStatement).setDouble(3, prezzo);
        verify(mockStatement).setString(4, sport);
        verify(mockStatement).setString(5, titolo);
        verify(mockStatement).setString(6, indirizzo);
        verify(mockStatement).setInt(7, massimo_di_partecipanti);
        verify(mockStatement).setString(8, citta);
        verify(mockStatement).setString(9, stato);
        assertNotNull(evento);
        assertEquals(titolo, evento.getTitolo());
        assertEquals(dataInizio, evento.getData_inizio());
        assertEquals(oraInizio, evento.getOra_inizio());
        assertEquals(prezzo, evento.getPrezzo());
        assertEquals(sport, evento.getSport()); 
        assertEquals(indirizzo, evento.getIndirizzo());
        assertEquals(massimo_di_partecipanti, evento.getMassimo_di_partecipanti());
        assertEquals(citta, evento.getCitta());
        assertEquals(stato, evento.getStato());
    }
}

