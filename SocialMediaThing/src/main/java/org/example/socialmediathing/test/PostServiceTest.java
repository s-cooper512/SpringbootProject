package org.example.socialmediathing.test;
import org.example.socialmediathing.model.Post;
import org.example.socialmediathing.repository.IPostRepository;
import org.example.socialmediathing.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private IPostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @org.junit.Test
    @Test
    public void testGetAllPosts() {
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
    public void testGetPostById() throws Exception {
        // Mock data
        long postId = 1L;
        Post post = new Post(postId, "Title", "Content", "url", 10);

        // Stub the repository method to return the mock data
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Call the service method
        Optional<Post> result = Optional.ofNullable(postService.getPostById(postId));

        // Verify the result
        assertEquals(post, result.orElse(null));
    }

    @Test
    public void testAddPost() {
        // Mock data
        Post post = new Post(1L, "Title", "Content", "url", 10);

        // Stub the repository method to return the mock data
        when(postRepository.save(post)).thenReturn(post);

        // Call the service method
        Post result = postService.addPost(post);

        // Verify the result
        assertEquals(post, result);
    }

    @Test
    public void testDeletePost() {
        // Mock data
        long postId = 1L;

        // Call the service method
        postService.deletePost(postId);

        // Verify that the delete method of the repository was called
        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    public void testUpdatePost() throws Exception {
        // Mock data
        long postId = 1L;
        Post post = new Post(postId, "Updated Title", "Updated Content", "updatedUrl", 20);

        // Stub the repository method to return the mock data
        when(postRepository.save(post)).thenReturn(post);

        // Call the service method
        Post result = postService.updatePost(postId, post);

        // Verify the result
        assertEquals(post, result);
    }
}

