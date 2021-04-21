package com.livescore.CricDream.Models;

import java.io.Serializable;

public class NewsItem implements Serializable {
    String title,time,description,news_im,news_url,thb, image,cap,aut;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public NewsItem(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public NewsItem(String time, String title, String description, String news_url, String image, String thb, String cap, String aut) {
        this.time = time;
        this.title = title;
        this.description = description;
        this.news_url = news_url;
        this.image = image;
        this.thb = thb;
        this.cap = cap;
        this.aut = aut;
    }

    public NewsItem(String title, String time, String description, String news_im, String news_url, String image) {
        this.title = title;
        this.time = time;
        this.description = description;
        this.news_im = news_im;
        this.news_url = news_url;
        this.image = image;
    }

    public String getThb() {
        return thb;
    }

    public void setThb(String thb) {
        this.thb = thb;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getAut() {
        return aut;
    }

    public void setAut(String aut) {
        this.aut = aut;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNews_im() {
        return news_im;
    }

    public void setNews_im(String news_im) {
        this.news_im = news_im;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
