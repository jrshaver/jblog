package org.launchcode.jblog.dao;

import org.launchcode.jblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}