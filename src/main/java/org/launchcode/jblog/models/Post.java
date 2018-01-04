package org.launchcode.jblog.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity(name = "Post")
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NotNull
    @Size(min=3, max=15, message = "Post's name must be between 3-15 characters long.")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Please enter a post.")
    @Column(name = "body")
    private String body;

    @Column(name = "date_posted")
    private Date datePosted;

    public String getDatePosted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(datePosted);
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    @ManyToMany
    @JoinTable(name = "posts_tags",
            joinColumns = @JoinColumn(name = "posts_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Collection<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    public Post(String title, String body, List<Tag> tags) {
        this.title = title;
        this.body = body;
        this.tags = tags;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }
}
