package com.elishi.android.Modal.Product;

import com.elishi.android.Modal.Home.Ads;

import java.util.ArrayList;

public class Product {
    private Integer id;
    private String product_name;
    private Double price;
    private Integer status;
    private String description;
    private Integer sub_category_id;
    private Integer user_id;
    private Boolean is_popular;
    private String size;
    private String phone_number;
    private String updated_at;
    private String created_at;
    private Integer view_count;
    private String cancel_reason;
    private String fullname;
    private String region_name_tm;
    private String region_name_ru;
    private String region_name_en;
    private String district_name_tm;
    private String district_name_ru;
    private String district_name_en;
    private String address;
    private String profile_image;
    private String email;
    private Integer gender;
    private String user_phone_number;
    private String sub_category_name_en;
    private String sub_category_name_ru;
    private String sub_category_name_tm;
    private String user_type;
    private Integer product_limit;
    private Integer isfav;
    private ArrayList<Images> images=new ArrayList<>();

    // For ads
    private Ads ads;

    private String layout_type;

    public Product(Integer id, String product_name, Double price, Integer status, String description, Integer sub_category_id, Integer user_id, Boolean is_popular, String size, String phone_number, String updated_at, String created_at, Integer view_count, String cancel_reason, String fullname, String region_name_tm, String region_name_ru, String region_name_en, String district_name_tm, String district_name_ru, String district_name_en, String address, String profile_image, String email, Integer gender, String user_phone_number, String sub_category_name_en, String sub_category_name_ru, String sub_category_name_tm, String user_type, Integer product_limit, Integer isfav, ArrayList<Images> images) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.status = status;
        this.description = description;
        this.sub_category_id = sub_category_id;
        this.user_id = user_id;
        this.is_popular = is_popular;
        this.size = size;
        this.phone_number = phone_number;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.view_count = view_count;
        this.cancel_reason = cancel_reason;
        this.fullname = fullname;
        this.region_name_tm = region_name_tm;
        this.region_name_ru = region_name_ru;
        this.region_name_en = region_name_en;
        this.district_name_tm = district_name_tm;
        this.district_name_ru = district_name_ru;
        this.district_name_en = district_name_en;
        this.address = address;
        this.profile_image = profile_image;
        this.email = email;
        this.gender = gender;
        this.user_phone_number = user_phone_number;
        this.sub_category_name_en = sub_category_name_en;
        this.sub_category_name_ru = sub_category_name_ru;
        this.sub_category_name_tm = sub_category_name_tm;
        this.user_type = user_type;
        this.product_limit = product_limit;
        this.isfav = isfav;
        this.images = images;
    }

    public Product(Ads ads, String layout_type) {
        this.ads = ads;
        this.layout_type = layout_type;
    }

    public Product(String layout_type) {
        this.layout_type = layout_type;
    }

    public Integer getId() {
        return id;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public String getLayout_type() {
        return layout_type;
    }

    public void setLayout_type(String layout_type) {
        this.layout_type = layout_type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(Integer sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Boolean getIs_popular() {
        return is_popular;
    }

    public void setIs_popular(Boolean is_popular) {
        this.is_popular = is_popular;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getView_count() {
        return view_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRegion_name_tm() {
        return region_name_tm;
    }

    public void setRegion_name_tm(String region_name_tm) {
        this.region_name_tm = region_name_tm;
    }

    public String getRegion_name_ru() {
        return region_name_ru;
    }

    public void setRegion_name_ru(String region_name_ru) {
        this.region_name_ru = region_name_ru;
    }

    public String getRegion_name_en() {
        return region_name_en;
    }

    public void setRegion_name_en(String region_name_en) {
        this.region_name_en = region_name_en;
    }

    public String getDistrict_name_tm() {
        return district_name_tm;
    }

    public void setDistrict_name_tm(String district_name_tm) {
        this.district_name_tm = district_name_tm;
    }

    public String getDistrict_name_ru() {
        return district_name_ru;
    }

    public void setDistrict_name_ru(String district_name_ru) {
        this.district_name_ru = district_name_ru;
    }

    public String getDistrict_name_en() {
        return district_name_en;
    }

    public void setDistrict_name_en(String district_name_en) {
        this.district_name_en = district_name_en;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public String getSub_category_name_en() {
        return sub_category_name_en;
    }

    public void setSub_category_name_en(String sub_category_name_en) {
        this.sub_category_name_en = sub_category_name_en;
    }

    public String getSub_category_name_ru() {
        return sub_category_name_ru;
    }

    public void setSub_category_name_ru(String sub_category_name_ru) {
        this.sub_category_name_ru = sub_category_name_ru;
    }

    public String getSub_category_name_tm() {
        return sub_category_name_tm;
    }

    public void setSub_category_name_tm(String sub_category_name_tm) {
        this.sub_category_name_tm = sub_category_name_tm;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public Integer getProduct_limit() {
        return product_limit;
    }

    public void setProduct_limit(Integer product_limit) {
        this.product_limit = product_limit;
    }

    public Integer getIsfav() {
        return isfav;
    }

    public void setIsfav(Integer isfav) {
        this.isfav = isfav;
    }

    public ArrayList<Images> getImages() {
        return images;
    }

    public void setImages(ArrayList<Images> images) {
        this.images = images;
    }

    public static class Images{
        private Integer id;
        private String small_image;
        private String large_image;
        private Integer product_id;
        private Boolean is_first;
        private String created_at;
        private String updated_at;

        public Images(Integer id, String small_image, String large_image, Integer product_id, Boolean is_first, String created_at, String updated_at) {
            this.id = id;
            this.small_image = small_image;
            this.large_image = large_image;
            this.product_id = product_id;
            this.is_first = is_first;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSmall_image() {
            return small_image;
        }

        public void setSmall_image(String small_image) {
            this.small_image = small_image;
        }

        public String getLarge_image() {
            return large_image;
        }

        public void setLarge_image(String large_image) {
            this.large_image = large_image;
        }

        public Integer getProduct_id() {
            return product_id;
        }

        public void setProduct_id(Integer product_id) {
            this.product_id = product_id;
        }

        public Boolean getIs_first() {
            return is_first;
        }

        public void setIs_first(Boolean is_first) {
            this.is_first = is_first;
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
    }
}
