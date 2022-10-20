package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    private static volatile Util instance;
    private Connection connection;
    private SessionFactory sessionFactory;

    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mySQL://localhost:3306/dbtest";
    private final String USER = "root1";
    private final String PASSWORD = "adminroot_27";
    private final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";

    private Util() {
        //empty private constructor
    }

    public static Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }

        return instance;
    }

    public Connection getConnection() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            System.out.println("Failed to establish database connection!");
        }
        return connection;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                //Hibernate configuration setting
                Configuration config = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, DRIVER);
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USER);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, DIALECT);
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.HBM2DDL_AUTO, "update");
                config.setProperties(properties);

//                // Entity mapping
//                config.addAnnotatedClass(User.class);
//
//                //Hibernate services setup
//                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                        .applySettings(config.getProperties()).build();
//
//                //SessionFactory creation
//                sessionFactory = config.buildSessionFactory(serviceRegistry);

                sessionFactory = config.addAnnotatedClass(User.class)
                        .setProperties(properties)
                        .buildSessionFactory();

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
