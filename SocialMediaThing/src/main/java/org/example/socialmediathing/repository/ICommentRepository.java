package org.example.socialmediathing.repository;

import org.example.socialmediathing.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
}

