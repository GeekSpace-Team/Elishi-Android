package com.elishi.android.Modal.Location;

import java.util.ArrayList;

public class Region {
    private String id;
    private String title_tm;
    private String title_ru;
    private ArrayList<SubLocation> subLocations=new ArrayList<>();

    public Region(String id, String title_tm, String title_ru, ArrayList<SubLocation> subLocations) {
        this.id = id;
        this.title_tm = title_tm;
        this.title_ru = title_ru;
        this.subLocations = subLocations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public ArrayList<SubLocation> getSubLocations() {
        return subLocations;
    }

    public void setSubLocations(ArrayList<SubLocation> subLocations) {
        this.subLocations = subLocations;
    }
}
