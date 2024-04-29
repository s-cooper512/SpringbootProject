package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private ICommentRepository commentRepository;

    // Implement methods for CRUD operations
}

