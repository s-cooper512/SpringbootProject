package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.ITagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private ITagRepository tagRepository;

    // Implement methods for CRUD operations
}

