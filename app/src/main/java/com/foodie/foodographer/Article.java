/**
 * Article class for Article list and Article page
 */
package com.foodie.foodographer;

public class Article {
    private String title;
    private String IMGURL;
    private String articleURL;

    public Article(String title, String IMGURL, String articleURL) {
        this.title = title;
        this.IMGURL = IMGURL;
        this.articleURL = articleURL;
    }

    // article title
    public String getTitle() {
        return this.title;
    }

    // article img url
    public String getIMGURL() {
        return this.IMGURL;
    }

    // get url
    public String getArticleURL() {
        return this.articleURL;
    }
}
