package com.foodie.foodographer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.bumptech.glide.Glide;
import android.util.Log;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth3;
    private Button signout;
    private TextView userEmail_textview;
    private Button settingBut;
    private DatabaseReference profileRefer;
    private String currentUserID;
    private CircleImageView userImageView;
    private String totalExpertise;
    private TextView expertiseText;
    private TextView mostFavRest;
    private DatabaseReference profileReviewRefer;
    private FirebaseUser currentUser;
    private int checkSize=0;
    private  String userFavRest;
    private Button favListBut;
    private Button recentViewListBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth3=FirebaseAuth.getInstance();
        currentUser=mAuth3.getCurrentUser();

        if(currentUser==null){
            finish();
            startActivity(new Intent(this, LogIn.class));
        }
        else {


            currentUserID = currentUser.getUid();
            profileRefer = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);

            userEmail_textview = (TextView) findViewById(R.id.username);
            String email = currentUser.getEmail();
            String emailUserName = email.substring(0,email.indexOf('@'));
            userEmail_textview.setText(emailUserName);
            signout = (Button) findViewById(R.id.logoutButton);
            signout.setOnClickListener(this);
            settingBut = (Button) findViewById(R.id.setting);
            settingBut.setOnClickListener(this);
            userImageView = (CircleImageView) findViewById(R.id.profile_image);
            expertiseText = (TextView) findViewById(R.id.perosonalExpertise);
            mostFavRest = (TextView) findViewById(R.id.favoriteRest);
            favListBut =(Button) findViewById(R.id.listFavor);
            favListBut.setOnClickListener(this);
            recentViewListBut=(Button) findViewById(R.id.listView);
            recentViewListBut.setOnClickListener(this);
            profileRefer.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String imageOfUser = dataSnapshot.child("profileImageUrl").getValue().toString();
                    Log.d("checking", imageOfUser + "checking id");

                    String firstExpertise = dataSnapshot.child("Expert").child("Expert1").getValue().toString();
                    String secondExpertise = dataSnapshot.child("Expert").child("Expert2").getValue().toString();
                    String thirdExpertise = dataSnapshot.child("Expert").child("Expert3").getValue().toString();

                    totalExpertise = firstExpertise +" | "+ secondExpertise +" | "+ thirdExpertise;

                    //Log.d("check",imageOfUser+" checking right");
                    //if(imageOfUser == "nothing"){
                    //Picasso.with(UserProfile.this).load(imageOfUser).placeholder(R.drawable.profile).error(R.drawable.profile).into(userImageView);
                    //}

                    //if(imageOfUser != "nothing"){
                    //Picasso.with(UserProfile.this).load(imageOfUser).placeholder(R.drawable.profile).error(R.drawable.profile).into(userImageView);
                    //}
                    Picasso.with(UserProfile.this).load(imageOfUser).into(userImageView);
                    //new DownloadImageTask(userImageView).execute(imageOfUser);
                    expertiseText.setText(totalExpertise);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            profileReviewRefer=profileRefer.child("comments");
            profileReviewRefer.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }




    }

    @Override
    public void onClick(View view){
        if(view==signout){
            mAuth3.signOut();
            finish();
            startActivity(new Intent(UserProfile.this, LogIn.class));
        }
        if(view==settingBut){
            finish();
            startActivity(new Intent(UserProfile.this, account_setting.class));
        }
        if(view==favListBut){
            finish();
            startActivity(new Intent(UserProfile.this, UserFavoriteRestaurants.class));
        }
        if(view==recentViewListBut){
            finish();
            startActivity(new Intent(UserProfile.this, UserRecentViewRestaurants.class));
        }
    }

}