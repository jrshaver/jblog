package org.launchcode.jblog.dao;

import org.launchcode.jblog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}