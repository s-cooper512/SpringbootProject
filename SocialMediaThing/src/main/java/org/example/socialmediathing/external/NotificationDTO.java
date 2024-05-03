package org.example.socialmediathing.external;

import java.util.Date;

public class NotificationDTO {

    private Long postOwnerId; // ID of the user who owns the post
    private String commenterUsername; // Username of the user who commented
    private Long postId; // ID of the post

    //private Date timestamp;
    public NotificationDTO() {
    }

    public NotificationDTO(Long userId, String username, Long postId) {
        this.setPostOwnerId(userId);
        this.setPostId(postId);
        this.setCommenterUsername(username);
    }

    public Long getPostOwnerId() {
        return postOwnerId;
    }

    public void setPostOwnerId(Long postOwnerId) {
        this.postOwnerId = postOwnerId;
    }

    public String getCommenterUsername() {
        return commenterUsername;
    }

    public void setCommenterUsername(String commenterUsername) {
        this.commenterUsername = commenterUsername;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
