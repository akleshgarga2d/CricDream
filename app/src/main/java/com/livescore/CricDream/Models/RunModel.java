package com.livescore.CricDream.Models;

public class RunModel {
    String over,run,ball;

    public RunModel(String over, String run, String ball) {
        this.over = over;
        this.run = run;
        this.ball = ball;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }

    public RunModel() {

    }

}
