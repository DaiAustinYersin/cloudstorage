package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute NoteDTO note, Model model) {
        int rowChanged = 0;
        if (note.getNoteId() == null)
            rowChanged = noteService.createNote(note);
        else
            rowChanged = noteService.updateNote(note);

        if (rowChanged <= 0)
            model.addAttribute("status", false);
        else
            model.addAttribute("status", true);
        return "/result";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        int rowDeleted = noteService.deleteNoteById(id);
        if (rowDeleted <= 0)
            model.addAttribute("status", false);
        else
            model.addAttribute("status", true);

        return "/result";
    }

}
