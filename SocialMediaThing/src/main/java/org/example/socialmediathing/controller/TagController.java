package org.example.socialmediathing.controller;

import org.example.socialmediathing.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // Implement REST endpoints for CRUD operations
}

