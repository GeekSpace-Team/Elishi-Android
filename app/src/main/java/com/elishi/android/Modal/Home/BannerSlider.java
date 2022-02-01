package com.elishi.android.Modal.Home;

public class BannerSlider {
    private Integer id;
    private String imageUrl;
    private Integer position;

    public BannerSlider(Integer id, String imageUrl,Integer position) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.position =position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
