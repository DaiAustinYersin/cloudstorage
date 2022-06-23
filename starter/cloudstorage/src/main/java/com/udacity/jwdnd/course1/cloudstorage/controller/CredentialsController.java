package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.dto.CredentialsDTO;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

    private final CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping("/save")
    public String save(CredentialsDTO dto, Model model) {
        int rowChanged = 0;
        if (dto.getCredentialId() == null)
            rowChanged = credentialsService.create(dto);
        else
            rowChanged = credentialsService.updateById(dto);
        if (rowChanged <= 0)
            model.addAttribute("status", false);
        else
            model.addAttribute("status", true);
        return "result";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        int rowDeleted = credentialsService.deleteById(id);
        if (rowDeleted <= 0)
            model.addAttribute("status", false);
        else
            model.addAttribute("status", true);

        return "/result";
    }

}
