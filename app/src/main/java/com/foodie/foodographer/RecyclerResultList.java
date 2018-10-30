//https://stackoverflow.com/questions/30751870/can-one-use-cardview-for-listview-item-and-how

package com.foodie.foodographer;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.view.*;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yelp.fusion.client.models.Business;


public class RecyclerResultList extends RecyclerView.Adapter<RecyclerResultList.MyViewHolder> {
    private List<Business> rest_list;

    public RecyclerResultList (ArrayList<Business> rest_list){
        this.rest_list = rest_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_card, parent, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Business restaurant = rest_list.get(position);
        holder.restName.setText(restaurant.getName());
        holder.restRating.setRating((float)restaurant.getRating());
        String address;
        if(restaurant.getLocation().getAddress2() != "") {
            address = restaurant.getLocation().getAddress1() + ", " + restaurant.getLocation().getAddress2() + ", " + restaurant.getLocation().getCity();
            if (restaurant.getLocation().getAddress3() != "") {
                address = restaurant.getLocation().getAddress1() + ", " + restaurant.getLocation().getAddress2() + ", " + restaurant.getLocation().getAddress3() + ", " + restaurant.getLocation().getCity();
            }
        }
        else {
            address = restaurant.getLocation().getAddress1() + ", " + restaurant.getLocation().getCity();
        }
        holder.restLocation.setText(address);
    }


    @Override
    public int getItemCount() {
        return rest_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView restName;
        private RatingBar restRating;
        private TextView restLocation;
        public MyViewHolder(View itemView) {
            super(itemView);
            restName = (TextView) itemView.findViewById(R.id.rest_name);
            restRating = (RatingBar) itemView.findViewById(R.id.rest_rating);
            restLocation = (TextView) itemView.findViewById(R.id.rest_location);
        }
    }
}