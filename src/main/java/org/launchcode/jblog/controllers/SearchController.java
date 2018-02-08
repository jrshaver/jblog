package org.launchcode.jblog.controllers;

import org.launchcode.jblog.models.Post;
import org.launchcode.jblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String searchDisplay(Model model) {
        model.addAttribute("title", "Search");

        return "search";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String searchProcess(Model model, @RequestParam String searchTerm) {

        List<Post> searchResults = new ArrayList<>();
        searchResults = postService.searchBySearchTerm(searchTerm);


        model.addAttribute("title", "Searching for: " + searchTerm);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("searchResults", searchResults);
        return "search";
    }

    @RequestMapping(value = "search/filter", method = RequestMethod.POST)
    public String searchProcessWithFilter(Model model, @RequestParam String searchTerm,
                                          @RequestParam String filter,
                                          @RequestParam(required = false) String sortby) {

        List<Post> searchResults = new ArrayList<>();

        searchResults = postService.searchByFilter(filter, searchTerm);

        model.addAttribute("title", "Searching for: " + searchTerm);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("searchResults", searchResults);
        return "search";
    }
}
