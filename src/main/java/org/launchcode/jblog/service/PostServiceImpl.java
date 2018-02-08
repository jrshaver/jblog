package org.launchcode.jblog.service;

import org.launchcode.jblog.dao.PostDao;
import org.launchcode.jblog.dao.TagDao;
import org.launchcode.jblog.dao.UserDao;
import org.launchcode.jblog.models.Post;
import org.launchcode.jblog.models.Tag;
import org.launchcode.jblog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return postDao.findAll();
    }

    @Override
    public Post findById(int id) {
        return postDao.findOne(id);
    }

    @Override
    public Post save(Principal principal, Post post) {
        User user = userDao.findByUsername(principal.getName());
        post.setUser(user);
        post.setDatePosted(new Date());

        List<String> tagsStrings = Arrays.asList(post.getTagsString().split(","));
        List<Tag> tags = new ArrayList<>();
        for (String stringTag : tagsStrings) {
            //if tag is an empty string, do not add to db
            if (stringTag.isEmpty()) break;
            //if tag doesn't already exists, add tag to db
            if (!(tagDao.findAll().contains(tagDao.findByName(stringTag)))) {
                Tag tag = new Tag();
                tag.setName(stringTag);
                tagDao.save(tag);
            }
            tags.add(tagDao.findByName(stringTag));
        }
        post.setTags(tags);

        return postDao.save(post);
    }

    @Override
    public Page<Post> getPage(int pageNumber) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, 4, Sort.Direction.DESC, "id");
        return postDao.findAll(pageRequest);
    }

    @Override
    public void delete(int id) {
        postDao.delete(id);
    }

    @Override
    public List<Post> searchBySearchTerm(String searchTerm) {

        List<Post> searchResults = new ArrayList<>();
        //Search titles
        if (!(postDao.findByTitleContaining(searchTerm).isEmpty())) {
            searchResults.addAll(postDao.findByTitleContaining(searchTerm));
        }

        //Search bodies
        else if (!(postDao.findByBodyContaining(searchTerm).isEmpty())) {
            searchResults.addAll(postDao.findByBodyContaining(searchTerm));
        }

        //Search tags
        else if (!(tagDao.findByNameContaining(searchTerm).isEmpty())) {
            for (Tag tag : tagDao.findByNameContaining(searchTerm)) {
                searchResults.addAll(tag.getPosts());
            }
        }

        return searchResults;
    }

    @Override
    public List<Post> searchByFilter(String filter, String searchTerm) {

        List<Post> searchResults = new ArrayList<>();
        switch (filter) {
            case "all":
                searchResults = searchBySearchTerm(searchTerm);
                break;
            case "title":
                searchResults = postDao.findByTitleContaining(searchTerm);
                break;
            case "body":
                searchResults = postDao.findByBodyContaining(searchTerm);
                break;
            case "tag":
                for (Tag tag : tagDao.findByNameContaining(searchTerm)) {
                    searchResults.addAll(tag.getPosts());
                }
                break;
        }


        return searchResults;
    }

}
