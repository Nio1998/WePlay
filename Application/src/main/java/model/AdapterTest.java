package model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class AdapterTest extends Adapter {
		DataSource _ds;
		public AdapterTest(DataSource ds) {
			_ds = ds;
		}
		
		public synchronized Connection getConnection() throws SQLException {
			return _ds.getConnection();
		}
		
		public static synchronized void releaseConnection(Connection connection) {

		}
	}