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
import android.net.Uri;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;
import java.util.HashMap;
import java.util.Map;
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
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        getemail = (EditText) findViewById(R.id.EmailAdress);
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

        final String expert1 = expertSpinner.getSelectedItem().toString().trim();
        final String expert2 = expertSpinner2.getSelectedItem().toString().trim();
        final String expert3 = expertSpinner3.getSelectedItem().toString().trim();

        final String interest1 = interestSpinner.getSelectedItem().toString().trim();
        final String interest2 = interestSpinner2.getSelectedItem().toString().trim();
        final String interest3 = interestSpinner3.getSelectedItem().toString().trim();
        final String interest4 = interestSpinner4.getSelectedItem().toString().trim();


        FirebaseUser usertemp = mAuth.getCurrentUser();
        if(usertemp !=null){
            usertemp.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText( SignUp.this,"Please verified your account!",Toast.LENGTH_SHORT).show();
                        String user_id=mAuth.getCurrentUser().getUid();
                        mDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(user_id);


                        HashMap user_info= new HashMap();
                        user_info.put("Email",infoEmail);
                        user_info.put("Favorite","");
                        user_info.put("Expert","");
                        user_info.put("Interest","");
                        user_info.put("RecentView","");
                        user_info.put("Comment","");
                        //Uri hold = "http://img.icons8.com/color/1600/circled-user-male-skin-type-1-2.png";
                        user_info.put("profileImageUrl","http://img.icons8.com/color/1600/circled-user-male-skin-type-1-2.png");
                        mDatabase.setValue(user_info);
                        DatabaseReference createExpert=mDatabase.child("Expert");
                        HashMap userExpert= new HashMap();
                        userExpert.put("Expert1",expert1);
                        userExpert.put("Expert2",expert2);
                        userExpert.put("Expert3",expert3);
                        createExpert.setValue(userExpert);
                        DatabaseReference createInterest=mDatabase.child("Interest");
                        HashMap userInterest= new HashMap();
                        userInterest.put("Interest1",interest1);
                        userInterest.put("Interest2",interest2);
                        userInterest.put("Interest3",interest3);
                        userInterest.put("Interest4",interest4);
                        createInterest.setValue(userInterest);

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
