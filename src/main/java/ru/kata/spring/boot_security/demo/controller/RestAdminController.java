package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RolesService;
import ru.kata.spring.boot_security.demo.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class RestAdminController {
    private UsersService usersService;
    private RolesService rolesService;

    @Autowired
    public RestAdminController(UsersService usersService, RolesService rolesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
    }

    //Получение данных о всех Users
    @GetMapping("/users")
    public List<User> getUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return rolesService.getAllRoles();
    }

    //Получение данных User по ID
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        User user = usersService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("There is no user with ID = " + id + " in Database.");
        }
        return usersService.getUserById(id);
    }

    //Удаление User
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = usersService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("There is no user with ID = " + id + " in Database.");
        }
        usersService.removeUserById(id);
    }

    //Создание User
    @PostMapping("/users")
    public User creatUser(@RequestBody User user) {
        usersService.addUser(user);
        return user;
    }

    //Редактирование User
    @PatchMapping("/users")
    public void editUser(@RequestBody User user) {
        usersService.updateUser(user);
    }

}
