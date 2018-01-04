package org.launchcode.jblog.controllers;

import org.launchcode.jblog.models.Post;
import org.launchcode.jblog.dao.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class PostController {

    @Autowired
    private PostDao postDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        //display index page with most recent posts, popular tags, and search function
        model.addAttribute("title", "Jblog");

        return "index";
    }

    @RequestMapping(value = "{postId}")
    public String viewPost(Model model, @PathVariable int postId) {
        Post thisPost = postDao.findOne(postId);
        model.addAttribute("title", thisPost.getTitle());
        model.addAttribute(thisPost);
        return "post/view";
    }

    @RequestMapping(value = "tags")
    public String tagList(Model model) {
        //display a list of all tags -- a good chance to show off some artsy markup

        return "tags/all";
    }

    @RequestMapping(value = "tags/{tagId}")
    public String postsByTag(Model model) {
        //display a list of posts by tag

        return "tags/posts";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.GET)
    public String newPostDisplay(Model model) {
        //display new post form

        model.addAttribute("title", "New Post");
        model.addAttribute(new Post());

        return "post/new";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.POST)
    public String newPostProcess(Model model, @ModelAttribute("post") @Valid Post newPost, Errors errors) {
        //validate and add post

        if (errors.hasErrors()) {
            model.addAttribute("title", "New Post");
            model.addAttribute(newPost);

            return "post/new";
        }

        else {
            newPost.setDatePosted(new Date());
            postDao.save(newPost);

            return "redirect:/" + newPost.getId();
        }
    }

}