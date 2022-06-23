package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.dto.NoteDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper, AuthenticationService authenticationService) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }

    public List<Note> getNoteList(Integer userId) {
        return noteMapper.findAll(userId);
    }

    public Integer createNote(NoteDTO note) {
        return noteMapper.insert(new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(),
                userMapper.getUserByUsername(authenticationService.getAuthentication()).getUserId()));
    }

    public Integer updateNote(NoteDTO note) {
        return noteMapper.updateById(
                new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(),
                        userMapper.getUserByUsername(authenticationService.getAuthentication()).getUserId()));
    }

    public Integer deleteNoteById(Integer noteId) {
        return noteMapper.deleteById(noteId);
    }

}
