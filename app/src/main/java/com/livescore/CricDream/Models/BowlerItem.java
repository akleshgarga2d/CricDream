package com.livescore.CricDream.Models;

public class BowlerItem
{
    String bowler_name,bowler_over,bowler_run,bowler_wicket,bowler_eco,bowler_maiden_overs;


    public BowlerItem() {
        this.bowler_name = "";
        this.bowler_over = "";
        this.bowler_run = "";
        this.bowler_wicket = "";
        this.bowler_eco = "";
        this.bowler_maiden_overs = "";
    }


    public BowlerItem(String bowler_name, String bowler_over, String bowler_run, String bowler_wicket, String bowler_eco, String bowler_maiden_overs) {
        this.bowler_name = bowler_name;
        this.bowler_over = bowler_over;
        this.bowler_run = bowler_run;
        this.bowler_wicket = bowler_wicket;
        this.bowler_eco = bowler_eco;
        this.bowler_maiden_overs = bowler_maiden_overs;
    }

    public String getBowler_name() {
        return bowler_name;
    }

    public void setBowler_name(String bowler_name) {
        this.bowler_name = bowler_name;
    }

    public String getBowler_over() {
        return bowler_over;
    }

    public void setBowler_over(String bowler_over) {
        this.bowler_over = bowler_over;
    }

    public String getBowler_run() {
        return bowler_run;
    }

    public void setBowler_run(String bowler_run) {
        this.bowler_run = bowler_run;
    }

    public String getBowler_wicket() {
        return bowler_wicket;
    }

    public void setBowler_wicket(String bowler_wicket) {
        this.bowler_wicket = bowler_wicket;
    }

    public String getBowler_eco() {
        return bowler_eco;
    }

    public void setBowler_eco(String bowler_eco) {
        this.bowler_eco = bowler_eco;
    }

    public String getBowler_maiden_overs() {
        return bowler_maiden_overs;
    }

    public void setBowler_maiden_overs(String bowler_maiden_overs) {
        this.bowler_maiden_overs = bowler_maiden_overs;
    }
}
