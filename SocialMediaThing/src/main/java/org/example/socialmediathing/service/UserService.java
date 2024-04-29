package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    // Implement methods for CRUD operations
}

