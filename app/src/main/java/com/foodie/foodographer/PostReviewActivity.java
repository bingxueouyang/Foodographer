package com.foodie.foodographer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PostReviewActivity extends AppCompatActivity {
   EditText ReviewText;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_post_review);
      ReviewText = findViewById(R.id.ReviewText);
   }

   /**
    * the user wants to submit his/her own review
    * return review as a string to RestaurantInfo(include onSuccess Code)
    * @param view
    */
   public void submitReview(View view) {
      String inputReview = ReviewText.getText().toString().trim();
      if(inputReview.equals("")){
         Toast.makeText(this, "Please enter a valid review.", Toast.LENGTH_SHORT).show();
         return;
      }
      Intent resultIntent = new Intent();
      resultIntent.putExtra("userReview", inputReview);
      setResult(RESULT_OK, resultIntent);
      finish();
   }

   /**
    * the user wants to cancel this session
    * return to RestaurantInfo with no return string form this activity
    * @param view
    */
   public void cancelReview(View view) {
      finish();
   }
}
