package com.elishi.android.Modal.Product;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductBody {
    private Integer page;
    private Integer limit;
    @Nullable
    private ArrayList<Integer> sub_category=new ArrayList<>();
    @Nullable
    private Integer category;
    @Nullable
    private Double min;
    @Nullable
    private Double max;
    @Nullable
    private ArrayList<Integer> region=new ArrayList<>();
    @Nullable
    private ArrayList<Integer> status=new ArrayList<>();
    @Nullable
    private Integer sort;
    @Nullable
    private Integer user;


    public ProductBody(Integer page, Integer limit, @Nullable ArrayList<Integer> sub_category, @Nullable Integer category, @Nullable Double min, @Nullable Double max, @Nullable ArrayList<Integer> region, @Nullable ArrayList<Integer> status, @Nullable Integer sort, @Nullable Integer user) {
        this.page = page;
        this.limit = limit;
        this.sub_category = sub_category;
        this.category = category;
        this.min = min;
        this.max = max;
        this.region = region;
        this.status = status;
        this.sort = sort;
        this.user = user;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Nullable
    public ArrayList<Integer> getSub_category() {
        return sub_category;
    }

    public void setSub_category(@Nullable ArrayList<Integer> sub_category) {
        this.sub_category = sub_category;
    }

    @Nullable
    public Integer getCategory() {
        return category;
    }

    public void setCategory(@Nullable Integer category) {
        this.category = category;
    }

    @Nullable
    public Double getMin() {
        return min;
    }

    public void setMin(@Nullable Double min) {
        this.min = min;
    }

    @Nullable
    public Double getMax() {
        return max;
    }

    public void setMax(@Nullable Double max) {
        this.max = max;
    }

    @Nullable
    public ArrayList<Integer> getRegion() {
        return region;
    }

    public void setRegion(@Nullable ArrayList<Integer> region) {
        this.region = region;
    }

    @Nullable
    public ArrayList<Integer> getStatus() {
        return status;
    }

    public void setStatus(@Nullable ArrayList<Integer> status) {
        this.status = status;
    }

    @Nullable
    public Integer getSort() {
        return sort;
    }

    public void setSort(@Nullable Integer sort) {
        this.sort = sort;
    }

    @Nullable
    public Integer getUser() {
        return user;
    }

    public void setUser(@Nullable Integer user) {
        this.user = user;
    }
}
