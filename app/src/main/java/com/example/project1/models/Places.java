package com.example.project1.models;

//Create Item class
public class Places {

    //Init private vars
    private String Title;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    private String Description;
    private String image_name;
    private String lat;
    private String lang;
    private String Start;
    private String End;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    private String Category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        this.Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        this.End = end;
    }

    public Places(String title, String image_name, String lat, String lang, String start, String end , String Description, int id , String Category) {
        this.Title = title;
        this.image_name = image_name;
        this.lat = lat;
        this.lang = lang;
        this.Start = start;
        this.End = end;
        this.id = id;
        this.Description = Description;
        this.Category =Category;
    }

    public Places() {
    }


}