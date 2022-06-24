package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.dto.FileDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final UserService userService;
    private final FileMapper fileMapper;

    public FileService(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles() {
        return fileMapper.findAll(userService.getCurrentUser().getUserId());
    }

    public Integer createFile(FileDTO dto) throws IOException {
        System.out.println(dto.getFileName() + dto.getContentType() +
                        dto.getFileSize() + userService.getCurrentUser().getUserId());
        return fileMapper.insert(
                new File(null, dto.getFileName(), dto.getContentType(), dto.getFileSize(),
                        userService.getCurrentUser().getUserId(), dto.getFileData().getBytes())
        );
    }

    public Integer updateFile(FileDTO dto) throws IOException {
        return fileMapper.updateById(
                new File(dto.getFileId(), dto.getFileName(), dto.getContentType(), dto.getFileSize(),
                        userService.getCurrentUser().getUserId(), dto.getFileData().getBytes())
        );
    }

    public int deleteFileById(Integer id) {
        return fileMapper.deleteById(id);
    }

}
