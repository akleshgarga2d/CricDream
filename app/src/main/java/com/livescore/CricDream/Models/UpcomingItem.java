package com.livescore.CricDream.Models;

public class UpcomingItem {
    String match_title, match_name,match_date,match_related_name,match_venue,match_key;

    public String getMatch_key() {
        return match_key;
    }

    public void setMatch_key(String match_key) {
        this.match_key = match_key;
    }

    public UpcomingItem(String match_title, String match_name, String match_date, String match_related_name, String match_venue,String match_key) {
        this.match_title = match_title;
        this.match_name = match_name;
        this.match_date = match_date;
        this.match_related_name = match_related_name;
        this.match_venue = match_venue;
        this.match_key = match_key;
    }

    public String getMatch_title() {
        return match_title;
    }

    public void setMatch_title(String match_title) {
        this.match_title = match_title;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public String getMatch_related_name() {
        return match_related_name;
    }

    public void setMatch_related_name(String match_related_name) {
        this.match_related_name = match_related_name;
    }

    public String getMatch_venue() {
        return match_venue;
    }

    public void setMatch_venue(String match_venue) {
        this.match_venue = match_venue;
    }

    public UpcomingItem() {

    }
}
