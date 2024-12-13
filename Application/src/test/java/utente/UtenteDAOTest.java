package utente;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import model.ConDB;
import model.utente.UtenteBean;
import model.utente.UtenteDAO;

class UtenteDAOTest {
	//Mock creation
	/*
	Connection connectionMock = mock(Connection.class);
    PreparedStatement preparedStatementMock = mock(PreparedStatement.class);*/
    
    /*@Mock
    ConDB mockConDB;*/
    @Mock
    DataSource mockDataSource;
    
    @Mock
    ResultSet mockResultSet;
    
	/*AccertamentoProfessionistaServlet servletMock = mock(AccertamentoProfessionistaServlet.class);
	GestioneAccreditamentoService accrServiceMock = mock(GestioneAccreditamentoServiceImpl.class);
	GestioneUtenzaService userServiceMock = mock(GestioneUtenzaServiceImpl.class);
	Utente utenteMock = mock(Utente.class);
	Accreditamento accrMock = mock(Accreditamento.class);
	RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);*/

	@Test
	void testSave() throws SQLException {
		
		Connection mockConn = mock(Connection.class);
		PreparedStatement mockPreparedStmnt = mock(PreparedStatement.class);

		try (MockedStatic mockConDB = mockStatic(ConDB.class)) {
			 mockConDB.when(ConDB::getConnection).thenReturn(mockConn);
			 when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
			 
			 UtenteDAO dao = new UtenteDAO();
			 
			 dao.save(new UtenteBean("asd", "asdsd", "asdasdasd", LocalDate.now(), "", "", 0, false, false, LocalDateTime.now(), 0, 0, 0));
			 
			 //verify(mockConDB, times(1)).getConnection();
			 verify(mockConn, times(2)).prepareStatement(anyString());
		}
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByUsername() {
		fail("Not yet implemented");
	}

	@Test
	void testIs_organizzatore() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByEmail() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllUtenti() {
		fail("Not yet implemented");
	}

	@Test
	void testAggiornaTimeout() {
		fail("Not yet implemented");
	}

}
