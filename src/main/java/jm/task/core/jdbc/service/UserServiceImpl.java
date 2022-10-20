package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    UserDao dao = new UserDaoJDBCImpl();
    UserDao daoHib = new UserDaoHibernateImpl();

    public void createUsersTable() {
        daoHib.createUsersTable();
    }

    public void dropUsersTable() {
        daoHib.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        daoHib.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        daoHib.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return daoHib.getAllUsers();
    }

    public void cleanUsersTable() {
        daoHib.cleanUsersTable();
    }
}
