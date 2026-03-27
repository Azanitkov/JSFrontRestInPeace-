package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserCreateRequest;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class Init {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public Init(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    private void init() {
        if (!roleRepository.existsById(1L))
            roleRepository.save(new Role(1L, "ROLE_ADMIN"));
        if (!roleRepository.existsById(2L))
            roleRepository.save(new Role(2L, "ROLE_USER"));

        if (!userService.existByEmail("admin@mail.ru")) {
            UserCreateRequest adminDto = new UserCreateRequest();
            User admin = new User("admin", "admin", 33, "admin@mail.ru", "admin");
            List<Long> adminRoleIds = List.of(1L, 2L);
            adminDto.setUser(admin);
            adminDto.setRolesIds(List.of(1L, 2L));
        }

        if (!userService.existByEmail("user@mail.ru")) {
            UserCreateRequest userDto = new UserCreateRequest();
            User user = new User("user", "user", 25, "user@mail.ru", "user");
            List<Long> userRoleIds = List.of(2L);
            userDto.setUser(user);
            userDto.setRolesIds(List.of(2L));
        }
    }
}