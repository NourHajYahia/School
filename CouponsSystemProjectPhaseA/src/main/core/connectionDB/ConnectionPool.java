package main.core.connectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import main.core.exceptions.ConnectionPoolException;

/**
 * Manages and maintains all needed connections for any interaction between coupons system app and the mySQL data base.
 * Connection pool is a container of specified number of connection which can be popped out, pushed back after use
 * and closed all.
 */
public class ConnectionPool {

    private String url = "jdbc:mysql://localhost:3306/coupons_db?serverTimezone=Israel&createDatabaseIfNotExist=true";
    private String user = "root";
    private String password = "n12345";
    private static final int MAX = 5;
    private Set<Connection> connectionsOut = new HashSet<Connection>();
    private Set<Connection> connectionsIn = new HashSet<Connection>();

    private static ConnectionPool instance;

    /**
     * initiates set specified number of SQL connections (MAX final variable- is number of the connections allowed
     * in the pool.
     *
     * @throws ConnectionPoolException - if the attempted request for connection from the data base failed
     */
    private ConnectionPool() throws ConnectionPoolException {
        for (int i = 0; i < MAX; i++) {
            try {
                Connection con = DriverManager.getConnection(url, user, password);
                connectionsIn.add(con);
            } catch (SQLException e) {
                throw new ConnectionPoolException("ConnectionPool Error: failed to initiate,\n" + e.getMessage());
            }
        }
    }

    /**
     * asking for the connection pool instance if already initiated, otherwise initiates it.
     *
     * @return ConnectionPool instance
     * @throws ConnectionPoolException - if initiating attempt failed
     */
    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * pulling out a connection to data base from the pool and saving the reference in the in use connections set
     *
     * @return Connection
     * @throws ConnectionPoolException - if interrupted
     */
    public synchronized Connection getConnection() throws ConnectionPoolException {
        while (connectionsIn.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new ConnectionPoolException("ConnectionPool Error: getConnection is interrupted,\n" + e.getMessage());
            }
        }

        Iterator<Connection> it = connectionsIn.iterator();
        Connection connection = it.next();
        connectionsOut.add(connection);
        it.remove();
        return connection;
    }

    /**
     * pushing in restored connection from outside and removing his existing from the in use connections set
     *
     * @param connection
     * @throws ConnectionPoolException - if connection to restore is not a creation of the pool or null
     */
    public synchronized void restoreConnections(Connection connection) throws ConnectionPoolException {
        if (connection != null && connectionsOut.contains(connection)) {
            connectionsIn.add(connection);
            connectionsOut.remove(connection);
            notify();
        } else {
            throw new ConnectionPoolException("ConnectionPool Error: connection to restore is not a creation of this pool");
        }
    }

    /**
     * closing all connection created by the pool if all returned, otherwise waiting for all to be restored.
     *
     * @throws ConnectionPoolException - if interrupted or failed to close any of the connections
     */
    public synchronized void closeAllConnections() throws ConnectionPoolException {
        while (connectionsIn.size() != MAX) {
            try {
                System.out.println("connection pool is waiting for returns");
                wait();
            } catch (InterruptedException e) {
                throw new ConnectionPoolException("ConnectionPool Error: restoreAllConnections is interrupted,\n" + e.getMessage());
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
            throw new ConnectionPoolException("ConnectionPool Error: failed to close all connections,\n" + e.getMessage());
        }

        System.out.println("connection pool closed successfully");
    }
}
