package com.example.project1.models;

//Create Item class
public class ItemDetails {
    //Init private vars
    private String itemTitle;
    private int itemImageResourceId;
    private String itemLocation;
    private int itemReviewStar;

    private String[] itemHighlights;


    //Item constructor takes 4 params
    public ItemDetails(String title, int imageResourceId, String location, int reviewStart, String[] stringArray) {
        itemTitle = title;
        itemImageResourceId = imageResourceId;
        itemLocation = location;
        itemReviewStar = reviewStart;
    }

    //Item constructor takes 8 params
    public ItemDetails(String title, int imageResourceId, String location, int reviewStar, String[] highlights, String overview, String provider, float price) {
        itemTitle = title;
        itemImageResourceId = imageResourceId;
        itemLocation = location;
        itemReviewStar = reviewStar;
        itemHighlights = highlights;

    }

    //Getter for Hightlights
    public String[] getHighlights() {
        return itemHighlights;
    }


    //Getter for Title
    public String getTitle() {
        return itemTitle;
    }

    //Getter for ImageResource
    public int getImageResourceId() {
        return itemImageResourceId;
    }

    //Getter for Location
    public String getLocation() {
        return itemLocation;
    }

    //Getter for ReviewStars
    public int getReviewStar() {
        return itemReviewStar;
    }

}
