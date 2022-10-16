package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
        //empty constructor
    }

    public void createUsersTable() {

        String createTable = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `id` INT(9) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(64) NOT NULL,\n" +
                "  `lastName` VARCHAR(128) NOT NULL,\n" +
                "  `age` INT(3) NOT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";

        try (Connection con = Util.getInstance().getConnection();
             Statement statement = con.createStatement())
        {
            statement.execute(createTable);
        } catch (SQLException e) {
            System.out.println("Fail to create a new table!");
        }
    }

    public void dropUsersTable() {

        String dropTable = "DROP TABLE IF EXISTS `users`";

        try (Connection con = Util.getInstance().getConnection();
             Statement statement = con.createStatement())
        {
            statement.execute(dropTable);
        } catch (SQLException e) {
            System.out.println("Fail to create a new table!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String saveUser = "INSERT INTO USERS (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection con = Util.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(saveUser))
        {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
            con.commit();
            System.out.println("User с именем " + name + " был добавлен в базу данных.");
        } catch (SQLException e) {
            System.out.println("Fail to save a new User!");
        }

    }

    public void removeUserById(long id) {

        try (Connection con = Util.getInstance().getConnection();
             Statement statement = con.createStatement())
        {
            statement.executeUpdate("DELETE FROM USERS WHERE id= " + id);
            con.commit();
        } catch (SQLException e) {
            System.out.println("Fail to delete user by id");
        }
    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        try (Connection con = Util.getInstance().getConnection();
             Statement statement = con.createStatement();
             ResultSet res = statement.executeQuery("SELECT * FROM users"))
        {
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong(1));
                user.setName(res.getString(2));
                user.setLastName(res.getString(3));
                user.setAge(res.getByte(4));
                list.add(user);
            }
        } catch (Exception e) {
            System.out.println("Fail to get user's list!");
        }
        return list;
    }

    public void cleanUsersTable() {

        try (Connection con = Util.getInstance().getConnection();
             Statement statement = con.createStatement())
        {
            statement.executeUpdate("DELETE FROM USERS");
            con.commit();
        } catch (SQLException e) {
            System.out.println("Fail to clear user table!");
        }
    }
}
