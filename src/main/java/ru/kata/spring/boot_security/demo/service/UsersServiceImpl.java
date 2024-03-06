package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private UserDAO userDAO;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersServiceImpl(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder/*, RoleDAO roleDAO*/) {
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.addUser(user);
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        userDAO.removeUserById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}
