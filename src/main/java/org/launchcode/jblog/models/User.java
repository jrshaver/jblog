package org.launchcode.jblog.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Size(min = 5, max = 15)
    @NotNull
    @Pattern(regexp = "^[A-Za-z]*$", message = "Username must only contain alphabetic characters.")
    @Column(name = "username")
    private String username;

    @Email
    @Column(name = "email")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull(message = "Passwords do not match.")
    @Transient
    private String verifyPassword;

    @NotNull
    private boolean enabled;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne
    private Role role;

    public User() {
    }

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    private void checkPassword() {

//        if (password!=null && verifyPassword!=null && !(BCrypt.checkpw(verifyPassword, password))) {
//            verifyPassword=null;
//        }
    }

    private Date dateJoined;

    public String getDateJoined() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(dateJoined);
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        checkPassword();
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
        checkPassword();
    }

    public int getId() {
        return id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
