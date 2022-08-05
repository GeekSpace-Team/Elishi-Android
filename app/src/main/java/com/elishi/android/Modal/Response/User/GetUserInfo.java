package com.elishi.android.Modal.Response.User;


import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Response.Login.User;

import java.util.ArrayList;

public class GetUserInfo {
    private User user;
    private ArrayList<Product> products=new ArrayList<>();

    public GetUserInfo(User user, ArrayList<Product> products) {
        this.user = user;
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
