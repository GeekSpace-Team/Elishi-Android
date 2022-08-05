package com.elishi.android.Modal.Home;

public class NotifBody {
    private String notification_token;

    public NotifBody(String notification_token) {
        this.notification_token = notification_token;
    }

    public String getNotification_token() {
        return notification_token;
    }

    public void setNotification_token(String notification_token) {
        this.notification_token = notification_token;
    }
}
