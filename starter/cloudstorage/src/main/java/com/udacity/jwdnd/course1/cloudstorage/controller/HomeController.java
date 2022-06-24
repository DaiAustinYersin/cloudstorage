package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final CredentialsService credentialsService;
    private final FileService fileService;

    public HomeController(NoteService noteService, UserService userService, CredentialsService credentialsService, FileService fileService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialsService = credentialsService;
        this.fileService = fileService;
    }

    @RequestMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("notes", noteService.getNoteList(userService.getCurrentUser().getUserId()));
        model.addAttribute("credentials", credentialsService.getCredentials());
        model.addAttribute("files", fileService.getFiles());
        return "home";
    }

}
