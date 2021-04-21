package com.livescore.CricDream.Models;

public class LiveListItem {
    String match_key,flag1,flag2,match_status,status_overview,team1_name,team2_name,
            team1_color,team2_color,dateTime,venue,type,related_name,team1_runs,team2_runs,team1_overs,team2_overs,time_str,name,
            wicket1,wicket2,status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getStatus_overview() {
        return status_overview;
    }

    public String getflag2() {
        return flag2;
    }

    public String getMatch_key() {
        return match_key;
    }

    public void setMatch_key(String match_key) {
        this.match_key = match_key;
    }

    public void setflag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getflag1() {
        return flag1;
    }

    public void setflag1(String flag1) {
        this.flag1 = flag1;
    }

    public void setStatus_overview(String status_overview) {
        this.status_overview = status_overview;
    }

    public LiveListItem() {
    }

    public String getTeam1_runs() {
        return team1_runs;
    }

    public String getTime_str() {
        return time_str;
    }

    public void setTime_str(String time_str) {
        this.time_str = time_str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public LiveListItem(String match_key,String team1_name, String team2_name, String related_name, String team1_runs, String team2_runs, String team1_overs, String team2_overs, String name, String venue, String datetime, String status_overview, String status,String flag1,String flag2,String wicket1,String wicket2,String statu) {
        this.match_key = match_key;
        this.team1_name = team1_name;
        this.team2_name = team2_name;
        this.related_name = related_name;
        this.team1_runs = team1_runs;
        this.team2_runs = team2_runs;
        this.team1_overs = team1_overs;
        this.team2_overs = team2_overs;
        this.name=name;
        this.venue=venue;
        this.dateTime=datetime;
        this.status_overview=status_overview;
        this.match_status=status;
        this.flag1=flag1;
        this.flag2=flag2;
        this.wicket1=wicket1;
        this.wicket2=wicket2;
        this.status=statu;

    }

    public String getWicket1() {
        return wicket1;
    }

    public void setWicket1(String wicket1) {
        this.wicket1 = wicket1;
    }

    public String getWicket2() {
        return wicket2;
    }

    public void setWicket2(String wicket2) {
        this.wicket2 = wicket2;
    }

    public void setTeam1_runs(String team1_runs) {
        this.team1_runs = team1_runs;
    }

    public String getTeam2_runs() {
        return team2_runs;
    }

    public void setTeam2_runs(String team2_runs) {
        this.team2_runs = team2_runs;
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

    public String getRelated_name() {
        return related_name;
    }

    public void setRelated_name(String related_name) {
        this.related_name = related_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public String getTeam1_color() {
        return team1_color;
    }

    public void setTeam1_color(String team1_color) {
        this.team1_color = team1_color;
    }

    public String getTeam2_color() {
        return team2_color;
    }

    public void setTeam2_color(String team2_color) {
        this.team2_color = team2_color;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
