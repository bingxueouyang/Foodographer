package com.foodie.foodographer;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class SignUp extends AppCompatActivity  implements View.OnClickListener {
    private Button registBut;
    private ImageButton addExpert;
    private ImageButton addInterest;
    private EditText getemail;
    private EditText getpassword;
    private TextView signupText;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private Spinner expertSpinner;
    private Spinner expertSpinner2;
    private Spinner expertSpinner3;
    private Spinner interestSpinner;
    private Spinner interestSpinner2;
    private Spinner interestSpinner3;
    private Spinner interestSpinner4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();


        progressDialog= new ProgressDialog(this);

        registBut = (Button) findViewById(R.id.checkingRegister);

        getpassword = (EditText) findViewById(R.id.create_password);
        signupText = (TextView) findViewById(R.id.givinghint);

        addExpert = (ImageButton) findViewById(R.id.AddExpert);
        addInterest = (ImageButton) findViewById(R.id.AddInterest);

        registBut.setOnClickListener(this);
        signupText.setOnClickListener(this);
        addExpert.setOnClickListener(this);
        addInterest.setOnClickListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> expertAdapter = new ArrayAdapter<String>( SignUp.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.expertise_array));
        // Specify the layout to use when the list of choices appears
        expertAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        expertSpinner = (Spinner) findViewById(R.id.ExpertiseSpinner);
        expertSpinner.setAdapter(expertAdapter);



        expertSpinner2 = (Spinner) findViewById(R.id.ExpertiseSpinner2);
        expertSpinner2.setAdapter(expertAdapter);
        expertSpinner2.setVisibility(View.GONE);

        expertSpinner3 = (Spinner) findViewById(R.id.ExpertiseSpinner3);
        expertSpinner3.setAdapter(expertAdapter);
        expertSpinner3.setVisibility(View.GONE);



        ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>( SignUp.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.expertise_array));
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        interestSpinner = (Spinner) findViewById(R.id.InterestSpinner);
        interestSpinner.setAdapter(interestAdapter);


        interestSpinner2 = (Spinner) findViewById(R.id.InterestSpinner2);
        interestSpinner2.setAdapter(interestAdapter);
        interestSpinner2.setVisibility(View.GONE);

        interestSpinner3 = (Spinner) findViewById(R.id.InterestSpinner3);
        interestSpinner3.setAdapter(interestAdapter);
        interestSpinner3.setVisibility(View.GONE);

        interestSpinner4 = (Spinner) findViewById(R.id.InterestSpinner4);
        interestSpinner4.setAdapter(interestAdapter);
        interestSpinner4.setVisibility(View.GONE);



    }

    private void registerUser(){
        String infoEmail = getemail.getText().toString().trim();
        String infoPassword = getpassword.getText().toString().trim();

        if(TextUtils.isEmpty(infoEmail)){
            Toast.makeText(this,"Please enter an email !",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(infoPassword)){
            Toast.makeText(this,"Please enter password !",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(infoEmail,infoPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //Toast.makeText(SignupActivity.this,"Successfully",Toast.LENGTH_SHORT).show();
                            send_message_verifed_email();

                        }else{
                            Toast.makeText( SignUp.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    protected void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser()!= null){
            //handle login user
        }
    }
    private void send_message_verifed_email(){
        final String infoEmail = getemail.getText().toString().trim();

        final ArrayList<String> expertises = new ArrayList<String>(3);
        expertises.add(expertSpinner.getSelectedItem().toString().trim());
        expertises.add(expertSpinner2.getSelectedItem().toString().trim());
        expertises.add(expertSpinner3.getSelectedItem().toString().trim());

        final ArrayList<String> interests = new ArrayList<String>(4);
        interests.add(interestSpinner.getSelectedItem().toString().trim());
        interests.add(interestSpinner2.getSelectedItem().toString().trim());
        interests.add(interestSpinner3.getSelectedItem().toString().trim());
        interests.add(interestSpinner4.getSelectedItem().toString().trim());


        FirebaseUser usertemp = mAuth.getCurrentUser();
        if(usertemp !=null){
            usertemp.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText( SignUp.this,"Please verified your account",Toast.LENGTH_SHORT).show();
                        String user_id=mAuth.getCurrentUser().getUid();
                        DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(user_id);

                        UserInfo user_info = new UserInfo(infoEmail,expertises,interests);
                        mDatabase.setValue(user_info);
                        finish();
                        startActivity(new Intent(getApplicationContext(),LogIn.class));
                        mAuth.signOut();
                    }else{
                        String grab_message=task.getException().getMessage();
                        Toast.makeText( SignUp.this,"Error occured:"+grab_message,Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        if(view == registBut){
            registerUser();
        }
        if(view == signupText){

            startActivity(new Intent(this,LogIn.class));
        }
        if(view == addExpert){
            if (expertSpinner2.getVisibility() != View.VISIBLE) {
                expertSpinner2.setVisibility(View.VISIBLE);
            }
            else{
                expertSpinner3.setVisibility(View.VISIBLE);
                //expertll3.invalidate();
                addExpert.setVisibility(View.GONE);
            }
        }
        if(view == addInterest){
            if (interestSpinner2.getVisibility() != View.VISIBLE) {
                interestSpinner2.setVisibility(View.VISIBLE);
            } else if (interestSpinner3.getVisibility() != View.VISIBLE) {
                interestSpinner3.setVisibility(View.VISIBLE);
            } else if (interestSpinner4.getVisibility() != View.VISIBLE) {
                interestSpinner4.setVisibility(View.VISIBLE);
                addInterest.setVisibility(View.GONE);
            }
        }

    }

}
