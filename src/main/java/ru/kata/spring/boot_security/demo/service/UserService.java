package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserCreateRequest;
import ru.kata.spring.boot_security.demo.dto.UserUpdateRequest;
import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> findAll();


    void save(UserCreateRequest dto);

    void update(UserUpdateRequest dto);

    Optional<User> findUserById(Long id);


    Boolean existByEmail(String email);

    User getByEmail(String email);

    void deleteById(Long id);


}
