package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RolesService;
import ru.kata.spring.boot_security.demo.service.UsersService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UsersService usersService;
    private RolesService rolesService;
    @Autowired
    public AdminController(UsersService usersService, RolesService rolesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
    }

    //Домашняя страница Админа
    @GetMapping
    public String Admin(Model model) {
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", usersService.getAllUsers());
        return "users";
    }

    //Переход на страницу пользователя
    @GetMapping("users/{id}")
    public String showUserId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", usersService.getUserById(id));
        return "userId";
    }

    //Удаление пользователя
    @DeleteMapping("/{id}")
    public String deleteUsersId(@PathVariable("id") Long id) {
        usersService.removeUserById(id);
        return "redirect:/admin/users";
    }

    //Добавление пользователя
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", rolesService.getAllRoles());
        return "newUser";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] roles) {
        user.setRoles(rolesService.getSetOfRoles(roles));
        usersService.addUser(user);
        return "redirect:/admin/users";
    }

    //Редактирование данных пользователя
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", usersService.getUserById(id));
        model.addAttribute("roles", rolesService.getAllRoles());
        return "editUser";
    }

    @PatchMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] roles) {
        user.setRoles(rolesService.getSetOfRoles(roles));
        usersService.updateUser(user);
        return "redirect:/admin/users";
    }
}
