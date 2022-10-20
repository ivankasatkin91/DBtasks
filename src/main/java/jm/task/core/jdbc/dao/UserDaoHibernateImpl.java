package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getInstance().getSessionFactory();
    }

    @Override
    public void createUsersTable() {

        String createTable = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `id` INT(9) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(64) NOT NULL,\n" +
                "  `lastName` VARCHAR(128) NOT NULL,\n" +
                "  `age` INT(3) NOT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";

        Transaction tn = null;

        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();
            session.createSQLQuery(createTable).executeUpdate();
            tn.commit();

        } catch (HibernateException e) {
            if (tn != null) {
                tn.rollback();
                System.out.println("Fail to create users table!");
            }
        }
    }

    @Override
    public void dropUsersTable() {

        String dropTable = "DROP TABLE IF EXISTS `users`";

        Transaction tn = null;

        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();
            session.createSQLQuery(dropTable).executeUpdate();
            tn.commit();

        } catch (HibernateException e) {
            if (tn != null) {
                tn.rollback();
                System.out.println("Fail to drop users table!");
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction tn = null;

        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tn.commit();
        } catch (HibernateException e) {
            if (tn != null) {
                tn.rollback();
            }
            System.out.println("Fail to save user to users table!");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction tn = null;

        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();
            User toDelete = session.get(User.class, id);
            session.detach(toDelete);
            session.remove(toDelete);
            tn.commit();
        } catch (HibernateException e) {
            if (tn != null) {
                tn.rollback();
            }
            System.out.println("Fail to delete user by id from users table!");
        }
    }

    @Override
    public List<User> getAllUsers() {

        Transaction tn = null;
        List<User> allList = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();
            allList = session.createQuery("from User", User.class)
                    .getResultList();
        } catch (HibernateException e) {
            if (tn != null) {
                tn.rollback();
            }
            System.out.println("Fail to get all users from users table!");
        }
        return allList;
    }

    @Override
    public void cleanUsersTable() {

        Transaction tn = null;

        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();
            session.createQuery("delete from User")
                    .executeUpdate();
            tn.commit();
        } catch (HibernateException e) {
            if (tn != null) {
                tn.rollback();
            }
            System.out.println("Fail to clear users update!");
        }
    }
}

