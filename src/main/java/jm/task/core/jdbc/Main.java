package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static UserServiceImpl service = new UserServiceImpl();

    public static void main(String[] args) {
        service.dropUsersTable();

        service.createUsersTable();
        service.saveUser("Алёна", "Филяева", (byte) 22);
        service.saveUser("Анатолий", "Кисилёв", (byte) 44);
        service.saveUser("Виктория", "Сикрет", (byte) 34);
        service.saveUser("Галина", "Медведивна", (byte) 52);
        List<User> users = service.getAllUsers();
        users.forEach(System.out::println);
        service.cleanUsersTable();

    }
}
