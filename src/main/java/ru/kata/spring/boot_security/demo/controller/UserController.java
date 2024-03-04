package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UsersService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private UsersService usersService;

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String userPage(Model model, Principal principal) {
        model.addAttribute("user", usersService.getUserByEmail(principal.getName()));
        return "user";
    }
}
