package com.elishi.android.Modal.Response.PublicAPI;

public class SubLocations {
    private Integer id;
    private String district_name_tm;
    private String district_name_ru;
    private String district_name_en;
    private Integer region_id;

    public SubLocations(Integer id, String district_name_tm, String district_name_ru, String district_name_en, Integer region_id) {
        this.id = id;
        this.district_name_tm = district_name_tm;
        this.district_name_ru = district_name_ru;
        this.district_name_en = district_name_en;
        this.region_id = region_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDistrict_name_tm() {
        return district_name_tm;
    }

    public void setDistrict_name_tm(String district_name_tm) {
        this.district_name_tm = district_name_tm;
    }

    public String getDistrict_name_ru() {
        return district_name_ru;
    }

    public void setDistrict_name_ru(String district_name_ru) {
        this.district_name_ru = district_name_ru;
    }

    public String getDistrict_name_en() {
        return district_name_en;
    }

    public void setDistrict_name_en(String district_name_en) {
        this.district_name_en = district_name_en;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }
}
