package com.elishi.android.Modal.Product;

public class ProductImage {
    private Integer id;
    private String smallImage;
    private String largeImage;

    public ProductImage(Integer id, String smallImage, String largeImage) {
        this.id = id;
        this.smallImage = smallImage;
        this.largeImage = largeImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }
}
