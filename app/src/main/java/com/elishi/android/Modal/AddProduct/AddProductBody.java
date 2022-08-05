package com.elishi.android.Modal.AddProduct;

import com.elishi.android.Modal.Product.Product;

import java.util.ArrayList;

public class AddProductBody {
    private String name;
    private String price;
    private String size;
    private String sub_category;
    private String phone_number;
    private String description;
    private ArrayList<Product.Images> images=new ArrayList<>();

    public AddProductBody(String name, String price, String size, String sub_category, String phone_number, String description, ArrayList<Product.Images> images) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.sub_category = sub_category;
        this.phone_number = phone_number;
        this.description = description;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Product.Images> getImages() {
        return images;
    }

    public void setImages(ArrayList<Product.Images> images) {
        this.images = images;
    }
}
