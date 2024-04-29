package org.example.socialmediathing.controller;

import org.example.socialmediathing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Implement REST endpoints for CRUD operations
}

