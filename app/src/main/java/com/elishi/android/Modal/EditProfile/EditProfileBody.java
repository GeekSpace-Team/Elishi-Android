package com.elishi.android.Modal.EditProfile;

public class EditProfileBody {
    private String fullname;
    private String address;
    private String region;
    private String email;
    private String gender;

    public EditProfileBody(String fullname, String address, String region, String email, String gender) {
        this.fullname = fullname;
        this.address = address;
        this.region = region;
        this.email = email;
        this.gender = gender;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
