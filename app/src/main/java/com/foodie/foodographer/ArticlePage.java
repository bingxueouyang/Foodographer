package com.foodie.foodographer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class ArticlePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        WebView web = findViewById(R.id.webview);

        String webURL = getIntent().getStringExtra("webURL");
        web.loadUrl(webURL);

    }

}