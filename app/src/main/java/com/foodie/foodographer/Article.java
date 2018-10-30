package com.foodie.foodographer;

import android.widget.ImageView;

public class Article {
    private String title;
    private String IMGURL;
    public Article(String title, String IMGURL){
        this.title = title;
        this.IMGURL = IMGURL;
    }
    public String getTitle(){
        return this.title;
    }
    public String getIMGURL(){
        return this.IMGURL;
    }
}
