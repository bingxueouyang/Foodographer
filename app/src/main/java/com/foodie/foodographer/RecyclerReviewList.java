/*
 * An adapter object for Review list
 */

package com.foodie.foodographer;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.view.*;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerReviewList extends RecyclerView.Adapter<RecyclerReviewList.MyViewHolder> {
    private List<Review> review_list;
    // firebase authorize object
    private FirebaseAuth firebaseAuth;
    // firebase database object
    private FirebaseDatabase firebaseDatabase;
    
    public RecyclerReviewList (ArrayList<Review> review_list){
        this.review_list = review_list;
    }
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate review list view with holder objects
        View listItem = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.review_card, parent, false);
        // assign firebase authorize
        firebaseAuth = FirebaseAuth.getInstance();
        // assign firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // set view content with review object value
        holder.setIsRecyclable(false);
        if(firebaseAuth != null){
            firebaseDatabase.getReference();
        }
        Review review = review_list.get(position);
        holder.username.setText(review.getUsername());
        holder.restRating.setRating((float)review.getRating());
        holder.review_time.setText(review.getTime());
        holder.content.setText(review.getContent());
        // download image from url
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
        // find views and assign to holder
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
