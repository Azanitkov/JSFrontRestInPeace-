package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Set;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByRoleName(String name);
    @Query("select r from Role r")
    Set<Role> findAllRoles();

}
