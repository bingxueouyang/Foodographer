package com.foodie.foodographer;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.widget.RatingBar;
import android.widget.ImageView;
import android.support.v7.widget.CardView;

import org.w3c.dom.Text;

public class RestaurantInfo extends AppCompatActivity implements View.OnClickListener{

    private CardView amenties;
    private TextView restName;
    private LinearLayout restTags;
    private ImageButton favoriteButton;
    private FirebaseAuth mAuthSetting;
    private DatabaseReference restRef;
    private DatabaseReference userRef;
    private String currentUserID;
    private DatabaseReference restReference;
    private int sizeCounter;
    private Restaurant myRest;
    private DatabaseReference userRef2;
    private ArrayList<Review> reviewArrayList;
    private RecyclerView myView;
    String Rest_ID;
    private TextView reviewButton;
    private Boolean checkUserExixt=true;
    private LinearLayout getDirection;
    private LinearLayout Call;
    private CardView openTime;
    private TextView time_info;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        //restaurant category
        restTags = findViewById(R.id.rest_tags);
        restTags.setVisibility(View.GONE);

        //restaurant name
        restName = findViewById(R.id.rest_name);
        restName.setText(myRest.getName());

        //Load image of restaurant
        ImageView restIMG = findViewById(R.id.rest_IMG);
        new DownloadImageTask(restIMG).execute(myRest.getIMGURL());

        //expandable time info
        openTime = findViewById(R.id.openTime);
        openTime.setOnClickListener(this);

        time_info = (TextView) findViewById(R.id.time_info);
        time_info.setVisibility(View.GONE);

        //amenties
        amenties = findViewById(R.id.openTag);

        //restaurant that we are looking at
        myRest = getIntent().getParcelableExtra("myRest");

        //bookmark favorite restaurant
        favoriteButton =(ImageButton) findViewById(R.id.likeButton);
        favoriteButton.setOnClickListener(this);

        //review button (write reviews and post it)
        reviewButton=(TextView) findViewById(R.id.saveRating_Btn);
        reviewButton.setOnClickListener(this);

        //mAuthSetting checks if user logged in
        mAuthSetting= FirebaseAuth.getInstance();
        if(mAuthSetting.getCurrentUser()!=null) {
            checkUserExixt=true;
            currentUserID = mAuthSetting.getCurrentUser().getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);

        }
        Rest_ID = myRest.getId();
        if(mAuthSetting.getCurrentUser()==null){
            Log.d("wassup","it is null");
            checkUserExixt=false;
        }

        final RatingBar restRating = findViewById(R.id.rest_rating);
        final TextView restLocation = findViewById(R.id.rest_location);

        // Yelp rating
        //restRating.setRating(myRest.getRating());
        restRef = FirebaseDatabase.getInstance().getReference("Restaurants").child(Rest_ID);
        myView = (RecyclerView) findViewById(R.id.recyclerview);
        reviewArrayList = new ArrayList<>();

        restRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Log.i("snapshot", "Inside onDataChange!!!");
                reviewArrayList.clear();
                for(DataSnapshot restSnapshot: dataSnapshot.getChildren()){
                    Log.i("database info", "Check: " + restSnapshot.getValue());
                    //get reviews of restaurant from firebase
                    Review thisReview = restSnapshot.child("comments").getValue(Review.class);
                    reviewArrayList.add(thisReview);

                    //get ratings of restaurant
                    float rating = (Float)restSnapshot.child("rating").getValue();
                    restRating.setRating(rating);

                    //get phone number of restaurant
                    String phone = (String) restSnapshot.child("phone").getValue();


                    //get location of restaurant
                    String location = (String) restSnapshot.child("location").getValue();
                    restLocation.setText(location);

                }
                RecyclerReviewList adapter = new RecyclerReviewList(reviewArrayList);
                myView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myView.setHasFixedSize(true);

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
        currentUserID=mAuthSetting.getCurrentUser().getUid();
        // userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);
        userRef2=userRef.child("comments").child(Rest_ID);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Log.i("snapshot", "Inside onDataChange!!!");
                String email = mAuthSetting.getCurrentUser().getEmail();
                String emailUserName = email.substring(0,email.indexOf('@'));
                String userIMGURL = dataSnapshot.child("profileImageUrl").getValue().toString();
                Log.i("database", "image Url is:" + userIMGURL);
                Review testReview = new Review(emailUserName, userIMGURL, (float) 3.0, "3 months ago", review);
                HashMap<String, Object> restaurantParams = new HashMap<>();
                restaurantParams.put(currentUserID, testReview);

                restRef.child("comments").updateChildren(restaurantParams);

                HashMap<String, Object> userParams = new HashMap<>();
                userParams.put("content", review);
                userRef2.updateChildren(userParams).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void updateUserFavResteraurant(){
        currentUserID=mAuthSetting.getCurrentUser().getUid();
        restReference = userRef.child("Favorite");
        final String grabUserFavoriteRest= myRest.getName();
        HashMap temp =new HashMap();
        //String temp2=restReference.push().getKey();
        //restReference.child(temp2).setValue(myRest);

        temp.put(myRest.getId(),grabUserFavoriteRest);
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

        //sizeCounter++;




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
        if(view==openTime){
            if (time_info.getVisibility() != View.VISIBLE) {
                time_info.setVisibility(View.VISIBLE);
            }
            else{
                time_info.setVisibility(View.GONE);
            }
        }
        if(view==getDirection){

        }

        if(view==Call) {
            phoneNumber = myRest.getPhone();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(phoneNumber));
            startActivity(callIntent);
        }
        if(view==favoriteButton && checkUserExixt== false){
            Toast.makeText( RestaurantInfo.this,"User only",Toast.LENGTH_SHORT).show();
            //Intent goingToLogin= new Intent(RestaurantInfo.this,LogIn.class);
            Log.d("checkMes","checking message"+checkUserExixt);
            //goingToLogin.putExtra("Value",checkUserExixt);
            //startActivity(goingToLogin);
        }
        if(view==reviewButton && checkUserExixt==true){
            postReview(view);
        }
        if(view==reviewButton && checkUserExixt== false){
            Toast.makeText( RestaurantInfo.this,"User only",Toast.LENGTH_SHORT).show();
        }
    }


}