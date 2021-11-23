package com.example.digicala.entity;

public class Product {

    private int id;
    private String title;
    private String price;
    private String img;
    private String cat;
    private String information;
    private String isimage;
    private String exists;
    private int dicount;
    private int rate;
    private String order;


    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }


    public String isIsimage() {
        return isimage;
    }

    public void setIsimage(String isimage) {
        this.isimage = isimage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String isExists() {
        return exists;
    }

    public void setExists(String exists) {
        this.exists = exists;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getOff() {
        return dicount;
    }

    public void setOff(int off) {
        this.dicount = off;
    }


    public Product(int id, String title, String price, String img, String cat, String information, String isimage, String exists, int dicount, int rate, String order) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.img = img;
        this.cat = cat;
        this.information = information;
        this.isimage = isimage;
        this.exists = exists;
        this.dicount = dicount;
        this.rate = rate;
        this.order = order;
    }


    public Product() {
    }

    public Product(int id, String title, String price, String img, String cat, String exists, int off, String isimage, String information) {

        this.id = id;
        this.title = title;
        this.price = price;
        this.img = img;
        this.cat = cat;
        this.exists = exists;
        this.dicount = off;
        this.isimage = isimage;
        this.information = information;
    }


}
