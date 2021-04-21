package com.livescore.CricDream.Models;

public class RecentItem
{
    String match_key, match_title, match_name,match_date,match_related_name,match_venue,
            team1_name,team2_name,flag1,flag2,match_status,wicket,wicket2,t1,t2,team1_overs,team2_overs,score,score2,status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RecentItem(String match_title, String match_name, String match_date, String match_related_name, String match_venue, String matchkey) {
        this.match_title = match_title;
        this.match_name = match_name;
        this.match_date = match_date;
        this.match_related_name = match_related_name;
        this.match_venue = match_venue;
        this.match_key=matchkey;
    }


    public RecentItem(String match_key, String match_title, String match_name, String match_date, String match_related_name, String match_venue,
                      String team1_name, String team2_name, String flag1, String flag2, String match_status, String wicket, String wicket2,
                      String t1, String t2, String team1_overs, String team2_overs, String score, String score2,String statu) {
        this.match_key = match_key;
        this.match_title = match_title;
        this.match_name = match_name;
        this.match_date = match_date;
        this.match_related_name = match_related_name;
        this.match_venue = match_venue;
        this.team1_name = team1_name;
        this.team2_name = team2_name;
        this.flag1 = flag1;
        this.flag2 = flag2;
        this.match_status = match_status;
        this.wicket = wicket;
        this.wicket2 = wicket2;
        this.t1 = t1;
        this.t2 = t2;
        this.team1_overs = team1_overs;
        this.team2_overs = team2_overs;
        this.score = score;
        this.score2 = score2;
        this.status=statu;
    }

    public String getTeam1_name() {
        return team1_name;
    }

    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

    public String getTeam2_name() {
        return team2_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public String getWicket() {
        return wicket;
    }

    public void setWicket(String wicket) {
        this.wicket = wicket;
    }

    public String getWicket2() {
        return wicket2;
    }

    public void setWicket2(String wicket2) {
        this.wicket2 = wicket2;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getTeam1_overs() {
        return team1_overs;
    }

    public void setTeam1_overs(String team1_overs) {
        this.team1_overs = team1_overs;
    }

    public String getTeam2_overs() {
        return team2_overs;
    }

    public void setTeam2_overs(String team2_overs) {
        this.team2_overs = team2_overs;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getMatch_key() {
        return match_key;
    }

    public void setMatch_key(String match_key) {
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

    public RecentItem() {

    }
}
