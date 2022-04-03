package com.elishi.android.Modal.Profile;

public class User {
    private Integer id;
    private String fullname;
    private String address;
    private String phone_number;
    private String profile_image;
    private Integer user_type_id;
    private Integer region_id;
    private String email;
    private String notification_token;
    private Integer gender;
    private Integer status;
    private String created_at;
    private String updated_at;
    private String user_type;
    private String product_limit;

    public User(Integer id, String fullname, String address, String phone_number, String profile_image, Integer user_type_id, Integer region_id, String email, String notification_token, Integer gender, Integer status, String created_at, String updated_at, String user_type, String product_limit) {
        this.id = id;
        this.fullname = fullname;
        this.address = address;
        this.phone_number = phone_number;
        this.profile_image = profile_image;
        this.user_type_id = user_type_id;
        this.region_id = region_id;
        this.email = email;
        this.notification_token = notification_token;
        this.gender = gender;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user_type = user_type;
        this.product_limit = product_limit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public Integer getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(Integer user_type_id) {
        this.user_type_id = user_type_id;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotification_token() {
        return notification_token;
    }

    public void setNotification_token(String notification_token) {
        this.notification_token = notification_token;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getProduct_limit() {
        return product_limit;
    }

    public void setProduct_limit(String product_limit) {
        this.product_limit = product_limit;
    }
}
