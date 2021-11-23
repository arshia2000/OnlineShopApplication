package com.example.digicala.entity;

public class ImageCategory {

    int img_source;
    String description;
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg_source() {
        return img_source;
    }

    public void setImg_source(int img_source) {
        this.img_source = img_source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageCategory(int img_source, String description, String title) {
        this.img_source = img_source;
        this.description = description;
        this.title = title;
    }

    public ImageCategory() {
    }
}
