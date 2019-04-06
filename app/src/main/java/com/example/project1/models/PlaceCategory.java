package com.example.project1.models;

public class PlaceCategory {
    private String  name;
    private String desc;
    private int pic;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    public PlaceCategory(String name, String desc, int pic , String value) {
        this.name = name;
        this.desc = desc;
        this.pic = pic;
        this.value=value;
    }

    public PlaceCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
