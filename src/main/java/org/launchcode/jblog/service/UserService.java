package org.launchcode.jblog.service;

import org.launchcode.jblog.models.User;

import java.util.List;

public interface UserService {
    public void addUser(User user);
    public User findByUsername(String username);
    public User findById(int id);
    public List<User> findAll();
}
