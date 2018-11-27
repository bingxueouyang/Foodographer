package com.foodie.foodographer;

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

public class UserCommentRestaurant extends AppCompatActivity {
    public static ArrayList<String> restaurantIDList; //ArrayList to store all of user's recent comment restaurant IDs
    //ArrayList to store user's recent comment as Review Objects
    ArrayList<Review> favoriteReviewList;
    //DB reference to current user
    DatabaseReference currentUserReference;
    //DB reference to Restaurants root
    DatabaseReference restaurantReference;
    //RecyclerView element in "activity_user_favorite_restaurant.xml"
    RecyclerView commentView;
    //DB authentication reference
    FirebaseAuth mAuthSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setting up to connect firebase
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comment_restaurant);
        commentView= (RecyclerView)findViewById(R.id.commentUserRecyclerView);
        mAuthSetting = FirebaseAuth.getInstance();
        String currentUserID = mAuthSetting.getCurrentUser().getUid();
        currentUserReference = FirebaseDatabase.getInstance().getReference("users").child(currentUserID);
        restaurantReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        //initialize two container lists
        restaurantIDList = new ArrayList<>();
        favoriteReviewList = new ArrayList<>();
        currentUserReference.child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //need to clear first before adding/updating our arraylist, since we are adding
                //everything at once
                restaurantIDList.clear();
                favoriteReviewList.clear();
                //for every child under user's "comments"
                for (DataSnapshot commentRestaurantID : dataSnapshot.getChildren()) {
                    Log.i("comment", "Current comments is: " + commentRestaurantID.getKey());
                    restaurantIDList.add(commentRestaurantID.getKey());
                    Review thisComment = commentRestaurantID.getValue(Review.class);
                    favoriteReviewList.add(thisComment);
                    Log.i("commentTocheck", "Restaurant added:" + thisComment.getUsername());
                    Log.i("commenNOT", "IMGURL: " + thisComment.getUserIMGURL());
                }
                RecyclerReviewList adapter = new RecyclerReviewList(favoriteReviewList);
                commentView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //these are some basic settings for the RecyclerView
        commentView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        commentView.setLayoutManager(llm);
    }
}
