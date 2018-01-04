package org.launchcode.jblog.service;

import org.launchcode.jblog.models.Post;

public interface PostService {

    Post getPost(int id);

    Post saveNewPost(Post post);
}
