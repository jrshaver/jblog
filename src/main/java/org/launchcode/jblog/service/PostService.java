package org.launchcode.jblog.service;

import org.launchcode.jblog.models.Post;

import java.security.Principal;
import java.util.List;

public interface PostService {

    List<Post> findAll();
    Post findById(int id);
    Post save(Principal principal, Post post);
}
