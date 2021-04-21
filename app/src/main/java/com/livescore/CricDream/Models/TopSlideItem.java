package com.livescore.CricDream.Models;

public class TopSlideItem {
    String matchkey,flag1,flag2,match_status_overview,match_status,team1_name,team2_name,team1_color,team2_color,
            dateTime,venue,type,related_name,team1_runs,team2_runs,team1_overs,team2_overs,
            time_str,wicket1,wicket2, t1, t2,status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    boolean now;

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

    public String getTeam1_name() {
        return team1_name;
    }

    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

    public String getTeam2_name() {
        return team2_name;
    }


    public boolean isNow() {
        return now;
    }

    public void setNow(boolean now) {
        this.now = now;
    }

    public TopSlideItem() {
    }

    public String getMatch_status() {
        return match_status;
    }

    public String getflag1() {
        return flag1;
    }

    public void setflag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getMatchkey() {
        return matchkey;
    }

    public void setMatchkey(String matchkey) {
        this.matchkey = matchkey;
    }

    public String getflag2() {
        return flag2;
    }

    public void setflag2(String flag2) {
        this.flag2 = flag2;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public String getTime_str() {
        return time_str;
    }

    public void setTime_str(String time_str) {
        this.time_str = time_str;
    }

    public String getTeam1_runs() {
        return team1_runs;
    }

    public TopSlideItem(boolean now_b,String venue,String key,String team1_name, String team2_name, String related_name, String team1_runs,
                        String team2_runs, String team1_overs, String team2_overs,String status,String status_overview,String time_str,
                        String flag1,
                        String flag2,
                        String wicket1,
                        String wicket2,
                        String t1,
                        String t2,String statu

    ) {
        this.now = now_b;
        this.venue = venue;
        this.matchkey = key;
        this.team1_name = team1_name;
        this.team2_name = team2_name;
        this.related_name = related_name;
        this.team1_runs = team1_runs;
        this.team2_runs = team2_runs;
        this.team1_overs = team1_overs;
        this.team2_overs = team2_overs;
        this.match_status=status;
        this.match_status_overview=status_overview;
        this.time_str=time_str;
        this.flag1=flag1;
        this.flag2=flag2;
        this.wicket1=wicket1;
        this.wicket2=wicket2;
        this.t1=t1;
        this.t2=t2;
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

    public String getMatch_status_overview() {
        return match_status_overview;
    }

    public void setMatch_status_overview(String match_status_overview) {
        this.match_status_overview = match_status_overview;
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
