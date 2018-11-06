package com.foodie.foodographer;

public class Review {
    private String username;
    private String IMGURL;
    private Float rating;
    private String time;
    private String content;

    public Review(String IMGURL, String username, Float rating, String time, String content) {
        this.IMGURL = IMGURL;
        this.username = username;
        this.rating = rating;
        this.time = time;
        this.content = content;
    }

    public String getUsername() {
        return this.username;
    }

    public String getIMGURL() {
        return this.IMGURL;
    }

    public Float getRating() {
        return this.rating;
    }

    public String getTime(){
        return this.time;
    }

    public String getContent(){
        return this.content;
    }

}