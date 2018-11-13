package com.foodie.foodographer;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.foodie.foodographer.PostReviewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.widget.RatingBar;
import android.widget.ImageView;
import android.support.v7.widget.CardView;

public class RestaurantInfo extends AppCompatActivity implements View.OnClickListener{

    private CardView amenties;
    private TextView restName;
    private LinearLayout restTags;
    private Button favoriteButton;
    private FirebaseAuth mAuthSetting;
    private DatabaseReference commentRef;
    private DatabaseReference userRef;
    private String currentUserID;
    private DatabaseReference restReference;
    private int sizeCounter;
    private Restaurant myRest;
    private DatabaseReference userRef2;
    private ArrayList<Review> reviewArrayList;
    private RecyclerView myView;
    String Rest_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        mAuthSetting= FirebaseAuth.getInstance();

        restTags = findViewById(R.id.rest_tags);
        restTags.setVisibility(View.GONE);

        amenties = findViewById(R.id.openTag);
        amenties.setOnClickListener(this);
        myRest = getIntent().getParcelableExtra("myRest");
        favoriteButton =(Button) findViewById(R.id.likeButton);

        if(mAuthSetting.getCurrentUser()!=null) {
            currentUserID = mAuthSetting.getCurrentUser().getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);
            favoriteButton.setOnClickListener(this);
        }
        Rest_ID = myRest.getId();



        restName = findViewById(R.id.rest_name);
        restName.setText(myRest.getName());

        RatingBar restRating = findViewById(R.id.rest_rating);
        restRating.setRating(myRest.getRating());

        TextView restLocation = findViewById(R.id.rest_location);
        restLocation.setText(myRest.getLocation());

        ImageView restIMG = findViewById(R.id.rest_IMG);
        new DownloadImageTask(restIMG).execute(myRest.getIMGURL());
        commentRef = FirebaseDatabase.getInstance().getReference("Restaurants").child(Rest_ID).child("comments");
        myView = (RecyclerView) findViewById(R.id.recyclerview);
        reviewArrayList = new ArrayList<>();

        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Log.i("snapshot", "Inside onDataChange!!!");
                reviewArrayList.clear();
                for(DataSnapshot reviewSnapshot: dataSnapshot.getChildren()){
                    Log.i("database info", "Check: " + reviewSnapshot.getValue());
                    Review thisReview = reviewSnapshot.getValue(Review.class);
                    reviewArrayList.add(thisReview);
                }
                RecyclerReviewList adapter = new RecyclerReviewList(reviewArrayList);
                myView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//
//        ArrayList<Review> reviews = new ArrayList<Review>();
//
//        reviews.add(new Review("https://upload.wikimedia.org/wikipedia/en/2/21/Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float) 3.0, "3 months ago", "Had a wonderful Sunday breakfast with good friends. My buddy had the Eggs Benedict and said it was the best he even had. I wanted a bite but it disappeared off his play too fast. Both the mushroom and western omelettes are very good. To top it off excellent delightful service. Thank you Kay!"));
//        reviews.add(new Review("https://upload.wikimedia.org/wikipedia/en/2/21/Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float) 3.0, "3 months ago", "content"));
//        reviews.add(new Review("https://upload.wikimedia.org/wikipedia/en/2/21/Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float) 3.0, "3 months ago", "content"));
//        reviews.add(new Review("https://upload.wikimedia.org/wikipedia/en/2/21/Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float) 3.0, "3 months ago", "content"));
//        reviews.add(new Review("https://upload.wikimedia.org/wikipedia/en/2/21/Web_of_Spider-Man_Vol_1_129-1.png", "Haofan", (float) 3.0, "3 months ago", "content"));

        // making articles recycle
//        RecyclerReviewList adapter = new RecyclerReviewList(reviews);
        myView.setHasFixedSize(true);
//        myView.setAdapter(adapter);
//        RecyclerView myView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
    public void postReview(View view) {
        Intent intent = new Intent(RestaurantInfo.this, PostReviewActivity.class);
        startActivityForResult(intent, 21);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == RESULT_OK) {
            String userReview = data.getStringExtra("userReview");
            saveReviewToDB(userReview);
        }
    }
    private void saveReviewToDB(String userReview){
        final String review = userReview;
        //currentUserID=mAuthSetting.getCurrentUser().getUid();
        // userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);
        userRef2=userRef.child("comments").child(Rest_ID);
        userRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Log.i("snapshot", "Inside onDataChange!!!");
                String email = mAuthSetting.getCurrentUser().getEmail();
                String emailUserName = email.substring(0,email.indexOf('@'));
                String userIMGURL = dataSnapshot.child("profileImageUrl").getValue().toString();
                Review testReview = new Review(emailUserName, userIMGURL, (float) 3.0, "3 months ago", review);
                HashMap<String, Object> restaurantParams = new HashMap<>();
                restaurantParams.put(currentUserID, testReview);
                commentRef.updateChildren(restaurantParams);
                HashMap<String, Object> userParams = new HashMap<>();
                userParams.put("content", review);
                userRef2.updateChildren(userParams);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void updateUserFavResteraurant(){

        restReference = userRef.child("Favorite");
        final String grabUserFavoriteRest= myRest.getName();
        HashMap temp =new HashMap();

        temp.put(myRest.getId(),grabUserFavoriteRest);
        //sizeCounter++;
        restReference.updateChildren(temp).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText( RestaurantInfo.this,"Update your favorite restaurant successfully",Toast.LENGTH_SHORT).show();
                }else{
                    String grab_message=task.getException().getMessage();
                    Toast.makeText( RestaurantInfo.this,"Error occured:"+grab_message,Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    @Override
    public void onClick(View view) {
        if (view == amenties) {
            if (restTags.getVisibility() != View.VISIBLE) {
                restTags.setVisibility(View.VISIBLE);
            }
            else{
                restTags.setVisibility(View.GONE);
            }
        }
        if(view == favoriteButton){

            updateUserFavResteraurant();
        }

    }


}