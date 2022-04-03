package com.elishi.android.Modal.Category;

public class Category {
    private Integer id;
    private String category_name_tm;
    private String category_name_ru;
    private String category_name_en;
    private Integer status;
    private String updated_at;
    private Boolean is_main;
    private String created_at;
    private String image;

    public Category(Integer id, String category_name_tm, String category_name_ru, String category_name_en, Integer status, String updated_at, Boolean is_main, String created_at, String image) {
        this.id = id;
        this.category_name_tm = category_name_tm;
        this.category_name_ru = category_name_ru;
        this.category_name_en = category_name_en;
        this.status = status;
        this.updated_at = updated_at;
        this.is_main = is_main;
        this.created_at = created_at;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory_name_tm() {
        return category_name_tm;
    }

    public void setCategory_name_tm(String category_name_tm) {
        this.category_name_tm = category_name_tm;
    }

    public String getCategory_name_ru() {
        return category_name_ru;
    }

    public void setCategory_name_ru(String category_name_ru) {
        this.category_name_ru = category_name_ru;
    }

    public String getCategory_name_en() {
        return category_name_en;
    }

    public void setCategory_name_en(String category_name_en) {
        this.category_name_en = category_name_en;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getIs_main() {
        return is_main;
    }

    public void setIs_main(Boolean is_main) {
        this.is_main = is_main;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
