package org.example.dao;

import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoIT {

    @Container
    private final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3")
            .withDatabaseName("userdb")
            .withUsername("postgres")
            .withPassword("postgres");

    private UserDao userDao;
    private SessionFactory sessionFactory;

    @BeforeAll
    void setUp() {
        System.setProperty("DB_URL", postgres.getJdbcUrl());
        System.setProperty("DB_USER", postgres.getUsername());
        System.setProperty("DB_PASSWORD", postgres.getPassword());

        sessionFactory = HibernateUtil.getSessionFactory();
        userDao = new UserDao(sessionFactory);
    }

    @Test
    @DisplayName("Integration: Save and read user via hibernate.cfg.xml with placeholders")
    void saveAndReadUser() {
        User user = new User("Ivan", "abcd@mail.com", 25);
        userDao.save(user);

        List<User> users = userDao.findAll();

        assertEquals(1, users.size());
        assertEquals("Ivan", users.get(0).getName());
        assertEquals("abcd@mail.com", users.get(0).getEmail());
    }

    @AfterAll
    void tearDown() {
        HibernateUtil.shutdown();
    }
}
