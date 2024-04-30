package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.socialmediathing.model.Comment;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private ICommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long commentId) throws Exception {
        return commentRepository.findById(commentId)
                .orElseThrow(Exception::new);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) throws Exception {
        if (!commentRepository.existsById(commentId)) {
            throw new Exception();
        }
        commentRepository.deleteById(commentId);
    }

    public Comment updateComment(Long commentId, Comment commentDetails) throws Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(Exception::new);

        comment.setText(commentDetails.getText());
        comment.setTimestamp(commentDetails.getTimestamp());
        comment.setLikes(commentDetails.getLikes());
        comment.setCommenterUsername(commentDetails.getCommenterUsername());
        comment.setPost(commentDetails.getPost());

        return commentRepository.save(comment);
    }
}

