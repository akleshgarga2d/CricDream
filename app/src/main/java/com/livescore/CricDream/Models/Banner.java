package com.livescore.CricDream.Models;

public class Banner
{
//    name	"Baazigar"
//    number	"+919005231790"
//    URL	"https://wa.me/919005231790"
//    banner	"banner/Baazigar.jpeg"


    private String name;
    private String number;
    private String url;
    private String banner;


    public Banner()
    {
        this.name = "";
        this.number = "";
        this.url = "";
        this.banner = "";
    }

    public Banner(String name, String number, String url, String banner) {
        this.name = name;
        this.number = number;
        this.url = url;
        this.banner = banner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
