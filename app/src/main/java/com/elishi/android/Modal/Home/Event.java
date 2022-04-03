package com.elishi.android.Modal.Home;

import com.elishi.android.Modal.Product.Product;

import java.util.ArrayList;

public class Event {
    private Integer id;
    private String title_tm;
    private String title_ru;
    private String title_en;
    private String event_image;
    private String url;
    private Integer status;
    private String created_at;
    private String updated_at;
    private String event_type;
    private Integer go_id;
    private Integer is_main;

    public Event(Integer id, String title_tm, String title_ru, String title_en, String event_image, String url, Integer status, String created_at, String updated_at, String event_type, Integer go_id, Integer is_main) {
        this.id = id;
        this.title_tm = title_tm;
        this.title_ru = title_ru;
        this.title_en = title_en;
        this.event_image = event_image;
        this.url = url;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.event_type = event_type;
        this.go_id = go_id;
        this.is_main = is_main;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle_tm() {
        return title_tm;
    }

    public void setTitle_tm(String title_tm) {
        this.title_tm = title_tm;
    }

    public String getTitle_ru() {
        return title_ru;
    }

    public void setTitle_ru(String title_ru) {
        this.title_ru = title_ru;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public Integer getGo_id() {
        return go_id;
    }

    public void setGo_id(Integer go_id) {
        this.go_id = go_id;
    }

    public Integer getIs_main() {
        return is_main;
    }

    public void setIs_main(Integer is_main) {
        this.is_main = is_main;
    }
}
