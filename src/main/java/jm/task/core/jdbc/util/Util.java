package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private final static Logger logger = Logger.getLogger(Util.class.getName());

    public static Connection connect() {

        logger.log(Level.INFO, "Trying to connect DataBase");

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "rootroot";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                logger.log(Level.INFO, "Connection for DataBase is successful");
            }
            return connection;
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Connection for DataBase is FAIL");
            throw new RuntimeException(e);
        }
    }
}
