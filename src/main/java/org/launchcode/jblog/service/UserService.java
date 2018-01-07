package org.launchcode.jblog.service;

import org.launchcode.jblog.models.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(int id);
    List<User> findAll();
}
