package org.example.socialmediathing.test;

import org.example.socialmediathing.repository.IPostRepository;
import org.example.socialmediathing.service.PostService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private IPostRepository postRepository;

    @Test
    public void testGetAllPosts() {
        
    }
}

