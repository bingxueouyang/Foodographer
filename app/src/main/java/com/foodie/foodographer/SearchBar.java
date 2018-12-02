package com.foodie.foodographer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchBar extends AppCompatActivity {
    String name;
    String location;
    android.widget.SearchView nameSearch;
    android.widget.SearchView locationSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        nameSearch = findViewById(R.id.name_search);
        nameSearch.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                gotoSearch(name, location);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                name = nameSearch.getQuery().toString();
                return true;
            }
        });
        locationSearch = findViewById(R.id.location_search);
        locationSearch.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                gotoSearch(name, location);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                location = locationSearch.getQuery().toString();
                return true;
            }
        });
    }

    public void gotoSearch(String rest_name, String rest_location) {

        Intent intent = new Intent(SearchBar.this, SearchResult.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", rest_name);
        bundle.putString("location", rest_location);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}