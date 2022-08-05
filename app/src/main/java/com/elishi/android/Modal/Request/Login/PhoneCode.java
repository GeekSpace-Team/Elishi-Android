package com.elishi.android.Modal.Request.Login;

public class PhoneCode {
    private String phoneNumber;
    private String code;
    private String type;

    public PhoneCode(String phoneNumber, String code, String type) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
