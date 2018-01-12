package org.launchcode.jblog.controllers;

import org.launchcode.jblog.models.Post;
import org.launchcode.jblog.models.Tag;
import org.launchcode.jblog.service.PostService;
import org.launchcode.jblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "1") int pageNumber) {
        //display index page with most recent posts, popular tags, and search function
        model.addAttribute("title", "Jblog");
        Page<Post> postsByPage = postService.getPage(pageNumber);

        model.addAttribute("allPosts", postsByPage);
        model.addAttribute("topTags", tagService.findTop5());

        return "index";
    }


    @RequestMapping(value = "post/{postId}")
    public String viewPost(Model model, @PathVariable int postId) {
        Post thisPost = postService.findById(postId);
        model.addAttribute("title", thisPost.getTitle());
        model.addAttribute(thisPost);
        return "post/view";
    }

    @RequestMapping(value = "post/edit/{postId}", method = RequestMethod.GET)
    public String editPostDisplay(Model model, @PathVariable int postId) {

        Post thisPost = postService.findById(postId);
        model.addAttribute("title", "Editing Post:" + thisPost.getTitle());
        model.addAttribute(thisPost);
        String tagList = "";
        for (Tag tag : thisPost.getTags()) {
            tagList += tag.getName() + ",";
        }
        model.addAttribute("tags", tagList);
        return "post/edit";
    }

    @RequestMapping(value = "post/edit/{postId}", method = RequestMethod.POST)
    public String editPostProcess(Principal principal, Model model, @PathVariable int postId,
                                  @ModelAttribute(name = "post") @Valid Post editedPost, Errors errors,
                                  RedirectAttributes redirectAttributes) {

        Post thisPost = postService.findById(postId);
        //If signed in user does not own post
        if (!thisPost.getUser().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("message", "You cannot edit someone else's post!");
            return "redirect:/post/" + postId;
        }

        if (errors.hasErrors()) {
            model.addAttribute("title", "Editing Post:" + thisPost.getTitle());
            model.addAttribute(thisPost);

            return "post/edit/" + postId;
        }

        else {
            thisPost.setTitle(editedPost.getTitle());
            thisPost.setBody(editedPost.getBody());
            thisPost.setTagsString(editedPost.getTagsString());
            postService.save(principal, thisPost);
            redirectAttributes.addFlashAttribute("message", "Post Edited!");

            return "redirect:/post/" + postId;
        }
    }

    @RequestMapping(value = "post/delete/{postId}", method = RequestMethod.POST)
    public String deletePostProcess(Principal principal, @PathVariable int postId, RedirectAttributes redirectAttributes) {

        Post thisPost = postService.findById(postId);
        //If signed in user does not own post
        if (!thisPost.getUser().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("message", "You cannot delete someone else's post!");
            return "redirect:/post/" + postId;
        }
        postService.delete(postId);
        redirectAttributes.addFlashAttribute("message", "Post deleted.");

        return "redirect:/";
    }

    @RequestMapping(value = "tags")
    public String tagList(Model model) {
        //display a list of all tags
        model.addAttribute("title", "Our Tags");
        model.addAttribute("tags", tagService.findAll());

        return "tags/all";
    }

    @RequestMapping(value = "tags/{tagId}")
    public String postsByTag(Model model, @PathVariable int tagId) {
        //display a list of posts by tag
        Tag tag = tagService.findById(tagId);
        model.addAttribute("title", "Posts by Tag: " + tag.getName());
        model.addAttribute("tag", tag);

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
    public String newPostProcess(Principal principal, Model model, @ModelAttribute(name = "post") @Valid Post newPost, Errors errors,
                                 RedirectAttributes redirectAttributes) {
        //validate and add post

        if (errors.hasErrors()) {
            model.addAttribute("title", "New Post");
            model.addAttribute(newPost);

            return "post/new";
        }

        else {

            postService.save(principal, newPost);
            redirectAttributes.addFlashAttribute("message", "Blog posted!");

            return "redirect:/post/" + newPost.getId();
        }
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String searchDisplay(Model model) {

        return "search";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String searchProcess(Model model, @RequestParam String searchTerm) {

        model.addAttribute("title", "Searching for: " + searchTerm);
        model.addAttribute("searchResults", postService.findBySearchTerm(searchTerm));
        return "search";
    }
}