/*
 * https://stackoverflow.com/questions/30751870/can-one-use-cardview-for-listview-item-and-how
 * An adapter for restaurant list
 */

package com.foodie.foodographer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import android.view.*;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerResultList extends RecyclerView.Adapter<RecyclerResultList.MyViewHolder> {
    private ArrayList<Restaurant> rest_list;
    private Context context;
    private boolean[] restaurantIsInDB;
    private FirebaseAuth mAuthSetting;
    private String currentUserID;
    private DatabaseReference profileReferRecent;
    private boolean checkuser;
    
    public RecyclerResultList (ArrayList<Restaurant> rest_list){
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // set view content with article object value
        Restaurant restaurant = rest_list.get(position);
        final Restaurant myRest = restaurant;
        final String restDistance = String
            .format("%.2f",(restaurant.getDistance() * 0.00062137)) + " mi";
        holder.restName.setText(myRest.getName());
        holder.restRating.setRating(myRest.getRating());
        holder.restLocation.setText(myRest.getLocation());
        holder.restDistance.setText(restDistance);
        
        // download restaurant image from url
        new DownloadImageTask(holder.restIMG).execute(myRest.getIMGURL());
    
        // get recently view from database
        mAuthSetting = FirebaseAuth.getInstance();
        if(mAuthSetting.getCurrentUser()!=null) {
            checkuser = true;
            currentUserID = mAuthSetting.getCurrentUser().getUid();
            profileReferRecent = FirebaseDatabase.getInstance().getReference()
                .child("users").child(currentUserID).child("RecentView");
        }
        
        // check if current user is guest
        if(mAuthSetting.getCurrentUser()==null){
            checkuser=false;
        }
        
        // insert recently viewed restaurant
        final HashMap recentUserView =new HashMap();
        recentUserView.put(myRest.getId(),myRest.getName());
        
        // go to RestaurantInfo page on click
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkuser==true) {
                    Log.d("checkuser","checking user exist:"+checkuser);
                    // update recently view to user in database
                    profileReferRecent.updateChildren(recentUserView);
                }
                gotoRestaurantInfo(myRest);
            }
        });
    }

    public void gotoRestaurantInfo(Restaurant myRest){
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
        // find views and assign to holder
        public MyViewHolder(View itemView) {
            super(itemView);
            restName = (TextView) itemView.findViewById(R.id.rest_name);
            restRating = (RatingBar) itemView.findViewById(R.id.rest_rating);
            restLocation = (TextView) itemView.findViewById(R.id.rest_location);
            restIMG = (ImageView) itemView.findViewById(R.id.rest_IMG);
            restDistance = (TextView) itemView.findViewById(R.id.rest_distance);
        }
    }
}
