package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import ru.kata.spring.boot_security.demo.dto.UserCreateRequest;
import ru.kata.spring.boot_security.demo.dto.UserUpdateRequest;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public void save(UserCreateRequest dto) {
        User user = dto.getUser();
       Set<Role> roles = roleService.getRolesByIds(dto.getRolesIds());
       user.setRoles(roles);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(UserUpdateRequest dto) {
        User existingUser = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (dto.getName() != null) existingUser.setName(dto.getName());
        if (dto.getSurname() != null) existingUser.setSurname(dto.getSurname());
        if (dto.getAge() != null) existingUser.setAge(dto.getAge());
        if (dto.getEmail() != null) existingUser.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRolesId() != null) {
            Set<Role> roles = roleService.getRolesByIds(dto.getRolesId());
            existingUser.setRoles(roles);
        }

        userRepository.save(existingUser);
    }

    @Override
    public Optional<User> findUserById(Long id) {
       return userRepository.findById(id);
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