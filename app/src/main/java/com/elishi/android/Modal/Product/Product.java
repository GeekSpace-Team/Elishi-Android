package com.elishi.android.Modal.Product;

public class Product {
    private Integer productId;
    private String name;
    private Double cost;
    private String image;
    private boolean isFavourite;

    public Product(Integer productId, String name, Double cost, String image, boolean isFavourite) {
        this.productId = productId;
        this.name = name;
        this.cost = cost;
        this.image = image;
        this.isFavourite = isFavourite;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
