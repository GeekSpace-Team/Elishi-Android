package com.elishi.android.Modal.Product;

import com.elishi.android.Modal.Home.Ads;

import java.util.ArrayList;

public class GetSingleProduct {
    private Product product;
    private ArrayList<Product> similar=new ArrayList<>();
    private Ads ads;

    public GetSingleProduct(Product product, ArrayList<Product> similar, Ads ads) {
        this.product = product;
        this.similar = similar;
        this.ads = ads;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<Product> getSimilar() {
        return similar;
    }

    public void setSimilar(ArrayList<Product> similar) {
        this.similar = similar;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }
}
