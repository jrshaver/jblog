package org.launchcode.jblog.service;

import org.launchcode.jblog.dao.PostDao;
import org.launchcode.jblog.dao.TagDao;
import org.launchcode.jblog.dao.UserDao;
import org.launchcode.jblog.models.Post;
import org.launchcode.jblog.models.Tag;
import org.launchcode.jblog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Post> findAll() {
        return this.postDao.findAll();
    }

    @Override
    public Post findById(int id) {
        return this.postDao.findOne(id);
    }

    @Override
    public Post save(Principal principal, Post post) {
        User user = userDao.findByUsername(principal.getName());
        post.setUser(user);
        post.setDatePosted(new Date());

        List<String> tagsStrings = Arrays.asList(post.getTagsString().split(","));
        List<Tag> tags = new ArrayList<>();
        for (String stringTag : tagsStrings) {
            //if tag doesn't already exists, add tag to db
            if (!(tagDao.findAll().contains(tagDao.findByName(stringTag)))) {
                Tag tag = new Tag();
                tag.setName(stringTag);
                tagDao.save(tag);
            }
            tags.add(tagDao.findByName(stringTag));
        }
        post.setTags(tags);

        return this.postDao.save(post);
    }

}
