package org.launchcode.jblog.dao;

import org.launchcode.jblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}