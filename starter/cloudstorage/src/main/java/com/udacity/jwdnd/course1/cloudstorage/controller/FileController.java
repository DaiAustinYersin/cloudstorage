package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        FileDTO dto = new FileDTO(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), fileUpload.getSize() + "", fileUpload);
        int rowChanged = 0;
        if (dto.getFileId() == null)
            rowChanged = fileService.createFile(dto);
        else
            rowChanged = fileService.updateFile(dto);

        if (rowChanged <= 0)
            model.addAttribute("status", false);
        else
            model.addAttribute("status", true);
        return "/result";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        int rowDeleted = fileService.deleteFileById(id);
        if (rowDeleted <= 0)
            model.addAttribute("status", false);
        else
            model.addAttribute("status", true);

        return "/result";
    }
    
}
