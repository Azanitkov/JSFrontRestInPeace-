package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public void save(User user, List<Long> roleIds) {
       Set<Role> roles = getRolesByIds(roleIds);
       user.setRoles(roles);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User updatedUser) {
        User existingUser = userRepository.getOptionalById(updatedUser.getId()).orElseThrow();
        existingUser.setRoles(roleRepository.findByNameIn(updatedUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet())));
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setName(updatedUser.getName());
        existingUser.setSurname(updatedUser.getSurname());
        existingUser.setAge(updatedUser.getAge());
        userRepository.save(existingUser);
    }

    @Override
    public Set<Role> getRolesByIds(List<Long> rolesIds) {
        if (rolesIds == null || rolesIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(roleRepository.findAllById(rolesIds));
    }

    @Override
    public Boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}