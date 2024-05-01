package org.example.socialmediathing.test;

import org.example.socialmediathing.model.Post;
import org.example.socialmediathing.repository.IPostRepository;
import org.example.socialmediathing.service.PostService;
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
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private IPostRepository postRepository;
    final Post TEST_POST = new Post(1L, "Title", "Content", "URL", 7);

    @Test
    public void testGetAllPosts_returnsAllPosts() {
        // Mock data
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Title 1", "Content 1", "url1", 10));
        posts.add(new Post(2L, "Title 2", "Content 2", "url2", 20));

        // Stub the repository method to return the mock data
        when(postRepository.findAll()).thenReturn(posts);

        // Call the service method
        List<Post> result = postService.getAllPosts();

        // Verify the result
        assertEquals(posts.size(), result.size());
        assertEquals(posts.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(posts.get(1).getTitle(), result.get(1).getTitle());
    }

    @Test
    public void testGetAllPosts_fails() {
        // Stub the repository method to throw an exception when called
        given(postRepository.findAll()).willThrow(new RuntimeException("Failed to retrieve posts"));

        // Verify that calling getAllPosts method throws an exception
        assertThrows(RuntimeException.class, () -> postService.getAllPosts());
    }


    @Test
    public void testGetPostByID_validID_returnsPost() throws Exception {
        given(postRepository.findById(TEST_POST.getId())).willReturn(Optional.of(TEST_POST));

        Post example = postService.getPostById(TEST_POST.getId());
        assertNotNull(example);
        assertEquals(TEST_POST.getId(), example.getId());
    }

    @Test
    public void testGetPostByID_invalidID_fails() {
        given(postRepository.findById(TEST_POST.getId())).willReturn(Optional.empty());

        assertThrows(Exception.class, () -> postService.getPostById(TEST_POST.getId()));
    }

    @Test
    public void testAddPost_Successful() {
        // Stub the repository method to return the saved post
        given(postRepository.save(TEST_POST)).willReturn(TEST_POST);

        // Call the service method
        Post example = postService.addPost(TEST_POST);

        // Verify that the post was saved successfully
        assertNotNull(example);
        assertEquals(TEST_POST.getId(), example.getId());
        assertEquals(TEST_POST.getTitle(), example.getTitle());
        assertEquals(TEST_POST.getContent(), example.getContent());
        assertEquals(TEST_POST.getMediaUrl(), example.getMediaUrl());
        assertEquals(TEST_POST.getLikes(), example.getLikes());
    }

    @Test
    public void testAddPost_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        given(postRepository.save(TEST_POST)).willThrow(new RuntimeException("Failed to add post"));

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> postService.addPost(TEST_POST));
    }

    @Test
    public void testDeletePost_Successful() {
        // Call the service method
        assertDoesNotThrow(() -> postService.deletePost(TEST_POST.getId()));

        // Verify that the delete method of the repository was called with the correct postId
        verify(postRepository, times(1)).deleteById(TEST_POST.getId());
    }

    @Test
    public void testDeletePost_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        doThrow(new RuntimeException("Failed to delete post")).when(postRepository).deleteById(TEST_POST.getId());

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> postService.deletePost(TEST_POST.getId()));
    }

    @Test
    public void testUpdatePost_Successful() throws Exception {
        // Mock data
        Post updatedPost = new Post(TEST_POST.getId(), "Updated Title", "Updated Content", "updatedUrl", 20);

        // Stub the repository method to return the existing post
        when(postRepository.findById(TEST_POST.getId())).thenReturn(Optional.of(TEST_POST));

        // Stub the repository method to return the updated post
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        // Call the service method
        Post result = postService.updatePost(TEST_POST.getId(), updatedPost);

        // Verify the result
        assertNotNull(result);
        assertEquals(updatedPost.getId(), result.getId());
        assertEquals(updatedPost.getTitle(), result.getTitle());
        assertEquals(updatedPost.getContent(), result.getContent());
        assertEquals(updatedPost.getMediaUrl(), result.getMediaUrl());
        assertEquals(updatedPost.getLikes(), result.getLikes());
    }

    @Test
    public void testUpdatePost_Unsuccessful_PostNotFound() {
        // Mock data
        Post updatedPost = new Post(TEST_POST.getId(), "Updated Title", "Updated Content", "updatedUrl", 20);

        // Stub the repository method to return an empty optional
        when(postRepository.findById(TEST_POST.getId())).thenReturn(Optional.empty());

        // Call the service method and verify that it throws an exception
        assertThrows(Exception.class, () -> postService.updatePost(TEST_POST.getId(), updatedPost));

        // Verify that the repository method save was not called
        verify(postRepository, never()).save(any(Post.class));
    }

}

