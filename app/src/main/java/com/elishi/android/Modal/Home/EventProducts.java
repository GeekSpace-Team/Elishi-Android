package com.elishi.android.Modal.Home;

import com.elishi.android.Modal.Product.Product;

import java.util.ArrayList;

public class EventProducts {
    private Event event;
    private ArrayList<Product> products=new ArrayList<>();

    public EventProducts(Event event, ArrayList<Product> products) {
        this.event = event;
        this.products = products;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
