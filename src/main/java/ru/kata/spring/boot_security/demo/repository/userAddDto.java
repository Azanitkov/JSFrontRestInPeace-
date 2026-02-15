package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public class userAddDto {
    private User user;
    private List<Long> rolesIds;

    public User getUser() {
        return user;
    }
    public userAddDto(){}

    public void setUser(User user) {
        this.user = user;
    }

    public List<Long> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(List<Long> rolesIds) {
        this.rolesIds = rolesIds;
    }
}
