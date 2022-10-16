package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService process = new UserServiceImpl();
        process.createUsersTable();

        //Create 4 random users
        User user1 = new User("Harrison", "Douglas", (byte)36);
        User user2 = new User("Abby", "Read", (byte)25);
        User user3 = new User("Cliff", "Snider", (byte)43);
        User user4 = new User("Mona", "Shortle", (byte)31);

        //add 4 users to the DataBase table;
        process.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        process.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        process.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        process.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        //get all users from DataBase table and print
        List<User> list = new ArrayList<>(process.getAllUsers());
        list.forEach(System.out::println);

        //clear Data Base table;
        process.cleanUsersTable();

        //remove table form DataBase;
        process.dropUsersTable();

    }// end of psvm
}
