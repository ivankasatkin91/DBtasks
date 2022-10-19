package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
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
            try (PreparedStatement ps = con.prepareStatement(createTable)) {
                con.setAutoCommit(false);
                ps.execute();
                con.commit();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to create a table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        String dropTable = "DROP TABLE IF EXISTS `users`";

        try (Connection con = Util.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(dropTable)) {

                con.setAutoCommit(false);
                ps.execute();
                con.commit();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to delete a table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String saveUser = "INSERT INTO `users` (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection con = Util.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(saveUser)) {

                con.setAutoCommit(false);
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);
                ps.executeUpdate();
                con.commit();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to save a user to a table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String removeUser = "DELETE FROM `users` WHERE id= " + id;

        try (Connection con = Util.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(removeUser)) {

                con.setAutoCommit(false);
                ps.executeUpdate();
                con.commit();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to remove user from a table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        String allUsers = "SELECT * FROM `users`";
        List<User> list = new ArrayList<>();

        try (Connection con = Util.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(allUsers);
                 ResultSet res = ps.executeQuery()) {

                con.setAutoCommit(false);
                while (res.next()) {
                    User user = new User();
                    user.setId(res.getLong(1));
                    user.setName(res.getString(2));
                    user.setLastName(res.getString(3));
                    user.setAge(res.getByte(4));
                    list.add(user);
                }
                con.commit();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to select all users from a table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {

        String clearTable = "DELETE FROM `users`";

        try (Connection con = Util.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(clearTable)) {

                con.setAutoCommit(false);
                ps.executeUpdate();
                con.commit();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Fail to clear a table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
