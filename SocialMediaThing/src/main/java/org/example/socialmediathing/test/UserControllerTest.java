package org.example.socialmediathing.test;

import org.example.socialmediathing.controller.UserController;
import org.example.socialmediathing.model.User;
import org.example.socialmediathing.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    UserController userController;

    @MockBean
    UserService userService;

    final User TEST_USER = new User(1L, "Username", "Email", "Password", new Date(Calendar.DATE), "URL", "Bio");

    @Test
    public void testGetAllUsers_Successful () {
        // Mock data
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "Username 1", "Email 1", "Password 1", new Date(Calendar.DATE), "URL 1", "Bio 1"));
        users.add(new User(2L, "Username 2", "Email 2", "Password 2", new Date(Calendar.DATE), "URL 2", "Bio 2"));

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> result = userController.getAllUsers();

        assertNotNull(result);
        assertEquals(users.size(), Objects.requireNonNull(result.getBody()).size());
        assertEquals(users.get(0).getUsername(), result.getBody().get(0).getUsername());
        assertEquals(users.get(1).getUsername(), result.getBody().get(1).getUsername());
    }

    @Test
    public void testGetAllUsers_Failure () {
        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetUserByID_Successful () throws Exception {
        given(userService.getUserById(TEST_USER.getId())).willReturn(TEST_USER);

        ResponseEntity<User> example = userController.getUserById(TEST_USER.getId());
        assertNotNull(example.getBody());
        assertEquals(TEST_USER.getId(), example.getBody().getId());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testGetUserByID_throwsException () throws Exception {
        ResponseEntity<User> example = userController.getUserById(TEST_USER.getId());
        assertNull(example.getBody());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testAddUser_Successful () {
        when(userService.addUser(TEST_USER)).thenReturn(TEST_USER);

        // Call the controller method
        ResponseEntity<User> responseEntity = userController.addUser(TEST_USER);

        // Verify the response status code
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify the returned user
        assertEquals(TEST_USER, responseEntity.getBody());
    }

    @Test
    public void testAddUser_Unsuccessful() {
        ResponseEntity<User> responseEntity = userController.addUser(TEST_USER);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateUser_Successful() throws Exception {
        when(userService.updateUser(TEST_USER.getId(), TEST_USER)).thenReturn(TEST_USER);
        ResponseEntity<User> responseEntity = userController.updateUser(TEST_USER.getId(), TEST_USER);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TEST_USER, responseEntity.getBody());
    }

    @Test
    public void testUpdateUser_Unsuccessful() throws Exception {
        ResponseEntity<User> responseEntity = userController.updateUser(TEST_USER.getId(), TEST_USER);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteUser_Successful() {
        ResponseEntity<?> responseEntity = userController.deleteUser(TEST_USER.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteUser_Unsuccessful() {
        ResponseEntity<?> responseEntity = userController.deleteUser(TEST_USER.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
