package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.dto.CredentialsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsService {

    private final UserService userService;
    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(UserService userService, CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credentials> getCredentials() {
        return credentialsMapper.findAll(userService.getCurrentUser().getUserId());
    }

    public Integer create(CredentialsDTO dto) {
        return credentialsMapper.insert(
                new Credentials(null, dto.getUrl(), dto.getUsername(),
                        "https://www.youtube.com/", encryptionService.encryptValue(dto.getPassword(), dto.getUrl()),
                        userService.getCurrentUser().getUserId()));
    }

    public Integer updateById(CredentialsDTO dto) {
        Credentials credentials = new Credentials();
        credentials.setCredentialId(dto.getCredentialId());
        credentials.setUrl(dto.getUrl());
        credentials.setUsername(dto.getUsername());
        credentials.setKey("https://www.youtube.com/");
        credentials.setPassword(encryptionService.encryptValue(dto.getPassword(), credentials.getKey()));
        credentials.setUserId(userService.getCurrentUser().getUserId());
        return credentialsMapper.updateById(credentials);
    }

    public Integer deleteById(Integer credentialsId) {
        return credentialsMapper.deleteById(credentialsId);
    }

}
