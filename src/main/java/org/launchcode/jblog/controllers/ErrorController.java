package org.launchcode.jblog.controllers;

import org.launchcode.jblog.service.PostService;
import org.launchcode.jblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ErrorController {

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @ExceptionHandler
    public String handleGlobalEx(RedirectAttributes redirectAttributes,
                                 Exception ex) {

        redirectAttributes.addFlashAttribute("message", "Page Not Found.");
        return "redirect:/";
    }
}
