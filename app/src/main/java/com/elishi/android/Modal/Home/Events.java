package com.elishi.android.Modal.Home;

public class Events {
    private Ads ads;
    private Event event;
    private EventProducts eventProducts;
    private String type;

    public Events(Ads ads, String type) {
        this.ads = ads;
        this.type = type;
    }

    public Events(Event event, String type) {
        this.event = event;
        this.type = type;
    }

    public Events(EventProducts eventProducts, String type) {
        this.eventProducts = eventProducts;
        this.type = type;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventProducts getEventProducts() {
        return eventProducts;
    }

    public void setEventProducts(EventProducts eventProducts) {
        this.eventProducts = eventProducts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
