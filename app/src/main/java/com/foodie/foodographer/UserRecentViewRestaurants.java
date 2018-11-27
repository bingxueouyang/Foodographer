package com.foodie.foodographer;
// import eveything needed for this java class
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class UserRecentViewRestaurants extends AppCompatActivity {
    ArrayList<String> restaurantIDList; //ArrayList to store all of user's recentview restaurant IDs
    //ArrayList to store user's recentview restaurant as Restaurant Objects
    ArrayList<Restaurant> recentViewRestaurantList;
    //DB reference to current user
    DatabaseReference currentUserReference;
    //DB reference to Restaurants root
    DatabaseReference restaurantReference;
    //RecyclerView element in "activity_user_favorite_restaurant.xml"
    RecyclerView userRecentView;
    //DB authentication reference
    FirebaseAuth mAuthSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recent_view_restaurants);
        //link layout RecyclerView elements
        userRecentView = (RecyclerView) findViewById(R.id.recentViewRecyclerView);
        //find current user and set up DB references
        mAuthSetting = FirebaseAuth.getInstance();
        String currentUserID = mAuthSetting.getCurrentUser().getUid();
        currentUserReference = FirebaseDatabase.getInstance().getReference("users").child(currentUserID);
        restaurantReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        //initialize two container lists
        restaurantIDList = new ArrayList<>();
        recentViewRestaurantList= new ArrayList<>();


        /**
         * If we find user's recent view restaurant list changed, we would first update favoriteIDlist to the
         * newest version, and update the restaurantList as well. Kind of messy down here.
         * Could be optimized as this gets laggy/slow when the user have a lot of recent view restaurants
         */
        currentUserReference.child("RecentView").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //need to clear first before adding/updating our arraylist, since we are adding
                //everything at once
                restaurantIDList.clear();
                //for every child under user's "favorite"
                for (DataSnapshot favoriteRestaurantID : dataSnapshot.getChildren()) {
                    Log.i("recentview", "Current recentview is: " + favoriteRestaurantID.getKey());
                    restaurantIDList.add(favoriteRestaurantID.getKey());
                }
                //begin to fetch Restaurant Objects from DB's restaurant reference
                restaurantReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        recentViewRestaurantList.clear();
                        //add each restaurant object for each favorite restaurant ID
                        for (String this_restaurantID : restaurantIDList) {
                            if (dataSnapshot.child(this_restaurantID).exists()) {
                                Log.i("favorite", this_restaurantID + " exists!");
                                String imgurl = dataSnapshot.child(this_restaurantID).child("imgurl").getValue().toString();
                                Log.i("favorite", "imgurl: " + imgurl);
                                Restaurant this_restaurant = dataSnapshot.child(this_restaurantID).getValue(Restaurant.class);
                                recentViewRestaurantList.add(this_restaurant);
                                Log.i("favorite", "Restaurant added:" + this_restaurant.getName());
                                Log.i("favorite", "IMGURL: " + this_restaurant.getIMGURL());
                            }
                        }
                        //update our recyclerView as data changed
                        //notice we are using "RecyclerResultList", the same as searchresult.
                        RecyclerResultList adapter = new RecyclerResultList(recentViewRestaurantList);
                        userRecentView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //these are some basic settings for the RecyclerView
        userRecentView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        userRecentView.setLayoutManager(llm);
    }
}
