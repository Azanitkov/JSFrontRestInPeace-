package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getByRoleName(name);
    }

    @Override
    public void setExistingRoles(User user) {
        Set<Role> roles = new HashSet<>(user.getRoles());
        user.removeRoles();
        for (Role role : roles){
            user.addRole(getRoleByName(role.getName()));
        }
    }

    @Override
    public Set<Role> getAllRole() {
        return roleRepository.findAllRoles();
    }

}
