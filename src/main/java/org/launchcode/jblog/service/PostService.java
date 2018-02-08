package org.launchcode.jblog.service;

import org.launchcode.jblog.models.Post;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface PostService {

    List<Post> findAll();
    Post findById(int id);
    Post save(Principal principal, Post post);
    Page<Post> getPage(int pageNumber);
    void delete(int id);
    List<Post> searchBySearchTerm(String searchTerm);
    List<Post> searchByFilter(String filter, String searchTerm);
}
