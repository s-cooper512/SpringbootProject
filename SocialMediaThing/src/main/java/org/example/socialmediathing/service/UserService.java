package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.socialmediathing.model.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(Exception::new);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User updateUser(Long userId, User user) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(Exception::new);

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRegistrationDate(user.getRegistrationDate());
        existingUser.setProfilePictureUrl(user.getProfilePictureUrl());
        existingUser.setBio(user.getBio());
        existingUser.setPosts(user.getPosts());
        existingUser.setAddress(user.getAddress());

        return userRepository.save(existingUser);
    }
}

