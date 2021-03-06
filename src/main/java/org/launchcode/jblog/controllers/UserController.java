package org.launchcode.jblog.controllers;

import org.launchcode.jblog.models.User;
import org.launchcode.jblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "")
    public String index(Model model) {
        //display a list of all users that link to their profile
        model.addAttribute("title", "Our Users");
        model.addAttribute("users", userService.findAll());

        return "user/index";
    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public String userProfileDisplay(Model model, @PathVariable String username) {
        //display a users profile with user info and posts
        User user = userService.findByUsername(username);
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

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String signUpProcess(Model model, @ModelAttribute @Valid User newUser, Errors errors,
                                HttpServletRequest request) {

        //if validation fails
        if (errors.hasErrors()) {
            model.addAttribute("title", "Sign up");
            model.addAttribute(newUser);
            return "user/add";
        }

        //if username exists in db
        if (userService.findByUsername(newUser.getUsername()) != null) {
            model.addAttribute("title", "Sign up");
            model.addAttribute(newUser);
            model.addAttribute("usernameExistsError", "Username already exists!");
            return "user/add";
        }

        //if email exists in db
        if (userService.findByEmail(newUser.getEmail()) != null) {
            model.addAttribute("title", "Sign up");
            model.addAttribute(newUser);
            model.addAttribute("emailExistsError", "Email already exists!");
            return "user/add";
        }

        //add user
        userService.addUser(newUser);

        //log user in

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getVerifyPassword());
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/";

    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String signInDisplay(Model model) {
        //display sign in form
        model.addAttribute("title", "Sign in");
        model.addAttribute(new User());

        return "user/login";
    }

    @RequestMapping(value="logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
