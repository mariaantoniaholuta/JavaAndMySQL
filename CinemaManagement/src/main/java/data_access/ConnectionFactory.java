/**
 * ConnectionFactory is the class we need for the making the connection to our data base
 *
 * @author Holuta Maria
 *
 */
package data_access;

import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "Rootpass107";
    private final static String DB_NAME = "Orders_Management";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    public ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection()  {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error while trying to close the connection");
            }
        }
    }
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error while trying to close the resultSet");
            }
        }
    }
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error while trying to close the statement");
            }
        }
    }

}
