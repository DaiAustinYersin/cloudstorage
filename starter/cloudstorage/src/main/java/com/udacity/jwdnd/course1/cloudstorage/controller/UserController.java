package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String getSignUpPage(@ModelAttribute User user, Model model) {

        if (userService.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("messageError", "Username already existed");
        } else {
            int rowAdded = userService.createUser(user);
            if (rowAdded <= 0)
                model.addAttribute("messageError", "There was an error signing you up. Please try again.");
            else
                model.addAttribute("messageSuccess", true);
        }

        return "signup";
    }

}
