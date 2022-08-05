package com.elishi.android.Modal.Home;

import com.elishi.android.Modal.Category.Category;
import com.elishi.android.Modal.Product.Product;
import com.elishi.android.Modal.Profile.User;

import java.util.ArrayList;

public class GetHome {
    private ArrayList<BannerSlider> banner=new ArrayList<>();
    private ArrayList<Category> main_category=new ArrayList<>();
    private ArrayList<Ads> ads=new ArrayList<>();
    private ArrayList<User> vip_users=new ArrayList<>();
    private ArrayList<Event> events=new ArrayList<>();
    private ArrayList<Product> newProducts=new ArrayList<>();
    private ArrayList<Product> trendProducts=new ArrayList<>();
    private ArrayList<DeviceVersion> deviceVersion=new ArrayList<>();
    private ArrayList<EventProducts> eventProducts=new ArrayList<>();


    public GetHome(ArrayList<BannerSlider> banner, ArrayList<Category> main_category, ArrayList<Ads> ads, ArrayList<User> vip_users, ArrayList<Event> events, ArrayList<Product> newProducts, ArrayList<Product> trendProducts, ArrayList<DeviceVersion> deviceVersion, ArrayList<EventProducts> eventProducts) {
        this.banner = banner;
        this.main_category = main_category;
        this.ads = ads;
        this.vip_users = vip_users;
        this.events = events;
        this.newProducts = newProducts;
        this.trendProducts = trendProducts;
        this.deviceVersion = deviceVersion;
        this.eventProducts = eventProducts;
    }

    public ArrayList<BannerSlider> getBanner() {
        return banner;
    }

    public void setBanner(ArrayList<BannerSlider> banner) {
        this.banner = banner;
    }

    public ArrayList<Category> getMain_category() {
        return main_category;
    }

    public void setMain_category(ArrayList<Category> main_category) {
        this.main_category = main_category;
    }

    public ArrayList<Ads> getAds() {
        return ads;
    }

    public void setAds(ArrayList<Ads> ads) {
        this.ads = ads;
    }

    public ArrayList<User> getVip_users() {
        return vip_users;
    }

    public void setVip_users(ArrayList<User> vip_users) {
        this.vip_users = vip_users;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Product> getNewProducts() {
        return newProducts;
    }

    public void setNewProducts(ArrayList<Product> newProducts) {
        this.newProducts = newProducts;
    }

    public ArrayList<Product> getTrendProducts() {
        return trendProducts;
    }

    public void setTrendProducts(ArrayList<Product> trendProducts) {
        this.trendProducts = trendProducts;
    }

    public ArrayList<DeviceVersion> getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(ArrayList<DeviceVersion> deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public ArrayList<EventProducts> getEventProducts() {
        return eventProducts;
    }

    public void setEventProducts(ArrayList<EventProducts> eventProducts) {
        this.eventProducts = eventProducts;
    }
}
