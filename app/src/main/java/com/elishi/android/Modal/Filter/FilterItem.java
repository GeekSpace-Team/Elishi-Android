package com.elishi.android.Modal.Filter;

public class FilterItem {
    private Integer id;
    private String title_tm;
    private String title_ru;
    private String title_en;

    public FilterItem(Integer id, String title_tm, String title_ru, String title_en) {
        this.id = id;
        this.title_tm = title_tm;
        this.title_ru = title_ru;
        this.title_en = title_en;
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
}
