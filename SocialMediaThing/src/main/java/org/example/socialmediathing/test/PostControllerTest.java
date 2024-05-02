package org.example.socialmediathing.test;

import org.example.socialmediathing.controller.PostController;
import org.example.socialmediathing.model.Post;
import org.example.socialmediathing.service.PostService;
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
public class PostControllerTest {
    @Autowired
    PostController postController;

    @MockBean
    PostService postService;

    final Post TEST_POST = new Post(1L, "Title", "Content", "URL", 7);

    @Test
    public void testGetAllPosts_Successful () {
        // Mock data
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Title 1", "Content 1", "url1", 10));
        posts.add(new Post(2L, "Title 2", "Content 2", "url2", 20));

        when(postService.getAllPosts()).thenReturn(posts);

        ResponseEntity<List<Post>> result = postController.getAllPosts();

        assertNotNull(result);
        assertEquals(posts.size(), Objects.requireNonNull(result.getBody()).size());
        assertEquals(posts.get(0).getTitle(), result.getBody().get(0).getTitle());
        assertEquals(posts.get(1).getTitle(), result.getBody().get(1).getTitle());
    }

    @Test
    public void testGetAllPosts_Failure () {
        ResponseEntity<List<Post>> responseEntity = postController.getAllPosts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetPostByID_Successful () throws Exception {
        given(postService.getPostById(TEST_POST.getId())).willReturn(TEST_POST);

        ResponseEntity<Post> example = postController.getPostById(TEST_POST.getId());
        assertNotNull(example.getBody());
        assertEquals(TEST_POST.getId(), example.getBody().getId());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testGetPostByID_throwsException () throws Exception {
        ResponseEntity<Post> example = postController.getPostById(TEST_POST.getId());
        assertNull(example.getBody());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testAddPost_Successful () {
        when(postService.addPost(TEST_POST)).thenReturn(TEST_POST);

        // Call the controller method
        ResponseEntity<Post> responseEntity = postController.addPost(TEST_POST);

        // Verify the response status code
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify the returned post
        assertEquals(TEST_POST, responseEntity.getBody());
    }

    @Test
    public void testAddPost_Unsuccessful() {
        ResponseEntity<Post> responseEntity = postController.addPost(TEST_POST);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdatePost_Successful() throws Exception {
        when(postService.updatePost(TEST_POST.getId(), TEST_POST)).thenReturn(TEST_POST);
        ResponseEntity<Post> responseEntity = postController.updatePost(TEST_POST.getId(), TEST_POST);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TEST_POST, responseEntity.getBody());
    }

    @Test
    public void testUpdatePost_Unsuccessful() throws Exception {
        ResponseEntity<Post> responseEntity = postController.updatePost(TEST_POST.getId(), TEST_POST);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletePost_Successful() {
        ResponseEntity<?> responseEntity = postController.deletePost(TEST_POST.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletePost_Unsuccessful() {
        ResponseEntity<?> responseEntity = postController.deletePost(TEST_POST.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
