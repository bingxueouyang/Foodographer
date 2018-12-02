package com.foodie.foodographer;


public class Review {
    private String username;
    private String userIMGURL;
    private Float rating;
    private String time;
    private String content;

    public Review() {
    }

    public Review(String username, String userIMGURL, Float rating, String time, String content) {
        this.username = username;
        this.userIMGURL = userIMGURL;
        this.rating = rating;
        this.time = time;
        this.content = content;
    }

    public String getUsername() {
        return this.username;
    }

    public String getUserIMGURL() {
        return this.userIMGURL;
    }

    public Float getRating() {
        return this.rating;
    }

    public String getTime() {
        return this.time;
    }

    public String getContent() {
        return this.content;
    }

}