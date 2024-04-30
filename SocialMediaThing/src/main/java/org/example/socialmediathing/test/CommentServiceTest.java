package org.example.socialmediathing.test;

import org.example.socialmediathing.model.Comment;
import org.example.socialmediathing.repository.ICommentRepository;
import org.example.socialmediathing.service.CommentService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @Mock
    private ICommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @org.junit.Test
    @Test
    public void testGetAllComments() {
        // Mock data
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L, "This is a comment", new Date(), 0, "JohnDoe"));
        comments.add(new Comment(2L, "This is another comment", new Date(), 2, "JaneDoe"));

        // Stub the repository method to return the mock data
        when(commentRepository.findAll()).thenReturn(comments);

        // Call the service method
        List<Comment> result = commentService.getAllComments();

        // Verify the result
        assertEquals(comments.size(), result.size());
        assertEquals(comments.get(0).getText(), result.get(0).getText());
        assertEquals(comments.get(1).getText(), result.get(1).getText());
    }

    @org.junit.Test
    @Test
    public void testGetCommentById() throws Exception {
        // Mock data
        long commentId = 1L;
        Comment comment = new Comment(commentId, "This is a comment", new Date(), 0, "JohnDoe");

        // Stubbing repository method
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Call service method
        Comment result = commentService.getCommentById(commentId);

        // Assertions
        assertNotNull(result);
        assertEquals(Optional.of(commentId), Optional.of(result.getId()));
        assertEquals(comment.getText(), result.getText());
    }

    @org.junit.Test
    @Test
    public void testCreateComment() {
        // Mock data
        Comment comment = new Comment(1L, "This is a comment", new Date(), 0, "JohnDoe");

        // Stubbing repository method
        when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        // Call service method
        Comment result = commentService.createComment(comment);

        // Assertions
        assertNotNull(result);
        assertEquals(comment.getText(), result.getText());
    }

    @org.junit.Test
    @Test
    public void testDeleteComment() throws Exception {
        // Mock data
        long commentId = 1L;

        // Stubbing repository method
        Mockito.doNothing().when(commentRepository).deleteById(commentId);

        // Call service method
        commentService.deleteComment(commentId);

        // Verify that deleteById method was called
        Mockito.verify(commentRepository, Mockito.times(1)).deleteById(commentId);
    }

    @org.junit.Test
    @Test
    public void testUpdateComment() throws Exception {
        // Mock data
        long commentId = 1L;
        Comment existingComment = new Comment(commentId, "This is a comment", new Date(), 0, "JohnDoe");
        Comment updatedComment = new Comment(commentId, "Updated comment", new Date(), 0, "JohnDoe");

        // Stubbing repository method
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(updatedComment);

        // Call service method
        Comment result = commentService.updateComment(commentId, updatedComment);

        // Assertions
        assertNotNull(result);
        assertEquals(updatedComment.getText(), result.getText());
    }
}

