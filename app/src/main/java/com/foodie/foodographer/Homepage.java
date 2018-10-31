package com.foodie.foodographer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.SearchView;
import java.util.ArrayList;

public class Homepage extends AppCompatActivity {
    private SearchView search_bar;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_recommend:
                    mTextMessage.setText(R.string.title_recommend);
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        search_bar = findViewById(R.id.search);
        search_bar.setOnSearchClickListener(new android.widget.SearchView.OnClickListener() {

            @Override
            public void onClick(View v) {
                gotoSearchBar();
            }
        });

        ArrayList<Article> articles = new ArrayList<Article>(10);
        articles.add(new Article("Hello Hamburger","https://amp.businessinsider.com/images/5a7dc169d03072af008b4bf2-750-562.jpg"));
        articles.add(new Article("Hello XLB","https://daily.jstor.org/wp-content/uploads/2017/11/dim_sum_dumplings_1050x700.jpg"));
        articles.add(new Article("Hello JianBing","https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D400/sign=57ab32c9cfcec3fd8b3ea675e689d4b6/a50f4bfbfbedab64edf682c5fb36afc379311e2b.jpg"));
        articles.add(new Article("Hello Red Bean","https://media-cdn.tripadvisor.com/media/daodao/photo-s/04/19/9e/1a/caption.jpg"));
        articles.add(new Article("Hello Crab","https://cp1.douguo.com/upload/caiku/3/9/b/600x400_39fa22c44f4f02d73e5c1fd953c0171b.jpg"));

        // making articles recycle
        RecyclerArticleList adapter = new RecyclerArticleList(articles);
        RecyclerView myView =  (RecyclerView)findViewById(R.id.recyclerview);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);

    }

    public void gotoSearchBar() {

        startActivity(new Intent(this, SearchBar.class));
    }

    public void gotoUser(MenuItem item){

        startActivity(new Intent(this, LogIn.class));
    }

}