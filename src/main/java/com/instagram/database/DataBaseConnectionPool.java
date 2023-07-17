package com.instagram.database;

import com.instagram.InstagramActivator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * <p>
 * Connects with database to store user information
 * </p>
 *
 * @author Arun
 * @version 1.1
 */
public class  DataBaseConnectionPool extends InstagramActivator {

    private static final String SQL_URL = PROPERTIES.getProperty("db_url");
    private static final String USER_NAME = PROPERTIES.getProperty("db_name");
    private static final String PASSWORD = PROPERTIES.getProperty("db_password");
    private static final Integer MAX_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;
    private static DataBaseConnectionPool connectionPool;

    private DataBaseConnectionPool() {
        pool = new ArrayBlockingQueue<>(MAX_POOL_SIZE);

        initializePool();
    }

    /**
     * <p>
     * Gets the object of the connection pool class
     * </p>
     *
     * @return The database connection object
     */
    public static DataBaseConnectionPool getInstance() {
            return null == connectionPool ? connectionPool = new DataBaseConnectionPool() : connectionPool;
    }

    /**
     * <p>
     * Gets the maximum collection of connection at a time
     * </p>
     */
    private static void initializePool() {
        try {

            for (int i = 0; i < MAX_POOL_SIZE; i++) {
                final Connection connection = getConnection();

                if (null != connection) {
                    pool.add(connection);
                }
            }
        } catch (final SQLException message) {
            message.printStackTrace();
        }
    }

    /**
     * <p>
     *  Gets the connection with database
     * </p>
     *
     * @return The connection of database
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SQL_URL, USER_NAME, PASSWORD);
    }

    /**
     * <p>
     * Gets the connection object from the collection
     * </p>
     *
     * @return The connection object of database
     * @throws InterruptedException
     */
    public Connection get() throws InterruptedException {
        return pool.take();
    }

    /**
     * <p>
     * Releases the connection to the collection
     * </p>
     *
     * @param connection Represents connection object
     */
    public void releaseConnection(final Connection connection) {
        if (null != connection) {
            pool.add(connection);
        }
    }

    /**
     * <p>
     * Closes the connection with database
     * </p>
     *
     * @throws SQLException
     */
    public void closeConnectionPool() {
        for (final Connection connection : pool) {

            try {
                connection.close();
            } catch (SQLException message) {
                System.out.println(message.getMessage());
            }
        }
    }
}
