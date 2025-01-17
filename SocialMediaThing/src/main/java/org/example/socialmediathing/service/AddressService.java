package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.IAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.socialmediathing.model.Address;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private IAddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long addressId) throws Exception {
        return addressRepository.findById(addressId)
                .orElseThrow(Exception::new);
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    public Address updateAddress(Long addressId, Address addressDetails) throws Exception {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(Exception::new);

        address.setStreet(addressDetails.getStreet());
        address.setCity(addressDetails.getCity());
        address.setStreet(addressDetails.getState());
        address.setZipCode(addressDetails.getZipCode());
        address.setCountry(addressDetails.getCountry());

        return addressRepository.save(address);
    }
}

