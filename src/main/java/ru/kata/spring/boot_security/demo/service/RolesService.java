package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RolesService {
    Role getRoleByName(String name);

    List<Role> getAllRoles();

    Set<Role> getSetOfRoles(String[] roleNames);

    void add(Role role);

    void edit(Role role);

    Role getById(Long id);
}
