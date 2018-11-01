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

public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth3;
    private Button signout;
    private TextView userEmail_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth3=FirebaseAuth.getInstance();
        if(mAuth3.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LogIn.class));
        }
        FirebaseUser firebaseUser=mAuth3.getCurrentUser();
        //userEmail_textview= (TextView) findViewById(R.id.textViewUseremail);
        //userEmail_textview.setText(firebaseUser.getEmail());
        signout= (Button) findViewById(R.id.logoutButton);
        signout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view==signout){
            mAuth3.signOut();
            finish();
            startActivity(new Intent(this, LogIn.class));
        }
    }
}