package com.foodie.foodographer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Homepage extends AppCompatActivity
        implements Home_fragment.OnFragmentInteractionListener,
        user_profile_fragment.OnFragmentInteractionListener,
        SearchBar_fragment.OnFragmentInteractionListener,
        search_result_fragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        account_setting_fragment.OnFragmentInteractionListener{

    private LinearLayout search_bar;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new Home_fragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_recommend:
                    mTextMessage.setText(R.string.title_recommend);
                    return true;
                case R.id.navigation_profile:
                    fragment = new user_profile_fragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //create Navigaton Bar
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //set the default fragment to home
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame_container, new Home_fragment());
        tx.commit();
        //search_bar = (LinearLayout)findViewById(R.id.search);
        //search_bar.setOnClickListener(new View.OnClickListener() {
//
        //     @Override
        //  public void onClick(View v) {
        //      gotoSearchBar();
        //   }
        //});

        //ArrayList<Article> articles = new ArrayList<Article>(10);
        //articles.add(new Article("Hello Hamburger","https://amp.businessinsider.com/images/5a7dc169d03072af008b4bf2-750-562.jpg","https://en.m.wikipedia.org/w/index.php?title=Hamburger&mobileaction=toggle_view_mobile"));
        //articles.add(new Article("Hello XLB","https://daily.jstor.org/wp-content/uploads/2017/11/dim_sum_dumplings_1050x700.jpg","https://en.m.wikipedia.org/wiki/Xiaolongbao"));
        //articles.add(new Article("Hello JianBing","https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D400/sign=57ab32c9cfcec3fd8b3ea675e689d4b6/a50f4bfbfbedab64edf682c5fb36afc379311e2b.jpg","https://en.m.wikipedia.org/wiki/Jianbing"));
        //articles.add(new Article("Hello Red Bean Double Skin Milk","https://media-cdn.tripadvisor.com/media/daodao/photo-s/04/19/9e/1a/caption.jpg","https://en.m.wikipedia.org/wiki/Double_skin_milk"));
        //articles.add(new Article("Hello Crab","https://cp1.douguo.com/upload/caiku/3/9/b/600x400_39fa22c44f4f02d73e5c1fd953c0171b.jpg", "https://learnchinesefood.com/detail%3D9807"));

        // making articles recycle
        //RecyclerArticleList adapter = new RecyclerArticleList(articles);
        //RecyclerView myView =  (RecyclerView)findViewById(R.id.recyclerview);
        //myView.setHasFixedSize(true);
        //myView.setAdapter(adapter);
        //LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        //myView.setLayoutManager(llm);

    }

    public void gotoSearchBar() {

        startActivity(new Intent(this, SearchBar.class));
    }

    public void gotoProfile(MenuItem item){

        startActivity(new Intent(this, UserProfile.class));
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}