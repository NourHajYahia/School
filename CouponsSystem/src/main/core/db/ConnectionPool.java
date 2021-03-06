package main.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import main.core.exceptions.ConnectionPoolException;

public class ConnectionPool {

	private String url = "jdbc:mysql://localhost:3306/coupons_db?serverTimezone=Israel&createDatabaseIfNotExist=true";
	private String user = "root";
	private String password = "n12345";
	private static final int MAX = 5;
	private Set<Connection> connectionsOut = new HashSet<Connection>();
	private Set<Connection> connectionsIn = new HashSet<Connection>();

	private static ConnectionPool instance;

	private ConnectionPool() throws ConnectionPoolException {
		for (int i = 0; i < MAX; i++) {
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				connectionsIn.add(con);
			} catch (SQLException e) {
				throw new ConnectionPoolException("Connection Error: failed to connect", e);
			}
		}
	}

	public static ConnectionPool getInstance() throws ConnectionPoolException {
		if (instance == null) {
			instance = new ConnectionPool();
			return instance;
		}
		return instance;
	}

	public synchronized Connection getConnection() throws ConnectionPoolException {
		while (connectionsIn.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new ConnectionPoolException("Connection Error: getConnection is interrupted", e);
			}
		}

		Iterator<Connection> it = connectionsIn.iterator();
		Connection connection = it.next();
		connectionsOut.add(connection);
		it.remove();
		return connection;
	}

	public synchronized void restoreConnections(Connection connection) throws ConnectionPoolException {
		if (connection != null && connectionsOut.contains(connection)) {			
			connectionsIn.add(connection);
			connectionsOut.remove(connection);
			notify();
		}else {
			throw new ConnectionPoolException("Connection Error: connection to restore does not fitt the connection pool set");
		}
	}
	
	

	public synchronized void closeAllConnections() throws ConnectionPoolException {
		while (connectionsIn.size() != MAX) {
			try {
				System.out.println("connection pool is waiting for returns");
				wait();
			} catch (InterruptedException e) {
				throw new ConnectionPoolException("Connection Error: restoreAllConnections is interrupted", e);
			}
		}

		try {
			Iterator<Connection> it = connectionsIn.iterator();
			while (it.hasNext()) {
				Connection connection = it.next();
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ConnectionPoolException("Connection Error: failed to close", e);
		}
		
		System.out.println("connection pool closed successfully");

	}

}
