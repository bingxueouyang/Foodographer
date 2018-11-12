package com.foodie.foodographer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

public class SearchResult extends AppCompatActivity {

    YelpFusionApiFactory yelpFusionApiFactory;
    YelpFusionApi yelpFusionApi;
    String this_name;
    String this_location;
    ArrayList<Business> businesses;
    ListView list;
    private LinearLayout search_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        thread.start();
        search_bar = findViewById(R.id.search);
        search_bar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gotoSearchBar();
            }
        });
    }
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                yelpFusionApiFactory = new YelpFusionApiFactory();
                yelpFusionApi = yelpFusionApiFactory.createAPI(getString(R.string.apiKey));
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                if(bundle!=null){
                    if(intent.hasExtra("name")){
                        this_name = bundle.getString("name", "No info");
                    }
                    if(intent.hasExtra("location")){
                        this_location = bundle.getString("location", "location");
                    }
                }
                Map<String, String> params = new HashMap<>();
                params.put("term", this_name);
                params.put("location", this_location);
                Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);
                call.enqueue(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
    // Yelp source:
    // https://github.com/ranga543/yelp-fusion-android/blob/master/yelp-fusion-client/src/main/java/com/yelp/fusion/client/models/Business.java
    Callback<SearchResponse> callback = new Callback<SearchResponse>() {

        @Override
        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
            Log.i("yelp", "in response");

            SearchResponse searchResponse = response.body();

            int totalNumberOfResult = searchResponse.getTotal();  // 3
            Log.i("yelp", String.valueOf(totalNumberOfResult));
            if (totalNumberOfResult == 0) {
                Toast.makeText(SearchResult.this, "Sorry, no result was found.", Toast.LENGTH_SHORT).show();
            }
            //generate an array list of result restaurants(businesses)
            businesses = searchResponse.getBusinesses();


            if (businesses.isEmpty()) {
                Log.i("yelp", "result business array is empty!!!");
            }


            RecyclerResultList adapter = new RecyclerResultList(businesses);
            RecyclerView myView =  (RecyclerView)findViewById(R.id.recyclerview);
            myView.setHasFixedSize(true);
            myView.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(SearchResult.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            myView.setLayoutManager(llm);

        }

        @Override
        public void onFailure(Call<SearchResponse> call, Throwable t) {
            // HTTP error happened, do something to handle it.
        }
    };

    public void gotoFilter(View view){

        startActivity(new Intent(this,Filter.class));
    }

    public void gotoSearchBar(){

        startActivity(new Intent(this,SearchBar.class));
    }
}
