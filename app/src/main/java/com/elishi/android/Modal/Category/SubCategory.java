package com.elishi.android.Modal.Category;

public class SubCategory {
    private Integer id;
    private String sub_category_name_tm;
    private String sub_category_name_ru;
    private String sub_category_name_en;
    private Integer category_id;
    private Integer status;
    private String created_at;
    private String updated_at;
    private String image;

    public SubCategory(Integer id, String sub_category_name_tm, String sub_category_name_ru, String sub_category_name_en, Integer category_id, Integer status, String created_at, String updated_at, String image) {
        this.id = id;
        this.sub_category_name_tm = sub_category_name_tm;
        this.sub_category_name_ru = sub_category_name_ru;
        this.sub_category_name_en = sub_category_name_en;
        this.category_id = category_id;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSub_category_name_tm() {
        return sub_category_name_tm;
    }

    public void setSub_category_name_tm(String sub_category_name_tm) {
        this.sub_category_name_tm = sub_category_name_tm;
    }

    public String getSub_category_name_ru() {
        return sub_category_name_ru;
    }

    public void setSub_category_name_ru(String sub_category_name_ru) {
        this.sub_category_name_ru = sub_category_name_ru;
    }

    public String getSub_category_name_en() {
        return sub_category_name_en;
    }

    public void setSub_category_name_en(String sub_category_name_en) {
        this.sub_category_name_en = sub_category_name_en;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
