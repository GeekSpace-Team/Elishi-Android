package com.elishi.android.Modal.Location;

public class SubLocation {
    private String id;
    private String title_ru;
    private String title_tm;

    public SubLocation(String id, String title_ru, String title_tm) {
        this.id = id;
        this.title_ru = title_ru;
        this.title_tm = title_tm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle_ru() {
        return title_ru;
    }

    public void setTitle_ru(String title_ru) {
        this.title_ru = title_ru;
    }

    public String getTitle_tm() {
        return title_tm;
    }

    public void setTitle_tm(String title_tm) {
        this.title_tm = title_tm;
    }
}
