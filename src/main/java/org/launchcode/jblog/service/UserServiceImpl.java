package org.launchcode.jblog.service;

import org.launchcode.jblog.dao.RoleDao;
import org.launchcode.jblog.dao.UserDao;
import org.launchcode.jblog.models.Role;
import org.launchcode.jblog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public void addUser(User user) {

        user.getRoles().add(roleDao.findByName("ROLE_USER"));
        user.setDateJoined(new Date());
        userDao.save(user);

    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findById(int id) {
        return userDao.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

}
