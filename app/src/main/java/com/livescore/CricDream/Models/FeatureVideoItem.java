package com.livescore.CricDream.Models;

public class FeatureVideoItem {
    String videoId,videoUrl,video_im,video_title;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public FeatureVideoItem() {
    }

    public FeatureVideoItem(String videoId, String videoUrl, String video_im, String video_title) {
        this.videoId = videoId;
        this.videoUrl = videoUrl;
        this.video_im = video_im;
        this.video_title = video_title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideo_im() {
        return video_im;
    }

    public void setVideo_im(String video_im) {
        this.video_im = video_im;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }
}
