package org.launchcode.jblog.controllers;

import org.launchcode.jblog.models.Post;
import org.launchcode.jblog.models.Tag;
import org.launchcode.jblog.service.PostService;
import org.launchcode.jblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "")
    public String index(Model model) {
        //display index page with most recent posts, popular tags, and search function
        model.addAttribute("title", "Jblog");
        model.addAttribute("posts", postService.findAll());

        return "index";
    }

    @RequestMapping(value = "{postId}")
    public String viewPost(Model model, @PathVariable int postId) {
        Post thisPost = postService.findById(postId);
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
    public String newPostProcess(Principal principal, Model model, @ModelAttribute(name = "post") @Valid Post newPost, Errors errors) {
        //validate and add post

        if (errors.hasErrors()) {
            model.addAttribute("title", "New Post");
            model.addAttribute(newPost);

            return "post/new";
        }

        else {

            postService.save(principal, newPost);

            return "redirect:/" + newPost.getId();
        }
    }

}