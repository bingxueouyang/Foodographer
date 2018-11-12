package com.foodie.foodographer;

public class Article {
    private String title;
    private String IMGURL;
    private String articleURL;
    public Article(String title, String IMGURL, String articleURL){
        this.title = title;
        this.IMGURL = IMGURL;
        this.articleURL = articleURL;
    }
    public String getTitle(){
        return this.title;
    }
    public String getIMGURL(){
        return this.IMGURL;
    }
    public String getArticleURL(){return this.articleURL;}
}
