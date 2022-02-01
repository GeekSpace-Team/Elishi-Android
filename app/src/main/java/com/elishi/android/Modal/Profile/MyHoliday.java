package com.elishi.android.Modal.Profile;

public class MyHoliday {
    private Integer id;
    private String image;
    private String name;
    private String date;
    private String desc;

    public MyHoliday(Integer id, String image, String name, String date, String desc) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.date = date;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
