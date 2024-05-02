package org.example.socialmediathing.test;

import org.example.socialmediathing.controller.AddressController;
import org.example.socialmediathing.model.Address;
import org.example.socialmediathing.service.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressControllerTest {
    @Autowired
    AddressController addressController;

    @MockBean
    AddressService addressService;

    final Address TEST_ADDRESS = new Address(1L, "Street", "City", "State", "Zipcode", "Country");

    @Test
    public void testGetAllAddresses_Successful () {
        // Mock data
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address(1L, "Street 1", "City 1", "State 1", "Zipcode 1", "Country 1"));
        addresses.add(new Address(2L, "Street 2", "City 2", "State 2", "Zipcode 2", "Country 2"));
        when(addressService.getAllAddresses()).thenReturn(addresses);

        ResponseEntity<List<Address>> result = addressController.getAllAddresses();

        assertNotNull(result);
        assertEquals(addresses.size(), Objects.requireNonNull(result.getBody()).size());
        assertEquals(addresses.get(0).getStreet(), result.getBody().get(0).getStreet());
        assertEquals(addresses.get(1).getStreet(), result.getBody().get(1).getStreet());
    }

    @Test
    public void testGetAllAddresses_Failure () {
        ResponseEntity<List<Address>> responseEntity = addressController.getAllAddresses();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAddressByID_Successful () throws Exception {
        given(addressService.getAddressById(TEST_ADDRESS.getId())).willReturn(TEST_ADDRESS);

        ResponseEntity<Address> example = addressController.getAddressById(TEST_ADDRESS.getId());
        assertNotNull(example.getBody());
        assertEquals(TEST_ADDRESS.getId(), example.getBody().getId());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testGetAddressByID_throwsException () throws Exception {
        ResponseEntity<Address> example = addressController.getAddressById(TEST_ADDRESS.getId());
        assertNull(example.getBody());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testAddAddress_Successful () {
        when(addressService.createAddress(TEST_ADDRESS)).thenReturn(TEST_ADDRESS);

        // Call the controller method
        ResponseEntity<Address> responseEntity = addressController.createAddress(TEST_ADDRESS);

        // Verify the response status code
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify the returned address
        assertEquals(TEST_ADDRESS, responseEntity.getBody());
    }

    @Test
    public void testAddAddress_Unsuccessful() {
        ResponseEntity<Address> responseEntity = addressController.createAddress(TEST_ADDRESS);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateAddress_Successful() throws Exception {
        when(addressService.updateAddress(TEST_ADDRESS.getId(), TEST_ADDRESS)).thenReturn(TEST_ADDRESS);
        ResponseEntity<Address> responseEntity = addressController.updateAddress(TEST_ADDRESS.getId(), TEST_ADDRESS);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TEST_ADDRESS, responseEntity.getBody());
    }

    @Test
    public void testUpdateAddress_Unsuccessful() throws Exception {
        ResponseEntity<Address> responseEntity = addressController.updateAddress(TEST_ADDRESS.getId(), TEST_ADDRESS);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteAddress_Successful() throws Exception {
        ResponseEntity<?> responseEntity = addressController.deleteAddress(TEST_ADDRESS.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteAddress_Unsuccessful() throws Exception {
        ResponseEntity<?> responseEntity = addressController.deleteAddress(TEST_ADDRESS.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
