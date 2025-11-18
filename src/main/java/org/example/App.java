package org.example;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting application...");

        if (System.getProperty("DB_URL") == null) {
            System.setProperty("DB_URL", "jdbc:postgresql://localhost:5432/userdb");
        }
        if (System.getProperty("DB_USER") == null) {
            System.setProperty("DB_USER", "postgres");
        }
        if (System.getProperty("DB_PASSWORD") == null) {
            System.setProperty("DB_PASSWORD", "postgres");
        }

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        UserDao userDao = new UserDao(sessionFactory);
        UserService userService = new UserService(userDao);

        System.out.println("Creating user...");
        userService.create("Ivan", "abcd@mail.com", 25);

        System.out.println("Fetching all users...");
        List<User> users = userService.getAll();
        users.forEach(u ->
                System.out.println(u.getId() + " | " + u.getName() + " | " + u.getEmail())
        );

        HibernateUtil.shutdown();
        System.out.println("Application finished successfully.");
    }
}
