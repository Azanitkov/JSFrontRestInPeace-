package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.userAddDto;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/getAllRoles")
    public ResponseEntity<Set<Role>> getAllRoles(){
        Set<Role> roles = roleService.getAllRole();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @GetMapping("/getCurrentUser")
    public ResponseEntity<User> getCurrentUser (Principal principal){
        User user = userService.getByEmail(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/showAllUsers")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> users =  userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PostMapping("/addUser")
    public void addUser(@RequestBody userAddDto dto){
        userService.save(dto.getUser(),dto.getRolesIds());
    }



    @PutMapping("/editUser")
    public void updateUser(@RequestBody User user){
          roleService.setExistingRoles(user);
        userService.update(user);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        userService.deleteById(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
