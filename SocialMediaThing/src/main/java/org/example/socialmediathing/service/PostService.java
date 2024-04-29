package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private IPostRepository postRepository;

    // Implement methods for CRUD operations
}

