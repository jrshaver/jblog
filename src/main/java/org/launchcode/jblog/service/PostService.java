package org.launchcode.jblog.service;

import org.launchcode.jblog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public interface PostService {

    List<Post> findAll();
    Post findById(int id);
    Post save(Principal principal, Post post);
    Page<Post> getPage(int pageNumber);
    void delete(int id);
    List<Post> findBySearchTerm(String searchTerm);
}
