package org.example.socialmediathing.test;

import org.example.socialmediathing.model.User;
import org.example.socialmediathing.repository.IUserRepository;
import org.example.socialmediathing.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private IUserRepository userRepository;
    final User TEST_USER = new User(1L, "Username", "Email", "Password", new Date(Calendar.DATE), "URL", "Bio");

    @Test
    public void testGetAllUsers_returnsAllUsers() {
        // Mock data
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "Username 1", "Email 1", "Password 1", new Date(Calendar.DATE), "URL 1", "Bio 1"));
        users.add(new User(2L, "Username 2", "Email 2", "Password 2", new Date(Calendar.DATE), "URL 2", "Bio 2"));

        // Stub the repository method to return the mock data
        when(userRepository.findAll()).thenReturn(users);

        // Call the service method
        List<User> result = userService.getAllUsers();

        // Verify the result
        assertEquals(users.size(), result.size());
        assertEquals(users.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(users.get(1).getUsername(), result.get(1).getUsername());
    }

    @Test
    public void testGetAllUsers_fails() {
        // Stub the repository method to throw an exception when called
        given(userRepository.findAll()).willThrow(new RuntimeException("Failed to retrieve users"));

        // Verify that calling getAllUsers method throws an exception
        assertThrows(RuntimeException.class, () -> userService.getAllUsers());
    }


    @Test
    public void testGetUserByID_validID_returnsUser() throws Exception {
        given(userRepository.findById(TEST_USER.getId())).willReturn(Optional.of(TEST_USER));

        User example = userService.getUserById(TEST_USER.getId());
        assertNotNull(example);
        assertEquals(TEST_USER.getId(), example.getId());
    }

    @Test
    public void testGetUserByID_invalidID_fails() {
        given(userRepository.findById(TEST_USER.getId())).willReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.getUserById(TEST_USER.getId()));
    }

    @Test
    public void testAddUser_Successful() {
        // Stub the repository method to return the saved user
        given(userRepository.save(TEST_USER)).willReturn(TEST_USER);

        // Call the service method
        User example = userService.addUser(TEST_USER);

        // Verify that the user was saved successfully
        assertNotNull(example);
        assertEquals(TEST_USER.getId(), example.getId());
        assertEquals(TEST_USER.getUsername(), example.getUsername());
        assertEquals(TEST_USER.getEmail(), example.getEmail());
        assertEquals(TEST_USER.getPassword(), example.getPassword());
        assertEquals(TEST_USER.getRegistrationDate(), example.getRegistrationDate());
        assertEquals(TEST_USER.getProfilePictureUrl(), example.getProfilePictureUrl());
        assertEquals(TEST_USER.getBio(), example.getBio());
    }

    @Test
    public void testAddUser_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        given(userRepository.save(TEST_USER)).willThrow(new RuntimeException("Failed to add user"));

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> userService.addUser(TEST_USER));
    }

    @Test
    public void testDeleteUser_Successful() {
        // Call the service method
        assertDoesNotThrow(() -> userService.deleteUser(TEST_USER.getId()));

        // Verify that the delete method of the repository was called with the correct userId
        verify(userRepository, times(1)).deleteById(TEST_USER.getId());
    }

    @Test
    public void testDeleteUser_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        doThrow(new RuntimeException("Failed to delete user")).when(userRepository).deleteById(TEST_USER.getId());

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> userService.deleteUser(TEST_USER.getId()));
    }

    @Test
    public void testUpdateUser_Successful() throws Exception {
        // Mock data
        User updatedUser = new User(TEST_USER.getId(), "Updated Username", "Updated Email", "Updated Password", new Date(Calendar.DATE), "Updated URL", "Updated Bio");

        // Stub the repository method to return the existing user
        when(userRepository.findById(TEST_USER.getId())).thenReturn(Optional.of(TEST_USER));

        // Stub the repository method to return the updated user
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Call the service method
        User result = userService.updateUser(TEST_USER.getId(), updatedUser);

        // Verify the result
        assertNotNull(result);
        assertEquals(updatedUser.getId(), result.getId());
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        assertEquals(updatedUser.getRegistrationDate(), result.getRegistrationDate());
        assertEquals(updatedUser.getProfilePictureUrl(), result.getProfilePictureUrl());
        assertEquals(updatedUser.getBio(), result.getBio());
    }

    @Test
    public void testUpdateUser_Unsuccessful_UserNotFound() {
        // Mock data
        User updatedUser = new User(TEST_USER.getId(), "Updated Username", "Updated Email", "Updated Password", new Date(Calendar.DATE), "Updated URL", "Updated Bio");

        // Stub the repository method to return an empty optional
        when(userRepository.findById(TEST_USER.getId())).thenReturn(Optional.empty());

        // Call the service method and verify that it throws an exception
        assertThrows(Exception.class, () -> userService.updateUser(TEST_USER.getId(), updatedUser));

        // Verify that the repository method save was not called
        verify(userRepository, never()).save(any(User.class));
    }

}

