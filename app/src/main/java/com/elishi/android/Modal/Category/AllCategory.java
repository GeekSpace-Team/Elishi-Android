package com.elishi.android.Modal.Category;

import java.util.ArrayList;

public class AllCategory {
    private Integer id;
    private ArrayList<String> images=new ArrayList<>();
    private String title_tm;
    private String title_ru;
    private String title_en;

    public AllCategory(Integer id, ArrayList<String> images, String title_tm, String title_ru, String title_en) {
        this.id = id;
        this.images = images;
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

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
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
