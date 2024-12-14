import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Adapter;
import model.AdapterTest;
import model.ConDB;
import model.Valutazione.ValutazioneBean;
import model.Valutazione.ValutazioneDAO;

class ValutazioneDAOTest extends DataSourceBasedDBTestCase  {
	private DataSource ds;
    private ValutazioneDAO od;

	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:resources/db/init/valutazione.sql'");
        dataSource.setUser("root");
        dataSource.setPassword("veloce123");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("resources/db/init/valutazione.xml"));
	}
	
	 @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }
	
	 @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		  ds = this.getDataSource();
	        od = new ValutazioneDAO(new AdapterTest(ds));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		 super.tearDown();
	}
	


	@Test
	void testSave() throws Exception {
		 ds = this.getDataSource();
	        od = new ValutazioneDAO(new AdapterTest(ds));
		 ITable expectedTable = new FlatXmlDataSetBuilder()
                 .build(ValutazioneDAOTest.class.getClassLoader().getResourceAsStream("resources/db/expected/valutazione.xml"))
                 .getTable("valutazione");
   
	     ValutazioneBean bean = new ValutazioneBean();
	     
	     bean.setEsito(1);
	     bean.setIdEvento(1);
	     bean.setUtenteValutante("utente2");
	     bean.setUtenteValutato("utente1");
	   
	     od.save(bean);
	     
	     IDatabaseTester tester = this.getDatabaseTester();
	     
	     ITable actualTable =  tester.getConnection().createDataSet().getTable("valutazione");    
	     System.out.println(actualTable.getRowCount());
	    
	    Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
			
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testGet() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByUsernameEvent() {
		fail("Not yet implemented");
	}

}
