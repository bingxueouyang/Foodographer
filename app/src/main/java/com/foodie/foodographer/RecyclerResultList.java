//https://stackoverflow.com/questions/30751870/can-one-use-cardview-for-listview-item-and-how

package com.foodie.foodographer;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;
import android.view.*;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yelp.fusion.client.models.Business;


public class RecyclerResultList extends RecyclerView.Adapter<RecyclerResultList.MyViewHolder> {
    private ArrayList<Restaurant> rest_list;
    private Context context;
    private DatabaseReference restRef = FirebaseDatabase.getInstance().getReference("Restaurants");
    private boolean[] restaurantIsInDB;

    public RecyclerResultList (ArrayList<Restaurant> rest_list){
        this.rest_list = rest_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_card, parent, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Restaurant restaurant = rest_list.get(position);
        final Restaurant myRest = restaurant;

        final String restDistance = String.format("%.2f",(restaurant.getDistance() * 0.00062137)) + " mi";
        holder.restName.setText(myRest.getName());
        holder.restRating.setRating(myRest.getRating());
        holder.restLocation.setText(myRest.getLocation());
        holder.restDistance.setText(restDistance);

        new DownloadImageTask(holder.restIMG).execute(myRest.getIMGURL());

        // go to RestaurantInfo page on click
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // save the restaurant into our database whenever the user click on it
                restRef.child(myRest.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Log.i("database info", "gotoRestaurant is called!");
                            gotoRestaurantInfo(myRest);
                            return;
                        }
                        else {
                            //the restaurant is not created yet; create it first
                            restRef.child(myRest.getId()).setValue(myRest);
                            gotoRestaurantInfo(myRest);
                            return;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    public void gotoRestaurantInfo(Restaurant myRest){
        Intent intent = new Intent(context, RestaurantInfo.class);
        intent.putExtra("myRest", myRest);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return rest_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView restName;
        private RatingBar restRating;
        private TextView restLocation;
        private ImageView restIMG;
        private TextView restDistance;
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