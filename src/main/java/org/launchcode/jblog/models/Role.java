package org.launchcode.jblog.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "Role")
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private int id;

    private String role = "Member";

    @OneToMany
    @JoinColumn(name = "role_id")
    private Collection<User> users = new ArrayList<>();;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                ", role='" + role + '\'' +
                '}';
    }
}
