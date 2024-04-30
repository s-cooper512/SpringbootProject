package org.example.socialmediathing.test;

import org.example.socialmediathing.model.Address;
import org.example.socialmediathing.service.AddressService;
import org.example.socialmediathing.repository.IAddressRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {

    @Mock
    private IAddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @org.junit.Test
    @Test
    public void testGetAllAddresses() {
        // Mock data
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address(1L, "123 Main St", "City", "State", "12345", "Country"));
        addresses.add(new Address(2L, "456 Elm St", "City", "State", "54321", "Country"));

        // Stubbing repository method
        Mockito.when(addressRepository.findAll()).thenReturn(addresses);

        // Call service method
        List<Address> result = addressService.getAllAddresses();

        // Assertions
        assertEquals(2, result.size());
    }

    @org.junit.Test
    @Test
    public void testGetAddressById() throws Exception {
        // Mock data
        long addressId = 1L;
        Address address = new Address(addressId, "123 Main St", "City", "State", "12345", "Country");

        // Stubbing repository method
        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        // Call service method
        Address result = addressService.getAddressById(addressId);

        // Assertions
        assertNotNull(result);
        assertEquals(Optional.of(addressId), Optional.of(result.getID()));
        assertEquals(address.getStreet(), result.getStreet());
    }

    @org.junit.Test
    @Test
    public void testCreateAddress() {
        // Mock data
        Address address = new Address(1L, "123 Main St", "City", "State", "12345", "Country");

        // Stubbing repository method
        Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);

        // Call service method
        Address result = addressService.createAddress(address);

        // Assertions
        assertNotNull(result);
        assertEquals(address.getStreet(), result.getStreet());
    }

    @org.junit.Test
    @Test
    public void testDeleteAddress() throws Exception {
        // Mock data
        long addressId = 1L;

        // Stubbing repository method
        Mockito.doNothing().when(addressRepository).deleteById(addressId);

        // Call service method
        addressService.deleteAddress(addressId);

        // Verify that deleteById method was called
        Mockito.verify(addressRepository, Mockito.times(1)).deleteById(addressId);
    }

    @org.junit.Test
    @Test
    public void testUpdateAddress() throws Exception {
        // Mock data
        long addressId = 1L;
        Address existingAddress = new Address(addressId, "123 Main St", "City", "State", "12345", "Country");
        Address updatedAddress = new Address(addressId, "456 New St", "New City", "New State", "67890", "New Country");

        // Stubbing repository method
        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.of(existingAddress));
        Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(updatedAddress);

        // Call service method
        Address result = addressService.updateAddress(addressId, updatedAddress);

        // Assertions
        assertNotNull(result);
        assertEquals(updatedAddress.getStreet(), result.getStreet());
    }
}

