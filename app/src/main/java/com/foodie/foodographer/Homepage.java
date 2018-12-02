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

/**
 * The main container Activity of various fragments(dynamic pages). Switched between different
 * fragments as the user interact with the navigation bar at the bottom. The navigation bar
 * consists of three items: home page, recommendation(currently not available), and user profile.
 * The search button(top left) lead the user to the Search Page.
 */
public class Homepage extends AppCompatActivity
        implements HomeFragment.OnFragmentInteractionListener,
        UserProfileFragment.OnFragmentInteractionListener,
        SearchBarFragment.OnFragmentInteractionListener,
        SearchResultFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        AccountSettingFragment.OnFragmentInteractionListener,
        RecommendFragment.OnFragmentInteractionListener {

    //load different fragments as the user clicked on respective navigation items
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_recommend:
                    fragment = new RecommendFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment = new UserProfileFragment();
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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //set the default fragment to home
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame_container, new HomeFragment());
        tx.commit();
    }

    public void gotoSearchBar() {

        startActivity(new Intent(this, SearchBar.class));
    }

    public void gotoProfile(MenuItem item) {

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