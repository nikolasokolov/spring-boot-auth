package com.starter.authentication.service;

import com.starter.authentication.exception.EmailTakenException;
import com.starter.authentication.exception.UsernameTakenException;
import com.starter.authentication.model.Roles;
import com.starter.authentication.model.User;
import com.starter.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) throws UsernameTakenException, EmailTakenException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameTakenException("Username " + user.getUsername() + " is already taken");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailTakenException("Email " + user.getEmail() + " is already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Roles.SITE_USER);
        userRepository.save(user);
        return user;
    }
}
