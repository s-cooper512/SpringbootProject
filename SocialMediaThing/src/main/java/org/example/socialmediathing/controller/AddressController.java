package org.example.socialmediathing.controller;

import org.example.socialmediathing.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Implement REST endpoints for CRUD operations
}

