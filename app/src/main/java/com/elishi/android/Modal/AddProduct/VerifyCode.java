package com.elishi.android.Modal.AddProduct;

public class VerifyCode {
    private String number;
    private String text;
    private String code;

    public VerifyCode(String number, String text, String code) {
        this.number = number;
        this.text = text;
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
