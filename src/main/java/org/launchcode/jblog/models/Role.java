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

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

}