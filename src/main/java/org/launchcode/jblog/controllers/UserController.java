package org.launchcode.jblog.controllers;

import org.launchcode.jblog.models.User;
import org.launchcode.jblog.dao.UserDao;
import org.launchcode.jblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "")
    public String index(Model model) {
        //display a list of all users that link to their profile
        model.addAttribute("title", "Our Users");
        model.addAttribute("users", userService.findAll());

        return "user/index";
    }

    @RequestMapping(value = "/{userId}")
    public String userProfile(Model model, @PathVariable int userId) {
        //display a users profile with user info and posts
        User user = userService.findById(userId);
        model.addAttribute("title", user.getUsername());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String signUpDisplay(Model model) {
        //display form to register a new account
        model.addAttribute("title", "Sign up");
        model.addAttribute(new User());

        return "user/add";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String signUpProcess(Model model, @ModelAttribute @Valid User newUser, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Sign up");
            model.addAttribute(newUser);
            return "user/add";
        }

        else {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setDateJoined(new Date());
            userService.addUser(newUser);

            //Start session
            model.addAttribute("title", "Jblog");
            return "redirect:/";
        }

    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String signInDisplay(Model model) {
        //display sign in form
        model.addAttribute("title", "Sign in");
        model.addAttribute(new User());

        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String signInProcess(@RequestParam String username, @RequestParam String password) {



        return "redirect:/";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

}
