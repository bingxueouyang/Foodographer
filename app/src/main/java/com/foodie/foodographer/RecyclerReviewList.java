package com.foodie.foodographer;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.*;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;




public class RecyclerReviewList extends RecyclerView.Adapter<RecyclerReviewList.MyViewHolder> {
    private List<Review> review_list;

    public RecyclerReviewList (ArrayList<Review> review_list){
        this.review_list = review_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card, parent, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Review review = review_list.get(position);
        holder.username.setText(review.getUsername());
        //String email = review.getUser().getEmail();
        //Log.i("ssssss", email);
        holder.restRating.setRating((float)review.getRating());
        holder.review_time.setText(review.getTime());
        holder.content.setText(review.getContent());
        new DownloadImageTask(holder.userIMG).execute(review.getUserIMGURL());
    }


    @Override
    public int getItemCount() {
        return review_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private ImageView userIMG;
        private RatingBar restRating;
        private TextView review_time;
        private TextView content;
        public MyViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.user_name);
            userIMG = (ImageView) itemView.findViewById(R.id.user_IMG);
            restRating = (RatingBar) itemView.findViewById(R.id.review_rating);
            review_time = (TextView) itemView.findViewById(R.id.reviewed_time);
            content = (TextView) itemView.findViewById(R.id.review_content);
        }
    }


}
