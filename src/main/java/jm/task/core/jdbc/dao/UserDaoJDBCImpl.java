package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        try (Connection con = Util.getInstance().getConnection()) {
            // turn off auto committing
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(createTable)) {
                // create empty table according to prepared statement
                ps.execute();
            } catch (SQLException ex) {
                // terminate transaction and roll back
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to create a new table!");
            }
            // save changes in Data Base
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        String dropTable = "DROP TABLE IF EXISTS `users`";

        try (Connection con = Util.getInstance().getConnection()) {
            // turn off auto committing
            con.setAutoCommit(false);
            // delete table from Data Base
            try (PreparedStatement ps = con.prepareStatement(dropTable)) {
                ps.execute();
            } catch (SQLException ex) {
                // terminate transaction and roll back
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to delete a table!");
            }
            // save changes in Data Base
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String saveUser = "INSERT INTO USERS (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection con = Util.getInstance().getConnection()) {
            // turn off auto committing
            con.setAutoCommit(false);
            // add a new user to the table
            try (PreparedStatement ps = con.prepareStatement(saveUser)) {
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setInt(3, age);
                ps.executeUpdate();
            } catch (SQLException ex) {
                // terminate transaction and roll back
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to add user to the table!");
            }
            // save changes in Data Base
            con.commit();
            con.setAutoCommit(true);
            System.out.println("User с именем " + name + " был добавлен в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String removeUser = "DELETE FROM USERS WHERE id= " + id;

        try (Connection con = Util.getInstance().getConnection()) {
            // turn off auto committing
            con.setAutoCommit(false);
            // remove user by id
            try (PreparedStatement ps = con.prepareStatement(removeUser)) {
                ps.executeUpdate();
            } catch (SQLException ex) {
                // terminate transaction and roll back
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to delete user by!");
            }
            // save changes in Data Base
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        String allUsers = "SELECT * FROM users";
        List<User> list = new ArrayList<>();

        try (Connection con = Util.getInstance().getConnection()) {
            // turn off auto committing
            con.setAutoCommit(false);
            // Acquire data from table to list
            try (PreparedStatement ps = con.prepareStatement(allUsers);
                 ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    User user = new User();
                    user.setId(res.getLong(1));
                    user.setName(res.getString(2));
                    user.setLastName(res.getString(3));
                    user.setAge(res.getByte(4));
                    list.add(user);
                }
            } catch (SQLException ex) {
                // terminate transaction and roll back
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to acquire user's list!");
            }
            //save changes to Data Base
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {

        String clearTable = "DELETE FROM USERS";

        try (Connection con = Util.getInstance().getConnection()) {
            // turn off auto committing
            con.setAutoCommit(false);
            //clear table
            try (PreparedStatement ps = con.prepareStatement(clearTable)) {
                ps.execute();
            } catch (SQLException ex) {
                // terminate transaction and roll back
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to clear user's list!");
            }
            // save changes to Data Base
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
