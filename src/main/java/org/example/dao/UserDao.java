package org.example.dao;

import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {

    public void save(User user) {
        executeInsideTransaction(session -> session.persist(user));
    }

    public User findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(User.class, id);
        }
    }

    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    public void update(User user) {
        executeInsideTransaction(session -> session.merge(user));
    }

    public void delete(Long id) {
        executeInsideTransaction(session -> {
            User user = session.find(User.class, id);
            if (user != null) session.remove(user);
        });
    }

    private void executeInsideTransaction(HibernateAction action) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            action.execute(session);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface HibernateAction {
        void execute(Session session);
    }
}
