package org.example.socialmediathing.test;

import org.example.socialmediathing.model.Address;
import org.example.socialmediathing.repository.IAddressRepository;
import org.example.socialmediathing.service.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private IAddressRepository addressRepository;
    final Address TEST_ADDRESS = new Address(1L, "Street", "City", "State", "Zipcode", "Country");

    @Test
    public void testGetAllAddresses_returnsAllAddresses() {
        // Mock data
        List<Address> address = new ArrayList<>();
        address.add(new Address(1L, "Street 1", "City 1", "State 1", "Zipcode 1", "Country 1"));
        address.add(new Address(2L, "Street 2", "City 2", "State 2", "Zipcode 2", "Country 2"));

        // Stub the repository method to return the mock data
        when(addressRepository.findAll()).thenReturn(address);

        // Call the service method
        List<Address> result = addressService.getAllAddresses();

        // Verify the result
        assertEquals(address.size(), result.size());
        assertEquals(address.get(0).getStreet(), result.get(0).getStreet());
        assertEquals(address.get(1).getStreet(), result.get(1).getStreet());
    }

    @Test
    public void testGetAllAddresses_fails() {
        // Stub the repository method to throw an exception when called
        given(addressRepository.findAll()).willThrow(new RuntimeException("Failed to retrieve address"));

        // Verify that calling getAllAddresss method throws an exception
        assertThrows(RuntimeException.class, () -> addressService.getAllAddresses());
    }


    @Test
    public void testGetAddressByID_validID_returnsAddress() throws Exception {
        given(addressRepository.findById(TEST_ADDRESS.getId())).willReturn(Optional.of(TEST_ADDRESS));

        Address example = addressService.getAddressById(TEST_ADDRESS.getId());
        assertNotNull(example);
        assertEquals(TEST_ADDRESS.getId(), example.getId());
    }

    @Test
    public void testGetAddressByID_invalidID_fails() {
        given(addressRepository.findById(TEST_ADDRESS.getId())).willReturn(Optional.empty());

        assertThrows(Exception.class, () -> addressService.getAddressById(TEST_ADDRESS.getId()));
    }

    @Test
    public void testAddAddress_Successful() {
        // Stub the repository method to return the saved address
        given(addressRepository.save(TEST_ADDRESS)).willReturn(TEST_ADDRESS);

        // Call the service method
        Address example = addressService.createAddress(TEST_ADDRESS);

        // Verify that the address was saved successfully
        assertNotNull(example);
        assertEquals(TEST_ADDRESS.getId(), example.getId());
        assertEquals(TEST_ADDRESS.getStreet(), example.getStreet());
        assertEquals(TEST_ADDRESS.getCity(), example.getCity());
        assertEquals(TEST_ADDRESS.getState(), example.getState());
        assertEquals(TEST_ADDRESS.getZipCode(), example.getZipCode());
        assertEquals(TEST_ADDRESS.getCountry(), example.getCountry());
    }

    @Test
    public void testAddAddress_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        given(addressRepository.save(TEST_ADDRESS)).willThrow(new RuntimeException("Failed to add address"));

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> addressService.createAddress(TEST_ADDRESS));
    }

    @Test
    public void testDeleteAddress_Successful() {
        // Call the service method
        assertDoesNotThrow(() -> addressService.deleteAddress(TEST_ADDRESS.getId()));

        // Verify that the delete method of the repository was called with the correct addressId
        verify(addressRepository, times(1)).deleteById(TEST_ADDRESS.getId());
    }

    @Test
    public void testDeleteAddress_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        doThrow(new RuntimeException("Failed to delete address")).when(addressRepository).deleteById(TEST_ADDRESS.getId());

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> addressService.deleteAddress(TEST_ADDRESS.getId()));
    }

    @Test
    public void testUpdateAddress_Successful() throws Exception {
        // Mock data
        Address updatedAddress = new Address(TEST_ADDRESS.getId(), "Updated Street", "Updated City", "Updated State", "Updated Zipcode", "Updated Country");

        // Stub the repository method to return the existing address
        when(addressRepository.findById(TEST_ADDRESS.getId())).thenReturn(Optional.of(TEST_ADDRESS));

        // Stub the repository method to return the updated address
        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);

        // Call the service method
        Address result = addressService.updateAddress(TEST_ADDRESS.getId(), updatedAddress);

        // Verify the result
        assertNotNull(result);
        assertEquals(updatedAddress.getId(), result.getId());
        assertEquals(updatedAddress.getStreet(), result.getStreet());
        assertEquals(updatedAddress.getCity(), result.getCity());
        assertEquals(updatedAddress.getState(), result.getState());
        assertEquals(updatedAddress.getZipCode(), result.getZipCode());
        assertEquals(updatedAddress.getCountry(), result.getCountry());
    }

    @Test
    public void testUpdateAddress_Unsuccessful_AddressNotFound() {
        // Mock data
        Address updatedAddress = new Address(TEST_ADDRESS.getId(), "Updated Street", "Updated City", "Updated State", "Updated Zipcode", "Updated Country");

        // Stub the repository method to return an empty optional
        when(addressRepository.findById(TEST_ADDRESS.getId())).thenReturn(Optional.empty());

        // Call the service method and verify that it throws an exception
        assertThrows(Exception.class, () -> addressService.updateAddress(TEST_ADDRESS.getId(), updatedAddress));

        // Verify that the repository method save was not called
        verify(addressRepository, never()).save(any(Address.class));
    }

}

