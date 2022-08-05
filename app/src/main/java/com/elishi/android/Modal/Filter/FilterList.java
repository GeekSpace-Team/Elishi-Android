package com.elishi.android.Modal.Filter;

import com.elishi.android.Common.FilterType;
import com.elishi.android.Modal.Category.Category;
import com.elishi.android.Modal.Category.SubCategory;
import com.elishi.android.Modal.Location.Region;
import com.elishi.android.Modal.Location.SubLocation;
import com.elishi.android.Modal.Response.PublicAPI.SubLocations;

import java.util.ArrayList;

public class FilterList {
    private Integer id;
    private String title_tm;
    private String title_ru;
    private String title_en;
    private ArrayList<FilterItem> filterItems=new ArrayList<>();
    private String type;
    private ArrayList<SubCategory> category=new ArrayList<>();
    private ArrayList<SubLocations> regions=new ArrayList<>();

    public FilterList(Integer id, String title_tm, String title_ru, String title_en, ArrayList<FilterItem> filterItems, String type, ArrayList<SubCategory> category, ArrayList<SubLocations> regions) {
        this.id = id;
        this.title_tm = title_tm;
        this.title_ru = title_ru;
        this.title_en = title_en;
        this.filterItems = filterItems;
        this.type = type;
        this.category = category;
        this.regions = regions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle_tm() {
        return title_tm;
    }

    public void setTitle_tm(String title_tm) {
        this.title_tm = title_tm;
    }

    public String getTitle_ru() {
        return title_ru;
    }

    public void setTitle_ru(String title_ru) {
        this.title_ru = title_ru;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public ArrayList<FilterItem> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(ArrayList<FilterItem> filterItems) {
        this.filterItems = filterItems;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<SubCategory> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<SubCategory> category) {
        this.category = category;
    }

    public ArrayList<SubLocations> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<SubLocations> regions) {
        this.regions = regions;
    }
}
