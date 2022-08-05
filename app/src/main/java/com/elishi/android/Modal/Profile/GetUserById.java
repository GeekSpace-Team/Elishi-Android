package com.elishi.android.Modal.Profile;

import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Response.Login.User;

import java.util.ArrayList;

public class GetUserById {
    private com.elishi.android.Modal.Response.Login.User user;
    private ArrayList<Product> products=new ArrayList<>();

    public GetUserById(com.elishi.android.Modal.Response.Login.User user, ArrayList<Product> products) {
        this.user = user;
        this.products = products;
    }

    public com.elishi.android.Modal.Response.Login.User getUser() {
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
