package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.IAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private IAddressRepository addressRepository;

    // Implement methods for CRUD operations
}

