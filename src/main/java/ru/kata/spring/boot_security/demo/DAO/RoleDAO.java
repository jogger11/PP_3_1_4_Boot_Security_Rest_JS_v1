package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDAO {
    Role getRoleByName(String name);

    List<Role> getAllRoles();

    void add(Role role);

    void edit(Role role);

    Role getRoleById(Long id);
}
