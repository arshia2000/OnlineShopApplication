package com.example.digicala.entity;

public class BookProduct {

    private String title;
    private String athor;
    private int img;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAthor() {
        return athor;
    }

    public void setAthor(String athor) {
        this.athor = athor;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public BookProduct() {
    }

    public BookProduct(String title, String athor, int img, String url) {
        this.title = title;
        this.athor = athor;
        this.img = img;
        this.url = url;
    }
}
