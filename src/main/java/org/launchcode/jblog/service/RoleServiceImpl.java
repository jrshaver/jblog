package org.launchcode.jblog.service;

import org.launchcode.jblog.dao.RoleDao;
import org.launchcode.jblog.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role getRole(int id) {
        return roleDao.findOne(id);
    }

}
