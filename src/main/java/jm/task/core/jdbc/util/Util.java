package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static volatile Util instance;
    private Connection connection;
    private final String URL = "jdbc:mySQL://localhost:3306/dbtest";
    private final String USER = "root1";
    private final String PASSWORD = "adminroot_27";


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
}
