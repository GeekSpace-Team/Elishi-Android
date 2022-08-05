package com.elishi.android.Modal.Constant;

public class Constant {
    private String id;
    private String titleTM;
    private String titleRU;
    private String titleEN;
    private String content_light_tm;
    private String content_light_ru;
    private String content_light_en;
    private String content_dark_tm;
    private String content_dark_ru;
    private String content_dark_en;
    private String page_type;
    private String created_at;
    private String updated_at;

    public Constant(String id, String titleTM, String titleRU, String titleEN, String content_light_tm, String content_light_ru, String content_light_en, String content_dark_tm, String content_dark_ru, String content_dark_en, String page_type, String created_at, String updated_at) {
        this.id = id;
        this.titleTM = titleTM;
        this.titleRU = titleRU;
        this.titleEN = titleEN;
        this.content_light_tm = content_light_tm;
        this.content_light_ru = content_light_ru;
        this.content_light_en = content_light_en;
        this.content_dark_tm = content_dark_tm;
        this.content_dark_ru = content_dark_ru;
        this.content_dark_en = content_dark_en;
        this.page_type = page_type;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleTM() {
        return titleTM;
    }

    public void setTitleTM(String titleTM) {
        this.titleTM = titleTM;
    }

    public String getTitleRU() {
        return titleRU;
    }

    public void setTitleRU(String titleRU) {
        this.titleRU = titleRU;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getContent_light_tm() {
        return content_light_tm;
    }

    public void setContent_light_tm(String content_light_tm) {
        this.content_light_tm = content_light_tm;
    }

    public String getContent_light_ru() {
        return content_light_ru;
    }

    public void setContent_light_ru(String content_light_ru) {
        this.content_light_ru = content_light_ru;
    }

    public String getContent_light_en() {
        return content_light_en;
    }

    public void setContent_light_en(String content_light_en) {
        this.content_light_en = content_light_en;
    }

    public String getContent_dark_tm() {
        return content_dark_tm;
    }

    public void setContent_dark_tm(String content_dark_tm) {
        this.content_dark_tm = content_dark_tm;
    }

    public String getContent_dark_ru() {
        return content_dark_ru;
    }

    public void setContent_dark_ru(String content_dark_ru) {
        this.content_dark_ru = content_dark_ru;
    }

    public String getContent_dark_en() {
        return content_dark_en;
    }

    public void setContent_dark_en(String content_dark_en) {
        this.content_dark_en = content_dark_en;
    }

    public String getPage_type() {
        return page_type;
    }

    public void setPage_type(String page_type) {
        this.page_type = page_type;
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
}
