package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("\"Create\" test")
    public void createOk() {
        String name = "Ivan";
        String email = "abcd@mail.ru";
        int age = 30;

        service.create(name, email, age);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(name, capturedUser.getName());
        assertEquals(email, capturedUser.getEmail());
        assertEquals(age, capturedUser.getAge());
    }

    @Test
    @DisplayName("\"Get\" test")
    public void getOk() {
        Long id = 1L;
        User user = new User("Ivan", "abcd@mail.ru", 30);
        when(userDao.findById(id)).thenReturn(user);

        User result = service.get(id);

        verify(userDao).findById(id);
        assertEquals("Ivan",result.getName());
    }

    @Test
    @DisplayName("\"GetAll\" test")
    public void getAllOk() {
        List<User> mockUsers = List.of(
                new User("Ivan", "abcd@mail.ru", 30),
                new User("Anna", "dcba@mail.ru", 20)
        );
        when(userDao.findAll()).thenReturn(mockUsers);

        List<User> result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ivan", result.get(0).getName());
        assertEquals("Anna", result.get(1).getName());
    }

    @Test
    @DisplayName("\"Update\" test if ID is not null")
    public void updateNotNullOk() {
        Long id = 0L;
        User oldUser = new User("Ivan", "abcd@mail.ru", 30);
        when(userDao.findById(id)).thenReturn(oldUser);

        String newName = "Anna";
        String newEmail = "dcba@mail.ru";
        int newAge = 20;

        service.update(id, newName, newEmail, newAge);

        verify(userDao).findById(id);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDao).update(captor.capture());
        User updateUser = captor.getValue();

        assertEquals(newName, updateUser.getName());
        assertEquals(newEmail, updateUser.getEmail());
        assertEquals(newAge, updateUser.getAge());
    }

    @Test
    @DisplayName("\"Update\" test if ID is null")
    public void updateNotNullFalse() {
        Long id = 50L;
        when(userDao.findById(id)).thenReturn(null);

        service.update(id, "name", "email", 100);

        verify(userDao).findById(id);
        verify(userDao, never()).update(any());
    }

    @Test
    @DisplayName("\"Delete\" test")
    public void deleteOk() {
        Long id = 1L;

        service.delete(id);

        verify(userDao, times(1)).delete(id);
    }
}
