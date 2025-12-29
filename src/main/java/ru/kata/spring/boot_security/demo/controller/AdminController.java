package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("allRoles", roleService.findAll());
        model.addAttribute("editUser", new User());
        return "users/users";
    }

    @GetMapping("/user")
    public String showUser(@RequestParam(value = "id") Long id, Model model) {
        User user = userService.findOne(id);
        model.addAttribute("user", userService.findOne(id));
        return "users/user";
    }

    @GetMapping("/users")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "users/users";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roleIds") List<Long> roleIds) {
        userService.save(user, roleIds);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String createUpdateForm(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "users/update";
    }


    @PostMapping("/user")
    public String updateUser(@RequestParam("id") Long id,
                             @ModelAttribute("editUser") User user,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        userService.update(id, user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}
