package com.elishi.android.Modal.Home;

public class Ads {
    private Integer id;
    private String ads_image;
    private Integer constant_id;
    private String site_url;
    private String created_at;
    private String updated_at;
    private String status;

    public Ads(Integer id, String ads_image, Integer constant_id, String site_url, String created_at, String updated_at, String status) {
        this.id = id;
        this.ads_image = ads_image;
        this.constant_id = constant_id;
        this.site_url = site_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAds_image() {
        return ads_image;
    }

    public void setAds_image(String ads_image) {
        this.ads_image = ads_image;
    }

    public Integer getConstant_id() {
        return constant_id;
    }

    public void setConstant_id(Integer constant_id) {
        this.constant_id = constant_id;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
