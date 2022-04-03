package com.elishi.android.Modal.Response.Login;

public class UserBody {
    private String exist;
    private User user;

    public UserBody(String exist, User user) {
        this.exist = exist;
        this.user = user;
    }

    public String getExist() {
        return exist;
    }

    public void setExist(String exist) {
        this.exist = exist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
