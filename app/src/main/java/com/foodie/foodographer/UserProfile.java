package com.foodie.foodographer;
// import everything needed for this java class
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import android.util.Log;
import de.hdodenhof.circleimageview.CircleImageView;
public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    // initialize firebase authentication, button, textview, circleimageview of user image and datebase reference.
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
        // connect to firebase authentication
        mAuth3=FirebaseAuth.getInstance();
        // grab the current user from firebase authentication
        currentUser=mAuth3.getCurrentUser();
        // check user exist
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
            // grab user info from the database and display in the userinfo page
            profileRefer.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String imageOfUser = dataSnapshot.child("profileImageUrl").getValue().toString();
                    Log.d("checking", imageOfUser + "checking id");

                    String firstExpertise = dataSnapshot.child("Expert").child("Expert1").getValue().toString();
                    String secondExpertise = dataSnapshot.child("Expert").child("Expert2").getValue().toString();
                    String thirdExpertise = dataSnapshot.child("Expert").child("Expert3").getValue().toString();

                    totalExpertise = firstExpertise +" | "+ secondExpertise +" | "+ thirdExpertise;
                    //showing user image
                    Picasso.with(UserProfile.this).load(imageOfUser).into(userImageView);
                    //showing user expertise choice
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
