package com.elishi.android.Modal.Profile;

public class User {
    private Integer userId;
    private String username;
    private String image;
    private String status;

    public User(Integer userId, String username, String image, String status) {
        this.userId = userId;
        this.username = username;
        this.image = image;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
