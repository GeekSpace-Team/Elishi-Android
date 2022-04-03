package com.elishi.android.Modal.Response;

public class Message {
    private String tm;
    private String ru;
    private String en;

    public Message(String tm, String ru, String en) {
        this.tm = tm;
        this.ru = ru;
        this.en = en;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
