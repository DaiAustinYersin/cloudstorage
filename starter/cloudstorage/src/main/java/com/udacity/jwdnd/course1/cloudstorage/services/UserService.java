package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;
    private final AuthenticationService authenticationService;

    public UserService(UserMapper userMapper, HashService hashService, AuthenticationService authenticationService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.authenticationService = authenticationService;
    }

    public Integer createUser(User user) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(
                new User(null, user.getUsername(),
                        encodedSalt, hashedPassword,
                        user.getFirstName(), user.getLastName()));
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public User getCurrentUser() {
        return getUserByUsername(authenticationService.getAuthentication());
    }

}
