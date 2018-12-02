/*
 * https://stackoverflow.com/questions/30751870/can-one-use-cardview-for-listview-item-and-how
 * An adapter for restaurant list
 */

package com.foodie.foodographer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import android.view.*;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecyclerResultList extends RecyclerView.Adapter<RecyclerResultList.MyViewHolder> {
    private ArrayList<Restaurant> rest_list;
    private Context context;
    // boolean for checking if restaurant is in database
    private boolean[] restaurantIsInDB;
    // firebase authorize object
    private FirebaseAuth mAuthSetting;
    // save current user id
    private String currentUserID;
    // user profile referece in database
    private DatabaseReference profileReferRecent;
    //restaurant reference in databse
    private DatabaseReference restaurantReference;
    // boolean for checking if current user is guest
    private boolean checkuser;

    public RecyclerResultList(ArrayList<Restaurant> rest_list) {
        this.rest_list = rest_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the list view with holder
        context = parent.getContext();
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_card, parent, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // set view content with restaurant object value
        Restaurant restaurant = rest_list.get(position);
        restaurantReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        final Restaurant myRest = restaurant;
        final String restDistance = String
                .format("%.2f", (restaurant.getDistance() * 0.00062137)) + " mi";
        //inflate the review numbers and ratings by our own comments in the database (if they exist)
        restaurantReference.child(myRest.getId()).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if there are comments in this restaurant, display the number of comments
                //and our own rating based on user comments
                if (dataSnapshot.exists()) {
                    long commentNumber = dataSnapshot.getChildrenCount();
                    //change the displaying string according to comment number
                    String reviewString = (commentNumber == 1) ? "Review" : "Reviews";
                    //set the display comment number
                    holder.restReviewNumber.setText(commentNumber + " " + reviewString);
                    float totalReviewPoints = 0;
                    //traverse through all comment ratings and calculate the final rating score
                    for (DataSnapshot myComment : dataSnapshot.getChildren()) {
                        float currentRating = myComment.child("rating").getValue(Float.class);
                        totalReviewPoints += currentRating;
                    }
                    float finalRatingPoints = totalReviewPoints / commentNumber;
                    //display our own rating score in the rating bar
                    holder.restRating.setRating(finalRatingPoints);
                } else {//if there are no comments yet, display 0 comments and Yelp rating score
                    holder.restReviewNumber.setText("No Reviews \n(Yelp Rating)");
                    holder.restRating.setRating(myRest.getRating());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.restName.setText(myRest.getName());
        holder.restLocation.setText(myRest.getLocation());
        holder.restDistance.setText(restDistance);

        // download restaurant image from url
        new DownloadImageTask(holder.restIMG).execute(myRest.getIMGURL());

        // get recently view from database
        mAuthSetting = FirebaseAuth.getInstance();
        if (mAuthSetting.getCurrentUser() != null) {
            checkuser = true;
            currentUserID = mAuthSetting.getCurrentUser().getUid();
            profileReferRecent = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(currentUserID).child("RecentView");
        }

        // check if current user is guest
        if (mAuthSetting.getCurrentUser() == null) {
            checkuser = false;
        }

        // insert recently viewed restaurant
        final HashMap recentUserView = new HashMap();
        recentUserView.put(myRest.getId(), myRest.getName());

        // go to RestaurantInfo page on click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkuser == true) {
                    Log.d("checkuser", "checking user exist:" + checkuser);
                    // update recently view to user in database
                    profileReferRecent.updateChildren(recentUserView);
                }
                gotoRestaurantInfo(myRest);
            }
        });
    }

    public void gotoRestaurantInfo(Restaurant myRest) {
        Intent intent = new Intent(context, RestaurantInfo.class);
        // pass restaurant object to restaurant info page
        intent.putExtra("myRest", myRest);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return rest_list.size();
    }

    // holder object
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView restName;
        private RatingBar restRating;
        private TextView restLocation;
        private ImageView restIMG;
        private TextView restDistance;
        private TextView restReviewNumber;

        // find views and assign to holder
        public MyViewHolder(View itemView) {
            super(itemView);
            restName = (TextView) itemView.findViewById(R.id.rest_name);
            restRating = (RatingBar) itemView.findViewById(R.id.rest_rating);
            restLocation = (TextView) itemView.findViewById(R.id.rest_location);
            restIMG = (ImageView) itemView.findViewById(R.id.rest_IMG);
            restDistance = (TextView) itemView.findViewById(R.id.rest_distance);
            restReviewNumber = (TextView) itemView.findViewById(R.id.rest_reviews);
        }
    }
}