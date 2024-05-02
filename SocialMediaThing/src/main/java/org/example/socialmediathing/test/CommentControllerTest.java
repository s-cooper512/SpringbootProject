package org.example.socialmediathing.test;

import org.example.socialmediathing.controller.CommentController;
import org.example.socialmediathing.model.Comment;
import org.example.socialmediathing.service.CommentService;
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
public class CommentControllerTest {
    @Autowired
    CommentController commentController;

    @MockBean
    CommentService commentService;

    final Comment TEST_COMMENT = new Comment(1L, "Comment", new Date(Calendar.DATE), 4, "username");

    @Test
    public void testGetAllComments_Successful () {
        // Mock data
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L,  "Comment 1", new Date(Calendar.DATE), 4, "username 1"));
        comments.add(new Comment(2L,  "Comment 2", new Date(Calendar.DATE), 12, "username 2"));

        when(commentService.getAllComments()).thenReturn(comments);

        ResponseEntity<List<Comment>> result = commentController.getAllComments();

        assertNotNull(result);
        assertEquals(comments.size(), Objects.requireNonNull(result.getBody()).size());
        assertEquals(comments.get(0).getText(), result.getBody().get(0).getText());
        assertEquals(comments.get(1).getText(), result.getBody().get(1).getText());
    }

    @Test
    public void testGetAllComments_Failure () {
        ResponseEntity<List<Comment>> responseEntity = commentController.getAllComments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetCommentByID_Successful () throws Exception {
        given(commentService.getCommentById(TEST_COMMENT.getId())).willReturn(TEST_COMMENT);

        ResponseEntity<Comment> example = commentController.getCommentById(TEST_COMMENT.getId());
        assertNotNull(example.getBody());
        assertEquals(TEST_COMMENT.getId(), example.getBody().getId());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testGetCommentByID_throwsException () throws Exception {
        ResponseEntity<Comment> example = commentController.getCommentById(TEST_COMMENT.getId());
        assertNull(example.getBody());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testAddComment_Successful () {
        when(commentService.createComment(TEST_COMMENT)).thenReturn(TEST_COMMENT);

        // Call the controller method
        ResponseEntity<Comment> responseEntity = commentController.createComment(TEST_COMMENT);

        // Verify the response status code
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify the returned comment
        assertEquals(TEST_COMMENT, responseEntity.getBody());
    }

    @Test
    public void testAddComment_Unsuccessful() {
        ResponseEntity<Comment> responseEntity = commentController.createComment(TEST_COMMENT);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateComment_Successful() throws Exception {
        when(commentService.updateComment(TEST_COMMENT.getId(), TEST_COMMENT)).thenReturn(TEST_COMMENT);
        ResponseEntity<Comment> responseEntity = commentController.updateComment(TEST_COMMENT.getId(), TEST_COMMENT);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TEST_COMMENT, responseEntity.getBody());
    }

    @Test
    public void testUpdateComment_Unsuccessful() throws Exception {
        ResponseEntity<Comment> responseEntity = commentController.updateComment(TEST_COMMENT.getId(), TEST_COMMENT);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteComment_Successful() throws Exception {
        ResponseEntity<?> responseEntity = commentController.deleteComment(TEST_COMMENT.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteComment_Unsuccessful() throws Exception {
        ResponseEntity<?> responseEntity = commentController.deleteComment(TEST_COMMENT.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
