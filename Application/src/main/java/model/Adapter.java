package model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class Adapter {
	
	public synchronized Connection getConnection() throws SQLException {
		return ConDB.getConnection();
	}
	
	public static synchronized void releaseConnection(Connection connection) {
		ConDB.releaseConnection(connection);
	}
}
