//https://stackoverflow.com/questions/30751870/can-one-use-cardview-for-listview-item-and-how

package com.foodie.foodographer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.view.*;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;

import com.yelp.fusion.client.models.Business;


public class RecyclerResultList extends RecyclerView.Adapter<RecyclerResultList.MyViewHolder> {
    private List<Business> rest_list;
    private Context context;
    public RecyclerResultList (ArrayList<Business> rest_list){
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

        Business restaurant = rest_list.get(position);
        final String restName = restaurant.getName();
        final String restURL = restaurant.getImageUrl();
        final float restRating = (float)restaurant.getRating();
        final String restDistance = String.format("%.2f",(restaurant.getDistance()*0.00062137))+"mi";

        String address;
        if(restaurant.getLocation().getAddress2()!=null&&restaurant.getLocation().getAddress2()!="") {
            address = restaurant.getLocation().getAddress1() + ", " + restaurant.getLocation().getAddress2() + ", " + restaurant.getLocation().getCity();
            if (restaurant.getLocation().getAddress3()!=null&&restaurant.getLocation().getAddress3()!="") {
                address = restaurant.getLocation().getAddress1() + ", " + restaurant.getLocation().getAddress2() + ", " + restaurant.getLocation().getAddress3() + ", " + restaurant.getLocation().getCity();
            }
        }
        else {
            address = restaurant.getLocation().getAddress1() + ", " + restaurant.getLocation().getCity();
        }

        final String restLocation = address;

        // create Restaurant object for RestaurantInfo page
        final Restaurant myRest = new Restaurant(restName, restURL, restRating, restLocation );


        holder.restName.setText(restName);
        holder.restRating.setRating(restRating);
        holder.restLocation.setText(restLocation);
        holder.restDistance.setText(restDistance);
        new DownloadImageTask(holder.restIMG).execute(restURL);

        // go to RestaurantInfo page on click
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantInfo.class);
                intent.putExtra("myRest", myRest);
                context.startActivity(intent);
            }
        });
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
            restDistance = (TextView)itemView.findViewById(R.id.rest_distance);
        }
    }
}