package com.elishi.android.Modal.Response.PublicAPI;

import java.util.ArrayList;

public class Locations {
    private Integer id;
    private String region_name_tm;
    private String region_name_ru;
    private String region_name_en;
    private ArrayList<SubLocations> sub_locations=new ArrayList<>();

    public Locations(Integer id, String region_name_tm, String region_name_ru, String region_name_en, ArrayList<SubLocations> sub_locations) {
        this.id = id;
        this.region_name_tm = region_name_tm;
        this.region_name_ru = region_name_ru;
        this.region_name_en = region_name_en;
        this.sub_locations = sub_locations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegion_name_tm() {
        return region_name_tm;
    }

    public void setRegion_name_tm(String region_name_tm) {
        this.region_name_tm = region_name_tm;
    }

    public String getRegion_name_ru() {
        return region_name_ru;
    }

    public void setRegion_name_ru(String region_name_ru) {
        this.region_name_ru = region_name_ru;
    }

    public String getRegion_name_en() {
        return region_name_en;
    }

    public void setRegion_name_en(String region_name_en) {
        this.region_name_en = region_name_en;
    }

    public ArrayList<SubLocations> getSub_locations() {
        return sub_locations;
    }

    public void setSub_locations(ArrayList<SubLocations> sub_locations) {
        this.sub_locations = sub_locations;
    }
}
