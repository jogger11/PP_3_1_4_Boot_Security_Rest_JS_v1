package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UsersService {
    List<User> getAllUsers();

    User getUserById(Long id);

    void addUser(User user);

    void removeUserById(Long id);

    void updateUser(User user);

    User getUserByEmail(String email);
}
