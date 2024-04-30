package org.example.socialmediathing.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private int likes;

    private String commenterUsername;

    @ManyToOne
    private Post post;

    public Comment(long commentId, String thisIsAComment, Date date, int likes, String username) {
        this.setId(commentId);
        this.setText(thisIsAComment);
        this.setTimestamp(date);
        this.setLikes(likes);
        this.setCommenterUsername(username);
    }

    public Comment() {

    }

    // Constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getCommenterUsername() {
        return commenterUsername;
    }

    public void setCommenterUsername(String commenterUsername) {
        this.commenterUsername = commenterUsername;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

