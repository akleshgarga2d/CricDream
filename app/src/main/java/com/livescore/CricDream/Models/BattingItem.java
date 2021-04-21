package com.livescore.CricDream.Models;

public class BattingItem {
    String bat_name,bat_run,bat_four,bat_six,bat_strike_rate,balls;

    public String getBat_name() {
        return bat_name;
    }



    public BattingItem() {
        this.bat_name = "";
        this.bat_run = "";
        this.bat_four = "";
        this.bat_six = "";
        this.bat_strike_rate = "";
        this.balls = "";
    }
 public BattingItem(String bat_name, String bat_run, String bat_four, String bat_six, String bat_strike_rate,String balls) {
        this.bat_name = bat_name;
        this.bat_run = bat_run;
        this.bat_four = bat_four;
        this.bat_six = bat_six;
        this.bat_strike_rate = bat_strike_rate;
        this.balls = balls;
    }

    public void setBat_name(String bat_name) {
        this.bat_name = bat_name;
    }

    public String getBat_run() {
        return bat_run;
    }

    public void setBat_run(String bat_run) {
        this.bat_run = bat_run;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getBat_four() {
        return bat_four;
    }

    public void setBat_four(String bat_four) {
        this.bat_four = bat_four;
    }

    public String getBat_six() {
        return bat_six;
    }

    public void setBat_six(String bat_six) {
        this.bat_six = bat_six;
    }

    public String getBat_strike_rate() {
        return bat_strike_rate;
    }

    public void setBat_strike_rate(String bat_strike_rate) {
        this.bat_strike_rate = bat_strike_rate;
    }
}
