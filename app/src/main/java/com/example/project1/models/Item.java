package com.example.project1.models;

public class Item {

    int background;
    String profileName;
    int profilePhoto;


    public Item() {
    }

    public Item(int background, String profileName, int profilePhoto) {
        this.background = background;
        this.profileName = profileName;
        this.profilePhoto = profilePhoto;
    }

    public int getBackground() {
        return background;
    }

    public String getProfileName() {
        return profileName;
    }

    public int getProfilePhoto() {
        return profilePhoto;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setProfilePhoto(int profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
