package org.example.socialmediathing.test;

import org.example.socialmediathing.model.Comment;
import org.example.socialmediathing.repository.ICommentRepository;
import org.example.socialmediathing.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static java.util.Calendar.OCTOBER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private ICommentRepository commentRepository;
    final Comment TEST_COMMENT = new Comment(1L, "Comment", new Date(Calendar.DATE), 4, "username");

    @Test
    public void testGetAllComments_returnsAllComments() {
        // Mock data
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L,  "Comment 1", new Date(Calendar.DATE), 4, "username 1"));
        comments.add(new Comment(2L,  "Comment 2", new Date(Calendar.DATE), 12, "username 2"));

        // Stub the repository method to return the mock data
        when(commentRepository.findAll()).thenReturn(comments);

        // Call the service method
        List<Comment> result = commentService.getAllComments();

        // Verify the result
        assertEquals(comments.size(), result.size());
        assertEquals(comments.get(0).getText(), result.get(0).getText());
        assertEquals(comments.get(1).getText(), result.get(1).getText());
    }

    @Test
    public void testGetAllComments_fails() {
        // Stub the repository method to throw an exception when called
        given(commentRepository.findAll()).willThrow(new RuntimeException("Failed to retrieve comments"));

        // Verify that calling getAllComments method throws an exception
        assertThrows(RuntimeException.class, () -> commentService.getAllComments());
    }


    @Test
    public void testGetCommentByID_validID_returnsComment() throws Exception {
        given(commentRepository.findById(TEST_COMMENT.getId())).willReturn(Optional.of(TEST_COMMENT));

        Comment example = commentService.getCommentById(TEST_COMMENT.getId());
        assertNotNull(example);
        assertEquals(TEST_COMMENT.getId(), example.getId());
    }

    @Test
    public void testGetCommentByID_invalidID_fails() {
        given(commentRepository.findById(TEST_COMMENT.getId())).willReturn(Optional.empty());

        assertThrows(Exception.class, () -> commentService.getCommentById(TEST_COMMENT.getId()));
    }

    @Test
    public void testAddComment_Successful() {
        // Stub the repository method to return the saved comment
        given(commentRepository.save(TEST_COMMENT)).willReturn(TEST_COMMENT);

        // Call the service method
        Comment example = commentService.createComment(TEST_COMMENT);

        // Verify that the comment was saved successfully
        assertNotNull(example);
        assertEquals(TEST_COMMENT.getId(), example.getId());
        assertEquals(TEST_COMMENT.getText(), example.getText());
        assertEquals(TEST_COMMENT.getTimestamp(), example.getTimestamp());
        assertEquals(TEST_COMMENT.getCommenterUsername(), example.getCommenterUsername());
        assertEquals(TEST_COMMENT.getLikes(), example.getLikes());
    }

    @Test
    public void testAddComment_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        given(commentRepository.save(TEST_COMMENT)).willThrow(new RuntimeException("Failed to add comment"));

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> commentService.createComment(TEST_COMMENT));
    }

    @Test
    public void testDeleteComment_Successful() {
        // Call the service method
        assertDoesNotThrow(() -> commentService.deleteComment(TEST_COMMENT.getId()));

        // Verify that the delete method of the repository was called with the correct commentId
        verify(commentRepository, times(1)).deleteById(TEST_COMMENT.getId());
    }

    @Test
    public void testDeleteComment_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        doThrow(new RuntimeException("Failed to delete comment")).when(commentRepository).deleteById(TEST_COMMENT.getId());

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> commentService.deleteComment(TEST_COMMENT.getId()));
    }

    @Test
    public void testUpdateComment_Successful() throws Exception {
        // Mock data
        Comment updatedComment = new Comment(TEST_COMMENT.getId(), "Updated Comment", new Date(Calendar.DATE), 1000, "Updated Username");

        // Stub the repository method to return the existing comment
        when(commentRepository.findById(TEST_COMMENT.getId())).thenReturn(Optional.of(TEST_COMMENT));

        // Stub the repository method to return the updated comment
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedComment);

        // Call the service method
        Comment result = commentService.updateComment(TEST_COMMENT.getId(), updatedComment);

        // Verify the result
        assertNotNull(result);
        assertEquals(updatedComment.getId(), result.getId());
        assertEquals(updatedComment.getText(), result.getText());
        assertEquals(updatedComment.getTimestamp(), result.getTimestamp());
        assertEquals(updatedComment.getCommenterUsername(), result.getCommenterUsername());
        assertEquals(updatedComment.getLikes(), result.getLikes());
    }

    @Test
    public void testUpdateComment_Unsuccessful_CommentNotFound() {
        // Mock data
        Comment updatedComment = new Comment(TEST_COMMENT.getId(), "Updated Comment", new Date(Calendar.DATE), 1000, "Updated Username");

        // Stub the repository method to return an empty optional
        when(commentRepository.findById(TEST_COMMENT.getId())).thenReturn(Optional.empty());

        // Call the service method and verify that it throws an exception
        assertThrows(Exception.class, () -> commentService.updateComment(TEST_COMMENT.getId(), updatedComment));

        // Verify that the repository method save was not called
        verify(commentRepository, never()).save(any(Comment.class));
    }

}

