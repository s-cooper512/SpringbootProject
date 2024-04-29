package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.socialmediathing.model.Post;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private IPostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) throws Exception {
        return postRepository.findById(postId)
                .orElseThrow(Exception::new);
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public Post updatePost(Long postId, Post post) throws Exception {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(Exception::new);

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        // Set other fields

        return postRepository.save(existingPost);
    }
}

