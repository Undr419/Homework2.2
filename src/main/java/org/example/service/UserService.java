package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void create(String name, String email, int age) {
        userDao.save(new User(name, email, age));
    }

    public User get(Long id) {
        return userDao.findById(id);
    }

    public List<User> getAll() {
        return userDao.findAll();
    }

    public void update(Long id, String name, String email, int age) {
        User user = userDao.findById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDao.update(user);
        }
    }

    public void delete(Long id) {
        userDao.delete(id);
    }
}
