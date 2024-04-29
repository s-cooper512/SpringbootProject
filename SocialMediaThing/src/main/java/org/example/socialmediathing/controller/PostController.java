package org.example.socialmediathing.controller;

import org.example.socialmediathing.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Implement REST endpoints for CRUD operations
}

