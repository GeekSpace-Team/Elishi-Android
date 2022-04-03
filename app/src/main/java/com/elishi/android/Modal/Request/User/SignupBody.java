package com.elishi.android.Modal.Request.User;

public class SignupBody {
    private String fullname;
    private String notif_token;
    private String phone_number;
    private Integer district_id;

    public SignupBody(String fullname, String notif_token, String phone_number, Integer district_id) {
        this.fullname = fullname;
        this.notif_token = notif_token;
        this.phone_number = phone_number;
        this.district_id = district_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNotif_token() {
        return notif_token;
    }

    public void setNotif_token(String notif_token) {
        this.notif_token = notif_token;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Integer getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }
}
