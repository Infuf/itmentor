package jm.task.core.jdbc.util;

import com.sun.xml.bind.v2.schemagen.xmlschema.TypeHost;
import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {

    private final static Logger logger = Logger.getLogger(Util.class.getName());

    private static final SessionFactory sessionFactory;

    static {
        logger.info("Getting SessionFactory _ Hibernate");
        try {
            Properties properties = new Properties();
            try (var input = Hibernate.class.getClassLoader().getResourceAsStream("hibernate.properties")) {
                if (input != null) {
                    properties.load(input);
                }
            }
            var config = new Configuration();
            config.setProperties(properties);
            config.addAnnotatedClass(User.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex) {

            logger.warning("cannot create SessionFactory");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection connectJDBC() {

        logger.info("Trying to connect DataBase with JDBC");

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "rootroot";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                logger.info("Connection for DataBase is successful");
            }
            return connection;
        } catch (SQLException e) {
            logger.info("Connection for DataBase is FAIL");
            throw new RuntimeException(e);
        }
    }
}
